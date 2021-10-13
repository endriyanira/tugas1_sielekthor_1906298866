package apap.tugas.sielekthor.service;
import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.repository.BarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class BarangServiceImpl implements BarangService{

    @Autowired
    BarangDb barangDb;

    @Override
    public void addBarang(BarangModel barang) {
        barangDb.save(barang);
    }

    @Override
    public void updateBarang(BarangModel barang) {
        barangDb.save(barang);
    }

    @Override
    public List<BarangModel> getBarangList() {
        return barangDb.findAll();
    }

    @Override
    public BarangModel getBarangByIdBarang(Long idBarang) {
        Optional<BarangModel> barang = barangDb.findById(idBarang);
        if(barang.isPresent()){
            return barang.get();
        }
        return null;
    }

    @Override
    public List<BarangModel> getBarangbyTipe(TipeModel tipe) {
        //ListSemuaBarang
        List<BarangModel> listBarang = getBarangList();

        //Temporary List
        List<BarangModel> listBarangByTipe = new ArrayList<>();

        //Looping cocoking semua barang di listBarang dengan tipe yang dimaksud
        for (int i = 0 ; i < listBarang.size(); i++){

            //cek jika Tipe Barang di listBarang sama dengan Tipe yang dimau
            if(listBarang.get(i).getTipe().equals(tipe)){

                //adding barang to temporary list
                listBarangByTipe.add(listBarang.get(i));
            }
        }
        return listBarangByTipe;
    }

    @Override
    public List<BarangModel> getBarangbyStok(List<BarangModel> listBarangbyTipe, boolean stokBarang) {
        //temporary list
        List<BarangModel> listBarangbyTipeStok = new ArrayList<>();


//        boolean avai = true;
//        if(stokBarang==true){
//            avai = true;
//        }
//        else{
//            avai = false;
//        }

        //looping semua barang di listBarang yg udh di filter tipenya
        for (BarangModel br : listBarangbyTipe){
            System.out.println(br.getNamaBarang() + " stoknya" + br.getStokBarang());
            //cek kalo stok barangnya ada dan avai dari param true (1)
            if(br.getStokBarang() > 0 && stokBarang==true){


                //tambahin modelBarang ke temporary listBarangbyTipeStok
                listBarangbyTipeStok.add(br);
            }
            if(br.getStokBarang() == 0 && stokBarang == false){
                System.out.println("masuk yang kosong");
                listBarangbyTipeStok.add(br);
            }

        }
        return listBarangbyTipeStok;
    }

    @Override
    public void deleteBarang(Long idBarang) {
        barangDb.delete(getBarangByIdBarang(idBarang));
    }
}
