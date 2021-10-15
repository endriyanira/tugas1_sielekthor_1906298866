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

        //for loop isi listPembelian Service
        for (PembelianModel p : pembelianService.getPembelianList()){
            Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(p);
                listTotalJumlah.add(totalJumlahPerPembelian);
        }
        // kirim listTotalJumlahBarang tiap Pembelian
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

        //GetListPembelianBarang
        List<PembelianBarangModel>  listPembelianBarang = pembelian.getListPembelianBarang();

        //Get NomorInvoice
        String nomorInvoice = pembelian.getNoInvoice();

        for(PembelianBarangModel pb : listPembelianBarang){
            //update jumlah barang setelah pembelian di hapus
            pb.getBarang().setStokBarang(pb.getQuantity()+pb.getBarang().getStokBarang());
            barangService.updateBarang(pb.getBarang());
        }

        //Temp List
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


    @RequestMapping(path="/cari/pembelian", params={"idMember", "tipePembayaran"}, method = RequestMethod.GET)
    public String getPembelianbyMemberAndTipeBayar(
            @RequestParam(value="idMember") Long idMember,
            @RequestParam(value = "tipePembayaran") boolean tipePembayaran,
            Model model
    ){
        //ambil semua member
        List<MemberModel> listMember = memberService.getMemberList();

        //ambil listpembelian
        List<PembelianModel> listPembelian = pembelianService.getPembelianList();

        List<PembelianModel> listPembelianByMemberAndTipe = new ArrayList<>();
        List<PembelianModel> listAllMember = new ArrayList<>();

        for(PembelianModel p : listPembelian){
            if(p.getIsCash() == tipePembayaran && p.getMember().getId().equals(idMember)){
                listPembelianByMemberAndTipe.add(p);
            }
        }
        List<Integer> listTotalJumlah = new ArrayList<>();
        //for loop isi listPembelian Service
        for (PembelianModel p : listPembelianByMemberAndTipe){
            // Dapetin jumlahtotalnominalharga barang tiap pembelian
            Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(p);
            listTotalJumlah.add(totalJumlahPerPembelian);
        }

        model.addAttribute("listPembelianByMemberAndTipe", listPembelianByMemberAndTipe);
        model.addAttribute("listMemberService",listMember);
        model.addAttribute("listTotalJumlah", listTotalJumlah);
        System.out.println(idMember);
        model.addAttribute("lastMember", idMember);
        model.addAttribute("tipePembayaran", tipePembayaran);

        return "form-cari-pembelian-by-member-and-tipe";

    }

}
