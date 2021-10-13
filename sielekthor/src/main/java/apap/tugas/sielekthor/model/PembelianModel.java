package apap.tugas.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pembelian")

public class PembelianModel {
    @Id
//    @Max(20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="total", nullable = false)
    private Integer total;

//  tanggal pembelian
    @NotNull
    @Column(name="tanggal_pembelian", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-DD HH:MI:SS")
    private Timestamp tanggalPembelian;

//  nama admin pengelola
    @NotNull
    @Size(max=255)
    @Column(name = "nama_admin", nullable = false)
    private String namaAdmin;

//  no invoice pembelian
    @NotNull
    @Size(max=255)
    @Column(name = "no_invoice", unique = true, nullable = false)
    private String noInvoice;

//  metode pembayaran
    @NotNull
    @Column(name = "is_cash", nullable = false)
    private Boolean isCash;

//  Relasi dengan Member
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_member", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberModel member;

//  Relasi dengan PembelianBarang
    @OneToMany(mappedBy = "pembelian", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianBarangModel> listPembelianBarang;

}
