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
import java.sql.Date;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pembelian_barang")

public class PembelianBarangModel {
    @Id
    @Max(20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Relasi dengan Barang
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_barang", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BarangModel barang;

    //Relasi dengan Pembelian
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pembelian", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PembelianModel pembelian;

    @NotNull
    @Column(name="tanggal_garansi",nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date tanggalGaransi;

//    jumlah pembelian suatu barang
    @NotNull
    @Column(name="quantity",nullable = false)
    private Integer quantity;




}
