package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MemberController {
    @Qualifier("memberServiceImpl")
    @Autowired
    private MemberService memberService;

    @Autowired
    private PembelianService pembelianService;

    @GetMapping("/member")
    public String listMember(Model model) {
        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("listMember", listMember);
        return "viewall-member";
    }

    @GetMapping("/member/tambah")
    public String addMemberForm(Model model) {
        MemberModel member = new MemberModel();
        model.addAttribute("member", member);
        return "form-add-member";
    }

    @RequestMapping(value="/member/tambah", method = RequestMethod.POST)
    public String addMemberSubmit(
            @ModelAttribute MemberModel member,
            Model model)  {

        memberService.addMember(member);
        model.addAttribute("namaMember", member.getNamaMember());
        return "add-member";
    }

    @GetMapping("member/ubah/{idMember}")
    public String ubahMemberForm(
            @PathVariable Long idMember,
            Model model) {

        MemberModel member = memberService.getMemberByIdMember(idMember);
        model.addAttribute("listPembelian", member.getListPembelian());
        model.addAttribute("member", member);
        model.addAttribute("listPembelianMember", member.getListPembelian());

        return "form-ubah-member";
    }

    @PostMapping("member/ubah/{idMember}")
    public String ubahMemberSubmit(
            @ModelAttribute MemberModel member,
            @PathVariable Long idMember,
            Model model)  {

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        memberService.updateMember(member);

        if(member.getListPembelian() != null){
            for(PembelianModel p : member.getListPembelian()){
                String noinv = pembelianService.createInvoiceNumber(p);
                p.setNoInvoice(noinv);
                pembelianService.updatePembelian(p);
            }
        }

        model.addAttribute("listInvoice", member.getListPembelian());
        model.addAttribute("namaMember", member.getNamaMember());
        return "ubah-member";
    }

    @GetMapping("bonus/cari/member/paling-banyak")
    public String bonus(Model model){
        //dapet listMember semuanya
        List<MemberModel> listMember = memberService.getMemberList();

        //Temp List
        List<Integer> listJumlahPembelianMember = new ArrayList<>();

        //Masukin jumlahPembelian tiap member ke list baru
        for(MemberModel m : listMember){
            //update isi jumlahPembelianMember di temp list
            listJumlahPembelianMember.add(m.getListPembelian().size());
        }

        Map<MemberModel, Integer> pasangan = new HashMap<MemberModel, Integer>();
        for(int i = 0 ; i < listMember.size(); i ++){
            pasangan.put(listMember.get(i),listJumlahPembelianMember.get(i));
        }
        pasangan = pasangan.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        model.addAttribute("pair", pasangan);
        return "bonus-top-member";
    }
}
