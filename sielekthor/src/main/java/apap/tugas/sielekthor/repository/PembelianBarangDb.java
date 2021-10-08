package apap.tugas.sielekthor.repository;

import apap.tugas.sielekthor.model.PembelianBarangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PembelianBarangDb extends JpaRepository<PembelianBarangModel, Long>{
    Optional<PembelianBarangModel> findById(Long idPembelianBarang);
}