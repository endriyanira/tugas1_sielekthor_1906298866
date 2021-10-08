package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.repository.TipeDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TipeServiceImpl implements TipeService{

    @Autowired
    TipeDb tipeDb;

    @Override
    public void addTipe(TipeModel tipe){
        tipeDb.save(tipe);
    }

    @Override
    public void updateTipe(TipeModel tipe){
        tipeDb.save(tipe);
    }

    @Override
    public List<TipeModel> getTipeList(){
        return tipeDb.findAll();
    }

    @Override
    public TipeModel getTipeByIdTipe(Long idTipe){
        Optional<TipeModel> tipe = tipeDb.findById(idTipe);
        if(tipe.isPresent()){
            return tipe.get();
        }
        return null;
    }
}
