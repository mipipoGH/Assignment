package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CTGH")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(CTGHKey.class)
public class CTGH {

    @Id
    @ManyToOne
    @JoinColumn(name = "MaGH")
    private GioHang gioHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "MaSP")
    private SanPham sanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;
}
