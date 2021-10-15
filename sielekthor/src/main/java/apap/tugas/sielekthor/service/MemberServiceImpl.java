package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.repository.MemberDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

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
    public void updateMember(MemberModel member)  {
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
}