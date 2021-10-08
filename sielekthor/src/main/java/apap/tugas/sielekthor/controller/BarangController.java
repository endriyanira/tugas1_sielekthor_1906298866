package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BarangController {

    @Qualifier("barangServiceImpl")
    @Autowired
    private BarangService barangService;

    @Autowired
    private TipeService tipeService;

    @GetMapping("/barang")
    public String listCabang(Model model) {
        List<BarangModel> listBarang = barangService.getBarangList();
        model.addAttribute("listBarang", listBarang);
        return "viewall-barang";
    }

    @GetMapping("barang/tambah")
    public String addCabangForm(Model model) {
        BarangModel barang = new BarangModel();
        model.addAttribute("barang", barang);
        model.addAttribute("listTipeService", tipeService.getTipeList());
        return "form-add-barang";
    }

    @PostMapping("barang/tambah")
    public String addBarangSubmit(
            @ModelAttribute BarangModel barang,
            Model model) {
        System.out.println(barang.getTipe());
        System.out.println(barang.getNamaBarang());
        barangService.addBarang(barang);

        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "add-barang";
    }

    @GetMapping("barang/{idBarang}")
    public String viewBarang(
            @PathVariable Long idBarang,
            Model model) {

        BarangModel barang = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("barang", barang);
        return "view-barang";

    }

    @GetMapping("barang/ubah/{idBarang}")
    public String ubahBarangForm(
            @PathVariable Long idBarang,
            Model model) {

        BarangModel barang = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("barang", barang);
        return "form-ubah-barang";
    }

    @PostMapping("barang/ubah")
    public String ubahBarangSubmit(
            @ModelAttribute BarangModel barang,
            Model model
    ){
//        System.out.println(barang.getTipe());
        barangService.updateBarang(barang);
        model.addAttribute("listBarang", barangService.getBarangList());
        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "ubah-barang";

    }





}
