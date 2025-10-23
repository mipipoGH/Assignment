package poly.edu.assignment.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import poly.edu.assignment.entity.KhachHang;
import poly.edu.assignment.repository.KhachHangRepository;
import poly.edu.assignment.service.CustomUserDetailsService;

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
        return NoOpPasswordEncoder.getInstance(); // chỉ dùng cho test
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/home", "/index",
                                "/login", "/register",
                                "/products/**", "/product/**", "/cart/**", "/checkout", "/order/**",
                                "/css/**", "/js/**", "/images/**", "/webjars/**", "/error")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            // Lấy username từ UserDetails
                            String username = authentication.getName();

                            // Nếu là Khách hàng, set session.user
                            khRepo.findByEmail(username).ifPresent(kh -> {
                                request.getSession().setAttribute("user", kh);
                            });

                            // Redirect dựa theo role
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            if (role.equals("ROLE_ADMIN")) {
                                response.sendRedirect("/admin/dashboard");
                            } else if (role.equals("ROLE_STAFF") || role.equals("ROLE_DELIVERY")) {
                                response.sendRedirect("/home/index");
                            } else {
                                response.sendRedirect("/home"); // khách hàng
                            }
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // nếu dùng H2
        return http.build();
    }
}
