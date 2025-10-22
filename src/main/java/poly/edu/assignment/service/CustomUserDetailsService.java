package poly.edu.assignment.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.KhachHangRepository;
import org.springframework.security.core.userdetails.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final KhachHangRepository khRepo;

    public CustomUserDetailsService(KhachHangRepository khRepo) {
        this.khRepo = khRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        KhachHang kh = khRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email));

        return User.builder()
                .username(kh.getEmail())
                .password(kh.getMatKhau()) // plain text tạm thời
                .roles("USER")
                .build();
    }
}
