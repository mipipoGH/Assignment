package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BIEN_THE_SP")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BienTheSP {

    @Id
    @Column(name = "MaBienThe", length = 20)
    private String maBienThe;

    @ManyToOne
    @JoinColumn(name = "MaSP", nullable = false)
    private SanPham sanPham;

    @Column(name = "Mau")
    private String mau;

    @Column(name = "Size")
    private String size;

    @Column(name = "SoLuong")
    private Integer soLuong;
}
