package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.repository.MemberDb;
import apap.tugas.sielekthor.repository.PembelianBarangDb;
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
public class PembelianBarangServiceImpl implements PembelianBarangService {
    @Autowired
    PembelianBarangDb pembelianBarangDb;


    @Override
    public void addPembelianBarang(PembelianBarangModel pembelianBarang) {
        pembelianBarangDb.save(pembelianBarang);
    }

    @Override
    public void updatePembelianBarang(PembelianBarangModel pembelianBarang) {
        pembelianBarangDb.save(pembelianBarang);
    }

    @Override
    public List<PembelianBarangModel> getPembelianBarangList() {
        return pembelianBarangDb.findAll();
    }

    @Override
    public PembelianBarangModel getPembelianBarangByIdPembelianBarang(Long idPembelianBarang) {
        Optional<PembelianBarangModel> pembelianBarang = pembelianBarangDb.findById(idPembelianBarang);
        if(pembelianBarang.isPresent()){
            return pembelianBarang.get();
        }
        return null;
    }
}
