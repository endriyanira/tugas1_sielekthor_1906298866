package apap.tugas.sielekthor.service;
import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.repository.BarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
}
