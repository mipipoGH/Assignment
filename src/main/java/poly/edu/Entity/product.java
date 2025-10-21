package poly.edu.Entity;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class product {
    private Long id;
    private String productName;
    private Double price;
    private String image;
}
