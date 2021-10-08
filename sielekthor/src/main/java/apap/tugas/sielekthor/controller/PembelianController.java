package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public String listPembelian(Model model){
        model.addAttribute(model);
        model.addAttribute("listPembelian", pembelianService.getPembelianList());
        return "viewall-pembelian";
    }
    @GetMapping("pembelian/{idPembelian}")
    public String viewPembelian(
            @PathVariable Long idPembelian,
            Model model) {

        PembelianModel pembelian = pembelianService.getPembelianByIdPembelian(idPembelian);
        model.addAttribute("pembelian", pembelian);
        return "view-pembelian";
    }

    @GetMapping("pembelian/tambah")
    public String addPembelianForm(Model model){
        PembelianBarangModel pembelianbarang = new PembelianBarangModel();
        PembelianModel pembelian = new PembelianModel();


        // Admin fieldnya ada di pembelianModel yang harus diisi
        model.addAttribute("pembelian", pembelian);
        model.addAttribute("pembelianbarang", pembelianbarang);

        // Pembeli ambil list member
        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("listMemberService", listMember); //Ini untuk dropdown form tambah pembelian

        // is Cash ada di pembelianModel (ngirim model pembelian)
        //Barang
        return "form-add-pembelian";
    }

    @PostMapping(value="/pembelian/tambah", params = {"tambahRow"})
    public String addRow(
        @ModelAttribute PembelianModel pembelian,
        @ModelAttribute PembelianBarangModel pembelianBarang,
        Model model
    ){
        if(pembelian.getListPembelianBarang()==null){
            pembelian.setListPembelianBarang(new ArrayList<PembelianBarangModel>());
        }
        List<PembelianBarangModel> listPembelianBarang = pembelianBarangService.getPembelianBarangList();
        pembelian.getListPembelianBarang().add(new PembelianBarangModel());

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listBarangService", barangService.getBarangList());
        model.addAttribute("listPembelianBarangService", listPembelianBarang);
        model.addAttribute("pembelianBarang", pembelianBarang);

        return "form-add-pembelian";
    }

    @RequestMapping(value="/pembelian/tambah", method= RequestMethod.POST, params={"hapusRow"})
    public String deleteRow(
        @ModelAttribute PembelianModel pembelian,
        final HttpServletRequest request,
        final BindingResult bindingResult,
        Model model
    ){
        final Integer idPembelianBarang = Integer.valueOf(request.getParameter("hapusRow"));
        pembelian.getListPembelianBarang().remove(idPembelianBarang.intValue());

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listPembelianBarangService", pembelianBarangService.getPembelianBarangList());
        return "form-add-pembelian";
    }


}
