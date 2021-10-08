package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.PembelianBarangService;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

//@Controller
public class PembelianBarangController {

    @Qualifier("pembelianBarangServiceImpl")
    @Autowired
    private PembelianBarangService PembelianBarangService;

    @Autowired
    private PembelianService pembelianService;

//    @GetMapping("pembelian/tambah")
//    public String addPembelianForm(Model model){
//        PembelianBarangModel pembelianbarang = new PembelianBarangModel();
//        model.addAttribute("pembelian", pembelianbarang);
////        model.addAttribute("member", pembelianService.)
////        model.addAttribute("pembelian", pembelian);
//        return "form-add-pembelian";
//    }
}
