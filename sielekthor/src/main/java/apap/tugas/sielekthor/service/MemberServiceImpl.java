package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.repository.MemberDb;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDb memberDb;

    @Override
    public void addMember(MemberModel member){
        memberDb.save(member);
    }

    @Override
    public void updateMember(MemberModel member){
        memberDb.save(member);
    }

    @Override
    public List<MemberModel> getMemberList(){
        return memberDb.findAll();
    }

    @Override
    public MemberModel getMemberByIdMember(Long idMember){
        Optional<MemberModel> member = memberDb.findById(idMember);
        if(member.isPresent()){
            return member.get();
        }
        return null;
    }

    @Override
    public Timestamp convertStringtoTimestamp(String dt) throws ParseException {

        String[] list = dt.split("T");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = (Date) formatter.parse(list[0]+" "+list[1]+":00");
        java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());
        return timeStampDate;
    }

    @Override
    public String formatPenulisanTanggalLahir(java.sql.Date dt) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = formatter.format(dt);
        return strDate;
    }

    @Override
    public String formatPenulisanTanggalPendaftaran(Timestamp tm) {
        String str=tm.toString();
        String[] list = str.split(" ");
        return (list[0]);
    }
}