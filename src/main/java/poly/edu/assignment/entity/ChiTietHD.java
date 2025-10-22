package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CTHD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietHD {
    @EmbeddedId
    private ChiTietHDKey id;

    @ManyToOne
    @MapsId("maHD")
    @JoinColumn(name = "MaHD")
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("maSP")
    @JoinColumn(name = "MaSP")
    private SanPham sanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia", precision = 18, scale = 2)
    private BigDecimal donGia;

    // ✅ Phương thức tính thành tiền
    public BigDecimal getThanhTien() {
        if (donGia == null || soLuong == null) return BigDecimal.ZERO;
        return donGia.multiply(BigDecimal.valueOf(soLuong));
    }
}
