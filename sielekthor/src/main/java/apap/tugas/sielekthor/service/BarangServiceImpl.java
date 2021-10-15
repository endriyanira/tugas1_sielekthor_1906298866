package apap.tugas.sielekthor.service;
import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.repository.BarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
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
    public List<BarangModel> getBarangbyStok(List<BarangModel> listBarangbyTipe, boolean stokBarang) {
        List<BarangModel> listBarangbyTipeStok = new ArrayList<>();

        for (BarangModel br : listBarangbyTipe){
            if(br.getStokBarang() > 0 && stokBarang==true){
                listBarangbyTipeStok.add(br);
            }
            if(br.getStokBarang() == 0 && stokBarang == false){
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
