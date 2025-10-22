package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "KHACHHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhachHang {
    @Id
    @Column(name = "MaKH", length = 20)
    private String maKH;

    @Column(name = "HoTen", length = 100, nullable = false)
    private String hoTen;

    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "SDT", length = 15)
    private String sdt;

    @Column(name = "MatKhau", length = 255, nullable = false)
    private String matKhau;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;
}
