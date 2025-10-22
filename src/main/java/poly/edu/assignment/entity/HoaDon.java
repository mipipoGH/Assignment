package poly.edu.assignment.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HOADON")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDon {
    @Id
    @Column(name = "MaHD", length = 20)
    private String maHD;

    @ManyToOne
    @JoinColumn(name = "MaKH")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaDC")
    private DiaChi diaChi;

    @Column(name = "NgayLap")
    private LocalDate ngayLap;

    @Column(name = "TrangThai", length = 50)
    private String trangThai;

    @Column(name = "TongTien", precision = 18, scale = 2)
    private BigDecimal tongTien;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietHD> chiTiet = new ArrayList<>();


}
