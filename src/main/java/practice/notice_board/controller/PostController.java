package practice.notice_board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.notice_board.model.Post;
import practice.notice_board.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 페이지
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "post-list"; // post-list.html 렌더링
    }

    // 글 작성 페이지
    @GetMapping("/new")
    public String newPostForm() {
        return "post-form"; // post-form.html 렌더링
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
}
