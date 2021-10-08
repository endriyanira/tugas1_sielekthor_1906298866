package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.PembelianBarangModel;
import java.util.List;

public interface PembelianBarangService {
    void addPembelianBarang(PembelianBarangModel pembelianBarang);
    void updatePembelianBarang(PembelianBarangModel pembelianBarang);
    List<PembelianBarangModel> getPembelianBarangList();
    PembelianBarangModel getPembelianBarangByIdPembelianBarang(Long idPembelianBarang);

}