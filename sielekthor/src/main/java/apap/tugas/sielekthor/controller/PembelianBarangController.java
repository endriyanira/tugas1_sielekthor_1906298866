package apap.tugas.sielekthor.controller;

import apap.tugas.sielekthor.model.BarangModel;
import apap.tugas.sielekthor.model.MemberModel;
import apap.tugas.sielekthor.model.PembelianBarangModel;
import apap.tugas.sielekthor.model.PembelianModel;
import apap.tugas.sielekthor.service.MemberService;
import apap.tugas.sielekthor.service.PembelianBarangService;
import apap.tugas.sielekthor.service.BarangService;
import apap.tugas.sielekthor.service.PembelianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class PembelianBarangController {

    @Qualifier("pembelianBarangServiceImpl")
    @Autowired
    private PembelianBarangService pembelianBarangService;

    @Autowired
    private PembelianService pembelianService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BarangService barangService;

    @GetMapping("pembelian/tambah")
    public String addPembelianForm(Model model){

        PembelianBarangModel pembelianbarang = new PembelianBarangModel();

        //bikin model pembelian baru
        PembelianModel pembelian = new PembelianModel();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        model.addAttribute("tanggalPembelian", dateFormat.format(date));

        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("listMemberService", listMember); //Ini untuk dropdown form tambah pembelian

        //kirim objek pembelian
        model.addAttribute("pembelian", pembelian);

        //kirim atribut pembelian si listpembelianBarang
//        model.addAttribute("listPembelianBarang", pembelian.getListPembelianBarang());


        model.addAttribute("pembelianbarang", pembelianbarang);

        return "form-add-pembelian";
    }

    @PostMapping(value="pembelian/tambah")
    public String addPembelianSubmit(
            @ModelAttribute PembelianModel pembelian,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            Model model) throws IOException {

        Date date = new Date();
        Timestamp timestamp2 = new Timestamp(date.getTime());
        pembelian.setTanggalPembelian(timestamp2);
        System.out.println(pembelian.getListPembelianBarang());

        //dapetin listPembelianBarang sesuai dengan pembelian yang barudibuat
        List<PembelianBarangModel> listPb = pembelian.getListPembelianBarang();

        //set totalBelanjaannya jadi 0 dulu buat nanti disimpen di db
        pembelian.setTotal(0);

        //Buat Invoice
        String noInvoice = pembelianService.createInvoiceNumber(pembelian);

        List<BarangModel> listBarangStokTidakMencukupi = new ArrayList<>();

        //cek Stok di pembelian yang request dari listPembelianBarang
        for(PembelianBarangModel pb : pembelian.getListPembelianBarang()){
            System.out.println("nama barang "+ pb.getBarang().getId());
            System.out.println("quantity " + pb.getQuantity());
            if(pb.getBarang().getStokBarang() == null){
//                System.out.println(pb.getBarang().getNamaBarang());
                listBarangStokTidakMencukupi.add(pb.getBarang());
            }
            else if(pb.getQuantity() > pb.getBarang().getStokBarang()){
                listBarangStokTidakMencukupi.add(pb.getBarang());
            }
        }
        if(listBarangStokTidakMencukupi.size()!=0){
            return "stok-habis";

        }
        else{
            pembelian.setNoInvoice(noInvoice);

            //kosoing listPembelianBarang di pembelian
            pembelian.setListPembelianBarang(new ArrayList<>());
            //simpen ke db dulu pembeliannya
            pembelianService.addPembelian(pembelian);


            for(PembelianBarangModel pb : listPb){
                if(pembelian.getListPembelianBarang() == null){
                    pembelian.setListPembelianBarang(new ArrayList<>());
                }

                BarangModel barang = barangService.getBarangByIdBarang(pb.getBarang().getId());
                pb.setBarang(barang);

                if(barang.getListPembelianBarang() == null){
                    barang.setListPembelianBarang(new ArrayList<>());
                }
                pb.setTanggalGaransi(pembelianBarangService.getTanggalGaransi(pb));
                System.out.println(pb.getTanggalGaransi());
                pb.setPembelian(pembelian);

                barang.getListPembelianBarang().add(pb);
                pembelian.getListPembelianBarang().add(pb);

                //update stokBarang dikurangin
                barang.setStokBarang(barang.getStokBarang()-pb.getQuantity());

                pembelianBarangService.addPembelianBarang(pb);
                pembelianService.updatePembelian(pembelian);
                barangService.updateBarang(barang);

            }

            //Kalkulasi semua totalPembelian
            Integer total = pembelianService.getTotalPembelian(pembelian);

            //set  JumlahTotal Pembelian
            pembelian.setTotal(total);
            pembelianService.updatePembelian(pembelian);

            model.addAttribute("nomorInvoice", pembelian.getNoInvoice());
            return "add-pembelian";
        }

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

        List<MemberModel> listMember = memberService.getMemberList();
        model.addAttribute("listMemberService", listMember);

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listBarangService", barangService.getBarangList());
        model.addAttribute("listPembelianBarangService", listPembelianBarang);
        model.addAttribute("pembelianBarang", pembelianBarang);

        return "form-add-pembelian";
    }

    @RequestMapping(value="/pembelian/tambah", method= RequestMethod.POST, params={"hapusRow"})
    public String deleteRow(
            @ModelAttribute PembelianModel pembelian,
            @ModelAttribute PembelianBarangModel pembelianBarang,
            final HttpServletRequest request,
            final BindingResult bindingResult,
            Model model
    ){
        final Integer idPembelianBarang = Integer.valueOf(request.getParameter("hapusRow"));
        pembelian.getListPembelianBarang().remove(idPembelianBarang.intValue());


        model.addAttribute("listBarangService", barangService.getBarangList());
        model.addAttribute("pembelianBarang", pembelianBarang);

        model.addAttribute("pembelian", pembelian);
        model.addAttribute("listPembelianBarangService", pembelianBarangService.getPembelianBarangList());
        return "form-add-pembelian";
    }
}
