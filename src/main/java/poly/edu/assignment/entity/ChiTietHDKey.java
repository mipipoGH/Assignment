package poly.edu.assignment.entity;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHDKey implements Serializable {
    private String maHD;
    private String maSP;
}
