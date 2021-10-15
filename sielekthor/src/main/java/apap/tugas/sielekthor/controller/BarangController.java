package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.TipeModel;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.TipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        TipeModel tipe = new TipeModel();
        BarangModel barang = barangService.getBarangByIdBarang(idBarang);
        model.addAttribute("tipe", tipe);
        model.addAttribute("barang", barang);
        return "form-ubah-barang";
    }

    @PostMapping("barang/ubah")
    public String ubahBarangSubmit(
            @ModelAttribute BarangModel barang,
            Model model
    ){
        barangService.updateBarang(barang);
        model.addAttribute("listBarang", barangService.getBarangList());
        model.addAttribute("kodeBarang", barang.getKodeBarang());
        return "ubah-barang";

    }

    @GetMapping("barang/cari")
    public String cariBarangForm(
            Model model){
        List<TipeModel> listTipeService= tipeService.getTipeList();
        TipeModel tipe = new TipeModel();
        List<BarangModel> listBarangByTipeAndPembayaran = new ArrayList<>();
        model.addAttribute("listTipeService", listTipeService);
        model.addAttribute("listBarangByTipeAndPembayaran",listBarangByTipeAndPembayaran);
        model.addAttribute("tp",tipe);

        return "form-cari-barang";
    }

    @RequestMapping(value="/barang/", params={"idTipe", "stok"})
    public String cariBarangSubmit(
            @RequestParam (value="idTipe") Long tipe,
            @RequestParam(name = "stok") boolean stokBarang,
            Model model){

        int stok;
        if(stokBarang){
            stok = 1;
        }
        else{
            stok = 0;

        }
        String url = tipe+"/stok?stok="+stok;
        return "redirect:"+url;

    }

    @GetMapping(value="/barang/{idTipe}/stok", params={"stok"})
    public String cariBarangSubmit(
            @PathVariable String idTipe,
            @RequestParam(name = "stok") boolean stokBarang,
            Model model){

        Long tipe = Long.parseLong(idTipe);
        //Dapetin Tipe Model yang dimaksud
        TipeModel tipeModel = tipeService.getTipeByIdTipe(tipe);

        //listBarang Semuanya
        List<BarangModel> listBarang = tipeModel.getListBarang();

        //Dapeting listBarang by Tipe
        List<BarangModel> listBarangbyTipe = barangService.getBarangbyTipe(tipeModel);

        //Dapetin listBarang by Tipe and Stok
        List<BarangModel> listBarangTipeStok = barangService.getBarangbyStok(listBarangbyTipe, stokBarang);

        //Buat di taro di form caripembelian
        List<TipeModel> listTipeService= tipeService.getTipeList();
        String availability;
        if(stokBarang){
            availability = "tersedia";
        }
        else{
            availability = "kosong";
        }
        model.addAttribute("tipe", tipeService.getTipeByIdTipe(tipe).getNamaTipe());
        model.addAttribute("listTipeService", listTipeService);
        model.addAttribute("listBarangTipeStok",listBarangTipeStok);

        //kirim untuk keterangan stok
        model.addAttribute("stringStok", availability);

        //stay with last selected dropdown and radio button
        model.addAttribute("stok", stokBarang);
        model.addAttribute("lastselected", tipeModel.getId());

        return "form-cari-barang";

    }








}
