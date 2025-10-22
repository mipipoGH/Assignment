package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DIACHI")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaChi {

    @Id
    @Column(name = "MaDC", length = 20)
    private String maDC;

    @ManyToOne
    @JoinColumn(name = "MaKH", nullable = false)
    private KhachHang khachHang;

    @Column(name = "DiaChi", nullable = false)
    private String diaChi;

    @Column(name = "Tinh")
    private String tinh;

    @Column(name = "Huyen")
    private String huyen;

    @Column(name = "Xa")
    private String xa;

    @Column(name = "MacDinh")
    private Boolean macDinh = false;
}
