package poly.edu.assignment.entity;


import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CTGHKey implements Serializable {
    private String gioHang;
    private String sanPham;
}
