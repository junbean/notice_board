package practice.notice_board.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import practice.notice_board.model.Post;
import practice.notice_board.repository.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 모든 게시글 가져오기
    /*
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    */

    // 게시글 목록(페이지 적용)
    public Page<Post> getAllPosts(int page) {
        Pageable pageable = PageRequest.of(page, 10); // 한 페이지에 10개씩 표시
        return postRepository.findAll(pageable);
    }

    // 게시글 저장
    public void addPost(String title, String content) {
        Post post = new Post(title, content);
        postRepository.save(post); // JPA가 자동으로 INSERT 실행
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePost(Long id, String title, String content) {
        Post post = getPostById(id);
        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }
}
