package practice.notice_board.user.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import practice.notice_board.user.model.User;
import practice.notice_board.user.repository.user.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원 가입 처리
    public void registerUser(String username, String password, String email) {
        String encryptedPassword = passwordEncoder.encode(password);
        User user = new User(username, encryptedPassword, email);
        userRepository.save(user);
    }

    // 로그인 처리
    public Optional<User> login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;        // 로그인 성공
        }
        return Optional.empty();   // 로그인 실패
    }

}
