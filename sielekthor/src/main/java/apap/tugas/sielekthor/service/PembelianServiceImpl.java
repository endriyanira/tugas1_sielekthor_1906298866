package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.repository.PembelianDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional

public class PembelianServiceImpl implements PembelianService{

    @Autowired
    PembelianDb pembelianDb;

    @Override
    public void addPembelian(PembelianModel pembelian) {
        pembelianDb.save(pembelian);
    }

    @Override
    public void updatePembelian(PembelianModel pembelian) {
        pembelianDb.save(pembelian);
    }

    @Override
    public List<PembelianModel> getPembelianList() {
        return pembelianDb.findAll();
    }

    @Override
    public PembelianModel getPembelianByIdPembelian(Long idPembelian) {
        Optional<PembelianModel> pembelian = pembelianDb.findById(idPembelian);
        if(pembelian.isPresent()){
            return pembelian.get();
        }
        return null;
    }

    @Override
    public String createInvoiceNumber(PembelianModel pembelian) {
        String invoiceNumber="";

        String namaMember = pembelian.getMember().getNamaMember(); //dapetin nama member
        int temp = (int) namaMember.toLowerCase().charAt(0); //bikin nama jadi lowecase
        String byName = (Integer.toString(temp-96));

        //Ambil karakter pertama namaMember yang udah jadi urutan alphabet
        invoiceNumber += (byName.substring(byName.length()-1));

        //Karakter terakhir nama admin
        invoiceNumber += (pembelian.getNamaAdmin().toUpperCase().charAt(pembelian.getNamaAdmin().length()-1));

        String tglbeli = new SimpleDateFormat("yyyy-MM-dd").format(pembelian.getTanggalPembelian());
        int tgl = Integer.parseInt(tglbeli.substring(8,10));
        int bln = Integer.parseInt(tglbeli.substring(5,7));

        //4 karakter tanggal pembelian dalam format ddmm
        invoiceNumber += (""+tgl+""+bln);

        //Cicilan Or Tunai
        if(pembelian.getIsCash()){
            invoiceNumber += "02";
        }
        else{
            invoiceNumber += "01";
        }

        //3 karakter (tgl beli + bulan bel) * 5
        int jumlah = (tgl+bln) * 5;
        if(Integer.toString(jumlah).length() < 3){
            invoiceNumber += ("0"+jumlah);
        }
        else{
            invoiceNumber += jumlah;
        }

        //Random 2 Char
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        Random r = new Random();
        for(int i = 0; i < 2; i++) {
            int randomNo = r.nextInt(26);
            invoiceNumber += (alphabet[randomNo]);

        }
        return invoiceNumber;

    }

    @Override
    public Integer getTotalPembelian(PembelianModel pembelian) {
        Integer total = 0;
        for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
            total += (pb.getQuantity()*pb.getBarang().getHargaBarang());
        }
        return total;
    }

    @Override
    public int getJumlahTotal(PembelianModel pembelian) {
        int jumlahBarang = 0;
        if(pembelian.getListPembelianBarang() != null){
            for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
                jumlahBarang += (pb.getQuantity());
            }
        }
        return jumlahBarang;
    }

    @Override
    public void deletePembelian(Long idpembelian) {
        pembelianDb.delete(getPembelianByIdPembelian(idpembelian));
    }

    @Override
    public String getJenisPembayaran(boolean tipePembayaran) {
        String jenisPembayaran;
        if(tipePembayaran){
            jenisPembayaran = "Tunai";
        }
        else{
            jenisPembayaran= "Cicilan";
        }
        return jenisPembayaran;
    }

}
