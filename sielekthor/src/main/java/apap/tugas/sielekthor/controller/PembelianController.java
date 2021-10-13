package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianService;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.PembelianBarangService;
import org.apache.tomcat.websocket.PerMessageDeflate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            @ModelAttribute PembelianModel pembelian,
            Model model){


        // kirim listPembelian (panjangnya) setiap member ke viewall-pembelian
        model.addAttribute("listPembelian", pembelianService.getPembelianList());

        List<Integer> listTotalJumlah = new ArrayList<>();
        //for loop isi listPembelian Service
        for (PembelianModel p : pembelianService.getPembelianList()){
            Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(p);
                listTotalJumlah.add(totalJumlahPerPembelian);
        }
        System.out.println(listTotalJumlah);

        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        String dateString = dateFormat.format(new Date());
        model.addAttribute("myDate", dateString);
        model.addAttribute("listTotalJumlah", listTotalJumlah);

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

    @GetMapping("pembelian/hapus/{idPembelian}")
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
        List<PembelianModel> listPembelianByMemberAndTipe = new ArrayList<>();

        model.addAttribute("listMemberService",listMember);
        model.addAttribute("listPembelianByMemberAndTipe", listPembelianByMemberAndTipe);
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
            Integer totalJumlahPerPembelian = pembelianService.getJumlahTotal(p);
            listTotalJumlah.add(totalJumlahPerPembelian);
        }

        model.addAttribute("listPembelianByMemberAndTipe", listPembelianByMemberAndTipe);
        model.addAttribute("listMemberService",listMember);
        model.addAttribute("listTotalJumlah", listTotalJumlah);

        return "form-cari-pembelian-by-member-and-tipe";

    }

}
