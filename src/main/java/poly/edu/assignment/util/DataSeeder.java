package poly.edu.assignment.util;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import poly.edu.assignment.entity.*;
import poly.edu.assignment.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataSeeder {

    private final NhanVienRepository nvRepo;
    private final KhachHangRepository khRepo;
    private final LoaiSPRepository loaiRepo;
    private final SanPhamRepository spRepo;
    private final PasswordEncoder encoder;

    public DataSeeder(
            NhanVienRepository nvRepo,
            KhachHangRepository khRepo,
            LoaiSPRepository loaiRepo,
            SanPhamRepository spRepo,
            PasswordEncoder encoder
    ) {
        this.nvRepo = nvRepo;
        this.khRepo = khRepo;
        this.loaiRepo = loaiRepo;
        this.spRepo = spRepo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void seed() {
        // ======= 1. Tạo nhân viên mẫu =======
        if (nvRepo.count() == 0) {
            NhanVien nv1 = new NhanVien();
            nv1.setMaNV("NV01");
            nv1.setHoTen("Nguyễn Văn An");
            nv1.setEmail("admin@shop.vn");
            nv1.setSdt("0912345678");
            nv1.setMatKhau(encoder.encode("123456"));
            nv1.setVaiTro("Admin");

            NhanVien nv2 = new NhanVien();
            nv2.setMaNV("NV02");
            nv2.setHoTen("Trần Thị B");
            nv2.setEmail("staff@shop.vn");
            nv2.setSdt("0933333333");
            nv2.setMatKhau(encoder.encode("123456"));
            nv2.setVaiTro("Bán hàng");

            nvRepo.saveAll(Arrays.asList(nv1, nv2));
            System.out.println("✅ Đã thêm dữ liệu nhân viên mẫu");
        }

        // ======= 2. Tạo khách hàng mẫu =======
        if (khRepo.count() == 0) {
            KhachHang kh = new KhachHang();
            kh.setMaKH("KH01");
            kh.setHoTen("Phạm Minh Tuấn");
            kh.setEmail("tuan.pham@gmail.com");
            kh.setSdt("0901234567");
            kh.setMatKhau(encoder.encode("123456"));
            kh.setNgayTao(LocalDate.now());
            khRepo.save(kh);
            System.out.println("✅ Đã thêm dữ liệu khách hàng mẫu");
        }

        // ======= 3. Tạo loại sản phẩm & sản phẩm =======
        if (loaiRepo.count() == 0) {
            LoaiSP l1 = new LoaiSP();
            l1.setMaLoai("L01");
            l1.setTenLoai("Điện thoại");

            LoaiSP l2 = new LoaiSP();
            l2.setMaLoai("L02");
            l2.setTenLoai("Laptop");

            loaiRepo.saveAll(Arrays.asList(l1, l2));

            SanPham sp1 = new SanPham();
            sp1.setMaSP("SP01");
            sp1.setTenSP("iPhone 15 Pro");
            sp1.setMoTa("Apple flagship");
            sp1.setDonGia(BigDecimal.valueOf(34990000.00));
            sp1.setSoLuongTon(50);
            sp1.setLoai(l1);
            sp1.setTrangThai(true);

            SanPham sp2 = new SanPham();
            sp2.setMaSP("SP02");
            sp2.setTenSP("MacBook Air M3");
            sp2.setMoTa("Laptop Apple");
            sp2.setDonGia(BigDecimal.valueOf(32990000.00));
            sp2.setSoLuongTon(20);
            sp2.setLoai(l2);
            sp2.setTrangThai(true);

            spRepo.saveAll(Arrays.asList(sp1, sp2));
            System.out.println("✅ Đã thêm dữ liệu sản phẩm mẫu");
        }
    }
}
