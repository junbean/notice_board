package practice.notice_board.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.notice_board.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Spring Security가 자동 호출
}
