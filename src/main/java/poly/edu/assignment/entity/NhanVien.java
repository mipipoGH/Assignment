package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NHANVIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanVien {
    @Id
    @Column(name = "MaNV", length = 20)
    private String maNV;

    @Column(name = "HoTen", length = 100, nullable = false)
    private String hoTen;

    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "SDT", length = 15)
    private String sdt;

    @Column(name = "MatKhau", length = 255, nullable = false)
    private String matKhau;

    @Column(name = "VaiTro", length = 50)
    private String vaiTro;
}
