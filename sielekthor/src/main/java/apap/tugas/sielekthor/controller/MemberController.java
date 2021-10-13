package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        System.out.println("masuk sini");
//        MemberModel member = memberService.getMemberByIdMember(idMember);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        memberService.updateMember(member);
        System.out.println(member.getNamaMember());
        System.out.println(member.getListPembelian().size());

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
        List<Entry<MemberModel, Integer>> list = new ArrayList<>(pasangan.entrySet());
        list.sort(Entry.comparingByValue());

        int i = 0;
        for (Map.Entry<MemberModel, Integer> pair : list) {
            listMember.set(i, pair.getKey());
            listJumlahPembelianMember.set(i, pair.getValue());
            i += 1;
        }
        Collections.reverse(listMember);
        Collections.reverse(listJumlahPembelianMember);

        model.addAttribute("listMember", listMember);
        model.addAttribute("listJumlahPembelianMember", listJumlahPembelianMember);
        return "bonus-top-member";
    }
}
