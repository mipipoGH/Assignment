package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.*;
import poly.edu.assignment.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class HoaDonService {

    private final HoaDonRepository hdRepo;
    private final ChiTietHDRepository cthdRepo;
    private final SanPhamRepository spRepo;
    private final KhachHangRepository khRepo;

    public HoaDonService(
            HoaDonRepository hdRepo,
            ChiTietHDRepository cthdRepo,
            SanPhamRepository spRepo,
            KhachHangRepository khRepo
    ) {
        this.hdRepo = hdRepo;
        this.cthdRepo = cthdRepo;
        this.spRepo = spRepo;
        this.khRepo = khRepo;
    }

    public java.util.List<HoaDon> findAll() {
        return hdRepo.findAll();
    }

    public java.util.Optional<HoaDon> findById(String maHD) {
        return hdRepo.findById(maHD);
    }

    public HoaDon save(HoaDon hoaDon) {
        return hdRepo.save(hoaDon);
    }

    public void delete(String maHD) {
        hdRepo.deleteById(maHD);
    }
    /**
     * Đặt hàng (tạo hóa đơn + chi tiết hóa đơn)
     */
    public HoaDon placeOrder(KhachHang kh, Map<String, Integer> cartItems) {
        // Tạo mã hóa đơn ngẫu nhiên
        String maHD = "HD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Lấy lại khách hàng từ DB (đảm bảo entity quản lý bởi JPA)
        KhachHang customer = khRepo.findById(kh.getMaKH())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        // Tạo hóa đơn
        HoaDon hd = new HoaDon();
        hd.setMaHD(maHD);
        hd.setKhachHang(customer);
        hd.setNgayLap(LocalDate.now());
        hd.setTrangThai("Chờ duyệt");
        hd.setTongTien(BigDecimal.ZERO);

        hdRepo.save(hd); // lưu trước để có thể gắn vào chi tiết

        BigDecimal total = BigDecimal.ZERO;

        for (var e : cartItems.entrySet()) {
            String maSP = e.getKey();
            int qty = e.getValue();

            SanPham sp = spRepo.findById(maSP)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm: " + maSP));

            // Tạo khóa tổng hợp cho chi tiết hóa đơn
            ChiTietHDKey key = new ChiTietHDKey(maHD, maSP);
            ChiTietHD ct = new ChiTietHD();
            ct.setId(key);
            ct.setHoaDon(hd);
            ct.setSanPham(sp);
            ct.setSoLuong(qty);
            ct.setDonGia(sp.getDonGia());


            cthdRepo.save(ct);

            // Cập nhật tồn kho
            if (sp.getSoLuongTon() != null && sp.getSoLuongTon() >= qty) {
                sp.setSoLuongTon(sp.getSoLuongTon() - qty);
                spRepo.save(sp);
            } else {
                throw new RuntimeException("Sản phẩm " + sp.getTenSP() + " không đủ hàng tồn!");
            }

            // Cộng tổng tiền
            total = total.add(sp.getDonGia().multiply(BigDecimal.valueOf(qty)));

        }

        // Cập nhật tổng tiền hóa đơn
        hd.setTongTien(total);
        return hdRepo.save(hd);
    }
    public java.util.Map<String, Long> getOrderStatusStatistics() {
        java.util.List<HoaDon> orders = findAll();
        return orders.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        hd -> hd.getTrangThai() != null ? hd.getTrangThai() : "Không xác định",
                        java.util.stream.Collectors.counting()
                ));
    }

}
