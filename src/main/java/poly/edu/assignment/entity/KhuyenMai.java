package poly.edu.assignment.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "KHUYENMAI")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KhuyenMai {
    @Id
    @Column(name = "MaKM", length = 20, nullable = false)
    private String maKM;

    @Column(name = "TenKM", length = 100, nullable = false)
    private String tenKM;

    @Column(name = "GiaTri", precision = 18, scale = 2, nullable = false)
    private BigDecimal giaTri;

    @Column(name = "NgayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDate ngayKetThuc;

    @Column(name = "DieuKien", length = 255)
    private String dieuKien;
}
