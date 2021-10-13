package apap.tugas.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "barang")

public class BarangModel {
    @Id
    @Max(20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name="nama_barang", nullable = false)
    private String namaBarang;

    @NotNull
    @Column(name="stok", nullable = false)
        private Integer stokBarang;

    @NotNull
    @Column(name="jumlah_garansi", nullable = false)
    private Integer jumlahGaransi;

    @NotNull
    @Size(max=255)
    @Column(name="deskripsi_barang", nullable = false)
    private String deskripsiBarang;

    @NotNull
    @Size(max=255)
    @Column(name="kode_barang", nullable = false, unique = true)
    private String kodeBarang;

    @NotNull
    @Size(max=255)
    @Column(name="merk_barang", nullable = false)
    private String merkBarang;

    @NotNull
    @Column(name="harga_barang", nullable = false)
    private Integer hargaBarang;


    //Relasi dengan Tipe
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_tipe", referencedColumnName = "id_tipe", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TipeModel tipe;

    //Relasi dengan PembelianBarang
    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianBarangModel> listPembelianBarang;


}
