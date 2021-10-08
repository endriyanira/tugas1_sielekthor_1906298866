package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.TipeModel;
import java.util.List;

public interface TipeService {
    void addTipe(TipeModel tipe);
    void updateTipe(TipeModel tipe);
    List<TipeModel> getTipeList();
    TipeModel getTipeByIdTipe(Long idTipe);

}