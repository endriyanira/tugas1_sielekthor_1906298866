package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.PembelianModel;
import java.util.List;

public interface PembelianService {
    void addPembelian(PembelianModel pembelian);
    void updatePembelian(PembelianModel pembelian);
    List<PembelianModel> getPembelianList();
    PembelianModel getPembelianByIdPembelian(Long idPembelian);

}
