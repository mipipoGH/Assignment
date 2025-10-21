package poly.edu.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import poly.edu.Entity.product;
import java.util.*;
@Controller
@RequestMapping("/home")
public class HomeController {
    @RequestMapping("/index")
    public String index(Model model) {
        return "/home/index";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        return "/home/about";
    }

    @RequestMapping("/product")
    public String product(Model model) {
        return "/home/product";
    }

    @ModelAttribute("items")
    public List<product> getItems() {
        return Arrays.asList(
                new product(1L, "Điện thoại thông minh", 12000.0, "/images/image1.jpg"),
                new product(2L, "Máy tính bảng", 8500.0, "/images/image2.jpg"),
                new product(3L, "Laptop gaming", 22000.0, "/images/image3.jpg"),
                new product(4L, "Balo laptop", 350.0, "/images/image4.jpg"),
                new product(5L, "Tai nghe Bluetooth", 220.0, "/images/image5.jpg"),
                new product(6L, "Đồng hồ thời trang", 500.0, "/images/image6.jpg")
        );
    }

    @RequestMapping("/contact")
    public String contract(Model model) {
        return "/home/contact";
    }
    @RequestMapping("/login")
    public String login(Model model) {
        return "/home/login";
    }
    @RequestMapping("/register")
    public String register(Model model) {
        return "/home/register";
    }
    @RequestMapping("/cart")
    public String cart(Model model) {
        return "/home/cart";
    }
    @ModelAttribute("totalAmount")
    public double getTotalAmount() {
        double sum = 0;
        for (product p : getItems()) {
            sum += p.getPrice();
        }
        return sum;
    }
}
