package practice.notice_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.notice_board.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적인 CRUD 메서드 제공
}
