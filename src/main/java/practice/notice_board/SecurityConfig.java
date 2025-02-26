package practice.notice_board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import practice.notice_board.user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // .csrf().disable() // CSRF 비활성화 (테스트용)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/register", "/users/login").permitAll() // 회원가입 & 로그인은 모든 사용자 접근 가능
                        .requestMatchers("/posts/new", "/posts/{id}/edit", "/posts/{id}/delete").authenticated() // 로그인한 사용자만 가능
                        .anyRequest().permitAll() // 나머지는 모두 허용
                )
                .formLogin(form -> form
                        .loginPage("/users/login") // 커스텀 로그인 페이지 사용
                        .usernameParameter("username")  // 폼 필드명 명시적 지정
                        .passwordParameter("password")
                        .defaultSuccessUrl("/posts", true) // 로그인 성공 후 게시글 목록으로 이동
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/posts") // 로그아웃 후 게시글 목록으로 이동
                        .invalidateHttpSession(true) // 세션 제거
                        .deleteCookies("JSESSIONID")
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}


/*
.logout(logout -> logout
            .logoutUrl("/users/logout")
            .logoutSuccessUrl("/posts") // 로그아웃 후 게시글 목록으로 이동
            .invalidateHttpSession(true) // 세션 제거
            .deleteCookies("JSESSIONID")
    ).exceptionHandling(ex -> ex
            .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/users/login")) // 인증되지 않은 사용자는 로그인 페이지로 리다이렉트
    );
*/