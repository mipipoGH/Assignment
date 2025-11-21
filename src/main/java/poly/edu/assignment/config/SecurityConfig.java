package poly.edu.assignment.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.stereotype.Service;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.service.AuthService;
import poly.edu.assignment.service.CustomUserDetailsService;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
@Service
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final KhachHangRepository khRepo;

    public SecurityConfig(CustomUserDetailsService userDetailsService, KhachHangRepository khRepo) {
        this.userDetailsService = userDetailsService;
        this.khRepo = khRepo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthService auth() {
        return new AuthService();
    }

    private String generateMaKH() {
        return "KH" + System.currentTimeMillis();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // --- PHÂN QUYỀN ---
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/home", "/index",
                        "/login", "/register",
                        "/products/**", "/product/**",
                        "/cart/**", "/checkout", "/order/**",
                        "/css/**", "/js/**", "/images/**",
                        "/webjars/**", "/error", "/oauth2/**"
                ).permitAll()

                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/staff/**").hasAnyRole("STAFF", "ADMIN")
                .requestMatchers("/delivery/**").hasAnyRole("DELIVERY", "ADMIN")

                .anyRequest().authenticated()
        );

        // --- FORM LOGIN ---
        http.formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {

                    String username = authentication.getName();

                    // Lưu thông tin khách vào session
                    khRepo.findByEmail(username).ifPresent(kh ->
                            request.getSession().setAttribute("user", kh)
                    );

                    String role = authentication.getAuthorities().iterator().next().getAuthority();

                    if (role.equals("ROLE_ADMIN")) {
                        response.sendRedirect("/admin/dashboard");
                    } else if (role.equals("ROLE_STAFF")) {
                        response.sendRedirect("/staff/home");
                    } else if (role.equals("ROLE_DELIVERY")) {
                        response.sendRedirect("/delivery/home");
                    } else {
                        response.sendRedirect("/home"); // customer
                    }
                })
                .permitAll()
        );

        // --- OAUTH2 LOGIN GOOGLE ---
        http.oauth2Login(oauth -> oauth
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {

                    DefaultOidcUser oauthUser = (DefaultOidcUser) authentication.getPrincipal();
                    String email = oauthUser.getEmail();
                    String fullName = oauthUser.getFullName();

                    // Tìm khách hàng trong DB
                    KhachHang kh = khRepo.findByEmail(email).orElse(null);

                    // Nếu chưa có → tạo mới
                    if (kh == null) {
                        kh = KhachHang.builder()
                                .maKH(generateMaKH())
                                .hoTen(fullName)
                                .email(email)
                                .sdt(null)
                                .matKhau("")              // Google login không dùng password
                                .ngayTao(LocalDate.now())
                                .build();

                        khRepo.save(kh);
                    }

                    // Lưu vào session
                    request.getSession().setAttribute("user", kh);

                    // Tạo Authentication tạm với role CUSTOMER
                    UserDetails newUser = User.withUsername(email)
                            .password("")                // password rỗng
                            .roles("CUSTOMER")
                            .build();

                    Authentication newAuth =
                            new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(newAuth);

                    response.sendRedirect("/home");
                })
        );

        // --- LOGOUT ---
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}
