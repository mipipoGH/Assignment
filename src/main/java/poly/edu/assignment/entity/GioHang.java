package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "GIOHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GioHang {

    @Id
    @Column(name = "MaGH", length = 20)
    private String maGH;

    @OneToOne
    @JoinColumn(name = "MaKH", unique = true, nullable = false)
    private KhachHang khachHang;

    @Column(name = "NgayTao")
    private LocalDate ngayTao = LocalDate.now();

    @OneToMany(mappedBy = "gioHang", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CTGH> chiTietGioHang;
}
