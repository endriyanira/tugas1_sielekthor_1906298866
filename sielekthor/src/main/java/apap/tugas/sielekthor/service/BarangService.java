package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.TipeModel;

import java.util.List;

public interface BarangService {
    void addBarang(BarangModel barang);
    void updateBarang(BarangModel barang);
    List<BarangModel> getBarangList();
    BarangModel getBarangByIdBarang(Long idBarang);
    List<BarangModel> getBarangbyStok(List<BarangModel> listBarangbyTipe, boolean stokBarang);
    void deleteBarang(Long idBbarang);
}
