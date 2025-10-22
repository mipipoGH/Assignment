// java
package poly.edu.assignment.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import poly.edu.assignment.service.GioHangService;
import poly.edu.assignment.service.SanPhamService;
import poly.edu.assignment.entity.SanPham;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class WebModelAdvice {

    private final GioHangService gioHang;
    private final SanPhamService spService;

    public WebModelAdvice(GioHangService gioHang, SanPhamService spService) {
        this.gioHang = gioHang;
        this.spService = spService;
    }

    @ModelAttribute("cart")
    public List<CartItem> cart() {
        List<CartItem> list = new ArrayList<>();
        for (Map.Entry<String, Integer> e : gioHang.items().entrySet()) {
            String maSP = e.getKey();
            Integer qty = e.getValue();
            SanPham sp = spService.findById(maSP).orElse(null);
            if (sp != null) {
                list.add(new CartItem(maSP, sp.getTenSP(), sp.getDonGia(), qty));
            }
        }
        return list;
    }

    @ModelAttribute("total")
    public BigDecimal total() {
        return gioHang.total();
    }
}
