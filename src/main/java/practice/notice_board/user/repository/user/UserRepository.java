package practice.notice_board.user.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.notice_board.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // 로그인 시 사용
}
