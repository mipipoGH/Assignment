package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.*;
import poly.edu.assignment.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HoaDonService {

    private final HoaDonRepository hoaDonRepository;
    private final ChiTietHDRepository chiTietHDRepository;
    private final SanPhamRepository sanPhamRepository;
    private final DiaChiRepository diaChiRepository;

    public HoaDonService(HoaDonRepository hoaDonRepository,
                         ChiTietHDRepository chiTietHDRepository,
                         SanPhamRepository sanPhamRepository,
                         DiaChiRepository diaChiRepository) {
        this.hoaDonRepository = hoaDonRepository;
        this.chiTietHDRepository = chiTietHDRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.diaChiRepository = diaChiRepository;
    }

    @Transactional
    public HoaDon placeOrder(KhachHang kh, Map<String, Integer> cartMap) {

        // ✅ 1. Tạo mã hóa đơn duy nhất
        String maHD = "HD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // ✅ 2. Lấy địa chỉ mặc định của khách hàng
        List<DiaChi> diaChiList = diaChiRepository.findByKhachHang(kh);
        DiaChi diaChi = diaChiList.stream()
                .filter(dc -> dc.getMacDinh() != null && dc.getMacDinh())
                .findFirst()
                .orElse(diaChiList.isEmpty() ? null : diaChiList.get(0));

        // Nếu không có địa chỉ nào, tạo địa chỉ mặc định
        if (diaChi == null) {
            diaChi = createDefaultAddress(kh);
        }

        // ✅ 3. Tạo đối tượng hóa đơn
        HoaDon hd = HoaDon.builder()
                .maHD(maHD)
                .khachHang(kh)
                .diaChi(diaChi)
                .nhanVien(null) // Có thể để null vì Allow Nulls = true
                .ngayLap(LocalDate.now())
                .trangThai("Chờ duyệt")
                .tongTien(BigDecimal.ZERO)
                .build();

        hoaDonRepository.save(hd);

        BigDecimal tongTien = BigDecimal.ZERO;

        // ✅ 4. Lặp qua giỏ hàng và tạo chi tiết hóa đơn
        for (Map.Entry<String, Integer> entry : cartMap.entrySet()) {
            String maSP = entry.getKey();
            Integer soLuong = entry.getValue();

            SanPham sp = sanPhamRepository.findById(maSP)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm có mã: " + maSP));

            // Kiểm tra số lượng tồn kho
            if (sp.getSoLuongTon() == null || sp.getSoLuongTon() < soLuong) {
                throw new RuntimeException("Sản phẩm " + sp.getTenSP() + " không đủ hàng trong kho!");
            }

            // Trừ tồn kho
            sp.setSoLuongTon(sp.getSoLuongTon() - soLuong);
            sanPhamRepository.save(sp);

            // ✅ Tạo chi tiết hóa đơn
            ChiTietHDKey id = new ChiTietHDKey(maHD, maSP);
            ChiTietHD ct = ChiTietHD.builder()
                    .id(id)
                    .hoaDon(hd)
                    .sanPham(sp)
                    .soLuong(soLuong)
                    .donGia(sp.getDonGia())
                    .build();

            // ✅ Tính thành tiền
            tongTien = tongTien.add(ct.getThanhTien());

            chiTietHDRepository.save(ct);
        }

        // ✅ 5. Cập nhật tổng tiền và lưu hóa đơn
        hd.setTongTien(tongTien);
        hoaDonRepository.save(hd);

        return hd;
    }

    private DiaChi createDefaultAddress(KhachHang khachHang) {
        String maDC = "DC" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        DiaChi diaChi = DiaChi.builder()
                .maDC(maDC)
                .khachHang(khachHang)
                .diaChi("Địa chỉ mặc định")
                .tinh("TP. Hồ Chí Minh")
                .huyen("Quận 1")
                .xa("Phường Bến Nghé")
                .macDinh(true)
                .build();
        
        return diaChiRepository.save(diaChi);
    }
}