package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public interface MemberService {
    void addMember(MemberModel member);
    void updateMember(MemberModel member);
    List<MemberModel> getMemberList();
    MemberModel getMemberByIdMember(Long idMember);
    Timestamp convertStringtoTimestamp(String date) throws ParseException;
    String formatPenulisanTanggalLahir(Date dt);
    String formatPenulisanTanggalPendaftaran(Timestamp tm);

}
