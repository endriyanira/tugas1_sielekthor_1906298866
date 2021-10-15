package apap.tugas.sielekthor.service;

import apap.tugas.sielekthor.model.MemberModel;
import java.util.List;

public interface MemberService {
    void addMember(MemberModel member);
    void updateMember(MemberModel member);
    List<MemberModel> getMemberList();
    MemberModel getMemberByIdMember(Long idMember);

}
