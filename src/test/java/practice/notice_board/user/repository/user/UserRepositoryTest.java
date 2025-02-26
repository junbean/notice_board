package practice.notice_board.user.repository.user;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import practice.notice_board.user.model.User;
import practice.notice_board.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("MYSQL에서 존재하는 사용자 조회 테스트")
    void test() {
        // given
        String username = "qq";

        // when
        Optional<User> user = userRepository.findByUsername(username);

        // then
        System.out.println("user = " + user.get().getUsername());
        Assertions.assertThat(user).isPresent();
        assertThat(user.get().getUsername()).isEqualTo(username);
    }
}