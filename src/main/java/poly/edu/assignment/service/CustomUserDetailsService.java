package poly.edu.assignment.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.entity.NhanVien;
import poly.edu.assignment.repository.HoaDonRepository;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.repository.NhanVienRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final KhachHangRepository khRepo;
    private final NhanVienRepository nvRepo;
    private final HoaDonRepository hdRepo;

    public CustomUserDetailsService(KhachHangRepository khRepo, NhanVienRepository nvRepo, HoaDonRepository hdRepo) {
        this.khRepo = khRepo;
        this.nvRepo = nvRepo;
        this.hdRepo = hdRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1️⃣ Kiểm tra Khách hàng trước
        Optional<KhachHang> khOpt = khRepo.findByEmail(email);
        if (khOpt.isPresent()) {
            KhachHang kh = khOpt.get();
            return User.builder()
                    .username(kh.getEmail())
                    .password(kh.getMatKhau())
                    .roles("USER")
                    .build();
        }

        // 2️⃣ Nếu không phải KH, kiểm tra Nhân viên
        Optional<NhanVien> nvOpt = nvRepo.findByEmail(email);
        if (nvOpt.isPresent()) {
            NhanVien nv = nvOpt.get();
            String role = nv.getVaiTro();

            // Chuyển vai trò thành ROLE chuẩn Spring Security
            String springRole = "USER"; // mặc định
            if (role.equalsIgnoreCase("Admin")) {
                springRole = "ADMIN";
            } else if (role.equalsIgnoreCase("Bán hàng")) {
                springRole = "STAFF";
            } else if (role.equalsIgnoreCase("Giao hàng")) {
                springRole = "DELIVERY";
            }

            return User.builder()
                    .username(nv.getEmail())
                    .password(nv.getMatKhau())
                    .roles(springRole)
                    .build();
        }

        // 3️⃣ Không tìm thấy
        throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);
    }
}