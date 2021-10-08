package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
//import java.util.Date;
import java.sql.Date;
import java.util.List;

@Controller
public class MemberController {
    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @GetMapping("/member")
    public String listMember(Model model) {
        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("listMember", listMember);
        return "viewall-member";
    }

    @GetMapping("/member/tambah")
    public String addMemberForm(Model model) {
        MemberModel member = new MemberModel();
        model.addAttribute("member", member);
//        Date date = new Date();
//        model.addAttribute("releaseDate", date);
        return "form-add-member";
    }

    @RequestMapping(value="/member/tambah", method = RequestMethod.POST)
    public String addMemberSubmit(
            @RequestParam("namaMember") String namaMember,
            @RequestParam("jenisKelamin")  Integer jenisKelamin,
            @RequestParam("tanggalLahir")  String tanggalLahir,
            @RequestParam("tanggalPendaftaran") String tanggalPendaftaran,
            Model model) throws ParseException {

        Timestamp tglDaftar = memberService.convertStringtoTimestamp(tanggalPendaftaran);
        Date tglLahir=Date.valueOf(tanggalLahir);

        MemberModel member = new MemberModel();
        member.setNamaMember(namaMember);
        member.setJenisKelamin(jenisKelamin);
        member.setTanggalLahir(tglLahir);
        member.setTanggalPendaftaran(tglDaftar);
        memberService.addMember(member);
        model.addAttribute("namaMember", member.getNamaMember());
        return "add-member";
    }

    @GetMapping("member/ubah/{idMember}")
    public String ubahMemberForm(
            @PathVariable Long idMember,
            Model model) {

        MemberModel member = memberService.getMemberByIdMember(idMember);
        model.addAttribute("member", member);

        return "form-ubah-member";
    }





}
