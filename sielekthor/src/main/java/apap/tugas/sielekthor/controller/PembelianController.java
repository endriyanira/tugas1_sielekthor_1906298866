package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.PembelianBarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PembelianController {
    @Qualifier("pembelianServiceImpl")
    @Autowired
    private PembelianService pembelianService;

    @Autowired
    private PembelianBarangService pembelianBarangService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BarangService barangService;

    @GetMapping("/pembelian")
    public String listPembelian(
            Model model){

        List<Integer> listTotalJumlah = new ArrayList<>();
        for (PembelianModel p : pembelianService.getPembelianList()){
            Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(p);
                listTotalJumlah.add(totalJumlahPerPembelian);
        }

        model.addAttribute("listTotalJumlah", listTotalJumlah);
        model.addAttribute("listPembelian", pembelianService.getPembelianList());
        return "viewall-pembelian";
    }
    @GetMapping("pembelian/{idPembelian}")
    public String viewPembelian(
            @PathVariable Long idPembelian,
            Model model) {

        PembelianModel pembelian = pembelianService.getPembelianByIdPembelian(idPembelian);
        List<PembelianBarangModel> listPembelianBarang = pembelian.getListPembelianBarang();
        Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(pembelian);
        model.addAttribute("listPembelianBarang",listPembelianBarang);
        model.addAttribute("pembelian", pembelian);
        model.addAttribute("jumlahBarang", totalJumlahPerPembelian);
        return "view-pembelian";
    }

    @PostMapping("pembelian/hapus/{idPembelian}")
    public String hapusPembelian(
        @PathVariable Long idPembelian,
        Model model
    ){
        //Get Pembelian Model by IdPembelian
        PembelianModel pembelian = pembelianService.getPembelianByIdPembelian(idPembelian);
        List<PembelianBarangModel>  listPembelianBarang = pembelian.getListPembelianBarang();
        String nomorInvoice = pembelian.getNoInvoice();

        for(PembelianBarangModel pb : listPembelianBarang){
            pb.getBarang().setStokBarang(pb.getQuantity()+pb.getBarang().getStokBarang());
            barangService.updateBarang(pb.getBarang());
        }

        List<PembelianBarangModel> listPembelianBarangUpdate = pembelian.getListPembelianBarang();
        pembelianService.deletePembelian(idPembelian);

        model.addAttribute("listPembelianBarangUpdate", listPembelianBarangUpdate);
        model.addAttribute("noInvoice", nomorInvoice);
        return "delete-pembelian";
    }

    @GetMapping(value="/filter-pembelian")
    public String subMenuFilterPembelian(
            Model model

    ){
        List<MemberModel> listMember = memberService.getMemberList();

        model.addAttribute("listMemberService",listMember);
        model.addAttribute("submenu", "Cari Pembelian Berdasarkan Member/Tipe Pembayaran");
        return "form-cari-pembelian-by-member-and-tipe";
    }


    @GetMapping(path="/cari/pembelian", params={"idMember", "tipePembayaran"})
    public String getPembelianbyMemberAndTipeBayar(
            @RequestParam(value="idMember") Long idMember,
            @RequestParam(value = "tipePembayaran") boolean tipePembayaran,
            Model model
    ){

        MemberModel member = memberService.getMemberByIdMember(idMember);
        List<MemberModel> listMember = memberService.getMemberList();

        List<PembelianModel> listPembelian = member.getListPembelian();
        List<PembelianModel> listPembelianByMemberAndTipe = new ArrayList<>();

        for(PembelianModel p : listPembelian){
            if(p.getIsCash() == tipePembayaran){
                listPembelianByMemberAndTipe.add(p);
            }
        }

        List<Integer> listTotalJumlah = new ArrayList<>();
        for (PembelianModel p : listPembelianByMemberAndTipe){
            Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(p);
            listTotalJumlah.add(totalJumlahPerPembelian);
        }

        String jenisPembayaran = pembelianService.getJenisPembayaran(tipePembayaran);

        model.addAttribute("listPembelianByMemberAndTipe", listPembelianByMemberAndTipe);
        model.addAttribute("listMemberService",listMember);
        model.addAttribute("listTotalJumlah", listTotalJumlah);
        model.addAttribute("lastMember", idMember);
        model.addAttribute("tipePembayaran", tipePembayaran);
        model.addAttribute("jenisPembayaran", jenisPembayaran );
        model.addAttribute("namaMember", memberService.getMemberByIdMember(idMember).getNamaMember());

        return "form-cari-pembelian-by-member-and-tipe";

    }

}
