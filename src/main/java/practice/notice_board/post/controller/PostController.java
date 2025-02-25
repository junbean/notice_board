package practice.notice_board.post.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.notice_board.post.model.Post;
import practice.notice_board.post.service.post.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 페이지
    /*
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "post-list"; // post-list.html 렌더링
    }
    */

    // 게시글 목록 페이지 (페이징 적용)
    @GetMapping
    public String listPosts(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Page<Post> postPage = postService.getAllPosts(page);

        model.addAttribute("posts", postPage.getContent()); // 현재 페이지의 게시글 목록을 가져옴
        model.addAttribute("currentPage", page);            // 현재 페이지 번호를 나타냄
        model.addAttribute("totalPages", postPage.getTotalPages()); // 전체 게시글 개수를 기반으로 전체 페이지 개수를 계산함
        // postPage.getTotalElements()	COUNT(*) FROM posts 결과, 전체 게시글 개수

        return "post-list"; // post-list.html 렌더링
    }

    // 글 작성 페이지
    /*
    @GetMapping("/new")
    public String newPostForm() {
        return "post-form"; // post-form.html 렌더링
    }
    */

    @GetMapping("/new")
    public String newPostForm(HttpSession session) {
        // 세션에서 로그인한 사용자 정보 확인
        if(session.getAttribute("loggedInUser") == null) {
            return" redirect:/users/login";
        }
        return "post-form";
    }

    // 글 작성 처리
    // /new?title=값?content=값
    @PostMapping("/new")
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content
    ) {
        postService.addPost(title, content);
        return "redirect:/posts"; // 글 작성 후 목록 페이지로 이동
    }

    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String viewPost(@PathVariable(name = "id") Long id, Model model) {
        System.out.println("id = " + id);
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post-detail"; // post-detail.html 렌더링
    }

    // 게시글 삭제 기능
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return "redirect:/posts"; // 삭제 후 목록 페이지로 이동
    }

    // 게시글 수정 페이지
    @GetMapping("/{id}/edit")
    public String editPostForm(
            @PathVariable(name = "id") Long id,
            Model model
    ) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post-edit";
    }

    // 게시글 수정 처리
    @PostMapping("/{id}/update")
    public String updatePost(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content
    ) {
        postService.updatePost(id, title, content);
        return "redirect:/posts/" + id;
        // return "redirect:/posts";
    }
}
