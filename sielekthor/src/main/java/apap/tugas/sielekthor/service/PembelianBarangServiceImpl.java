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
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.Calendar;
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

    @Override
    public Date getTanggalGaransi(PembelianBarangModel pb) {
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.DAY_OF_MONTH, pb.getBarang().getJumlahGaransi());
        timestamp = new Timestamp(cal.getTime().getTime());
        java.sql.Date dt = java.sql.Date.valueOf(timestamp.toLocalDateTime().toLocalDate());
        return dt;
    }
}
