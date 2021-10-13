package apap.tugas.sielekthor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name="member")

public class MemberModel {
    //    id member
    @Id
    @Max(20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    jenis kelamin member
    @NotNull
    @Column(name = "jenis_kelamin", nullable = false)
    private Integer jenisKelamin;

    //    nama member
    @NotNull
    @Size(max=255)
    @Column(name = "nama_member", nullable = false)
    private String namaMember;


    @NotNull
    @Column(name = "tanggal_pendaftaran", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalPendaftaran;

    @NotNull
    @Column(nullable = false)
    private Date tanggalLahir;

    //Relasi dengan barang
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PembelianModel> listPembelian;


}