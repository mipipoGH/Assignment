package poly.edu.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "SANPHAM")
public class SanPham {
    @Id
    @Column(name = "MaSP")
    private String maSP;

    @Column(name = "TenSP", nullable = false)
    private String tenSP;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "DonGia")  // ✅ khớp chính xác tên cột trong SQL Server
    private BigDecimal donGia;

    @Column(name = "SoLuongTon")
    private Integer soLuongTon;

    @Column(name = "TrangThai")
    private Boolean trangThai;

    @ManyToOne
    @JoinColumn(name = "MaLoai") // ✅ trùng với bảng LOAISP
    private LoaiSP loai;
    @Column(name="Extension") // mới
    private String extension; // "jpg" hoặc "png"

}
