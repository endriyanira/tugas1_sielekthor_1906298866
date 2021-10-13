package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import java.util.List;

public interface PembelianService {
    void addPembelian(PembelianModel pembelian);
    void updatePembelian(PembelianModel pembelian);
    List<PembelianModel> getPembelianList();
    PembelianModel getPembelianByIdPembelian(Long idPembelian);
    String createInvoiceNumber(PembelianModel pembelian);

    Integer getTotalPembelian(PembelianModel pembelian);

    int getJumlahTotal(PembelianModel pembelian);

    void deletePembelian(Long idpembelian);

}
