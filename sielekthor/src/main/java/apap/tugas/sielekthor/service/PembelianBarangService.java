package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;

import java.sql.Date;
import java.util.List;

public interface PembelianBarangService {
    void addPembelianBarang(PembelianBarangModel pembelianBarang);
    void updatePembelianBarang(PembelianBarangModel pembelianBarang);
    List<PembelianBarangModel> getPembelianBarangList();
    PembelianBarangModel getPembelianBarangByIdPembelianBarang(Long idPembelianBarang);
    Date getTanggalGaransi(PembelianBarangModel pb);

}