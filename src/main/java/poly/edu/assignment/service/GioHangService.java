package poly.edu.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.edu.assignment.entity.SanPham;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class GioHangService {

    // In-memory cart (Map<ProductId, Quantity>)
    // Trong project thực tế nên lưu theo người dùng (DB hoặc session)
    private final Map<String, Integer> items = new LinkedHashMap<>();
    private final SanPhamService spService;

    public GioHangService(SanPhamService spService) {
        this.spService = spService;
    }

    /** Thêm sản phẩm vào giỏ */
    public void add(String maSP, int qty) {
        if (qty <= 0) return;
        items.merge(maSP, qty, Integer::sum);
    }

    /** Xóa sản phẩm khỏi giỏ */
    public void remove(String maSP) {
        items.remove(maSP);
    }

    /** Lấy toàn bộ sản phẩm trong giỏ (read-only map) */
    public Map<String, Integer> items() {
        return Collections.unmodifiableMap(items);
    }

    /** Tính tổng tiền giỏ hàng */
    public BigDecimal total() {
        BigDecimal sum = BigDecimal.ZERO;

        for (var e : items.entrySet()) {
            SanPham sp = spService.findById(e.getKey()).orElse(null);
            if (sp != null && sp.getDonGia() != null) {
                sum = sum.add(sp.getDonGia().multiply(BigDecimal.valueOf(e.getValue())));
            }
        }

        return sum;
    }


    /** Xóa toàn bộ giỏ hàng */
    public void clear() {
        items.clear();
    }
}
