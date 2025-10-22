package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "THANHTOAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThanhToan {

    @Id
    @Column(name = "MaTT", length = 20)
    private String maTT;

    @OneToOne
    @JoinColumn(name = "MaHD", nullable = false, unique = true)
    private HoaDon hoaDon;

    @Column(name = "PhuongThuc")
    private String phuongThuc; // COD | Online

    @Column(name = "SoTien")
    private Double soTien;

    @Column(name = "NgayTT")
    private LocalDate ngayTT = LocalDate.now();

    @Column(name = "TrangThai")
    private String trangThai;
}
