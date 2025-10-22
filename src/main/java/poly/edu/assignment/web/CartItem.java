package poly.edu.assignment.web;

import java.math.BigDecimal;

public class CartItem {
    private String maSP;
    private String tenSP;
    private BigDecimal donGia;
    private int qty;

    public CartItem() {}

    public CartItem(String maSP, String tenSP, BigDecimal donGia, int qty) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.qty = qty;
    }

    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public BigDecimal getTongTien() {
        return donGia.multiply(BigDecimal.valueOf(qty));
    }
}
