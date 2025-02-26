package practice.notice_board.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.notice_board.user.model.User;
import practice.notice_board.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원 가입 처리
    public void registerUser(String username, String password, String email) {
        String encryptedPassword = passwordEncoder.encode(password); // Spring Security 비밀번호 암호화
        User user = new User(username, encryptedPassword, email);
        userRepository.save(user);
    }

    // 로그인 처리
    /*
    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;        // 로그인 성공
        }
        return Optional.empty();   // 로그인 실패
    }
    */

}

/*

// 로그인 처리 - Spring Security 사용
// findByUsername은 spring security가 자동으로 호출함(내부에서 로그인 인증을 처리할 떄)
public Optional<User> findByUsername(String username) {
    Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent()) {
//            System.out.println("사용자 정보: " + user.get());
//        } else {
//            System.out.println("사용자를 찾을 수 없음");
//        }
    return user;
}

*/
