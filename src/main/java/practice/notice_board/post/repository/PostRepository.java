package practice.notice_board.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.notice_board.post.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 기본적인 CRUD 메서드 제공

    //페이지 처리를 위한 메서드
    Page<Post> findAll(Pageable pageable);
}
