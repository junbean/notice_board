package practice.notice_board.post.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import practice.notice_board.post.model.Post;
import practice.notice_board.post.repository.PostRepository;

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
        Pageable pageable = PageRequest.of(page, 10); // 특정 페이지의 데이터를 10개씩 가져오겠다는 설정
        return postRepository.findAll(pageable);    // JPA가 자동으로 LIMIT, OFFSET을 적용한 SQL을 실행
    }

    // 게시글 저장
    public void addPost(String title, String content, String author) {
        Post post = new Post(title, content, author);
        postRepository.save(post); // JPA가 자동으로 INSERT 실행
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    @Transactional
    public void updatePost(Long id, String title, String content, String username) {
        Post post = getPostById(id);
        if(!post.getAuthor().equals(username)) {
            throw new IllegalStateException("게시글 작성자만 수정할 수 있습니다");
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id, String username) {
        Post post = getPostById(id);
        if(!post.getAuthor().equals(username)) {
            throw new IllegalStateException("게시글 작성자만 삭제할 수 있습니다");
        }
        postRepository.deleteById(id);
    }

}
