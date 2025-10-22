package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "LOAISP")
public class LoaiSP {
    @Id
    @Column(name = "MaLoai")
    private String maLoai;

    @Column(name = "TenLoai", nullable = false)
    private String tenLoai;

    @OneToMany(mappedBy = "loai", cascade = CascadeType.ALL)
    private List<SanPham> sanPhams;
}
