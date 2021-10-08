package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.repository.PembelianDb;
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

}
