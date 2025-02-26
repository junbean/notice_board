package practice.notice_board.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import practice.notice_board.post.model.Post;
import practice.notice_board.post.service.PostService;

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

    // 글 작성 페이지 (로그인한 사용자만 접근 가능)
    /*
    @GetMapping("/new")
    public String newPostForm(HttpSession session) {
        // 세션에서 로그인한 사용자 정보 확인
        if(session.getAttribute("loggedInUser") == null) {
            return" redirect:/users/login";
        }
        return "post-form";
    }
    */
    
    // 글 작성 페이지
    @GetMapping("/new")
    public String newPostForm() {
        return "post-form";
    }

    // 글 작성 처리 (로그인한 사용자만 가능)
    // /new?title=값&content=값
    /*
    @PostMapping("/new")
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content
    ) {
        postService.addPost(title, content);
        return "redirect:/posts"; // 글 작성 후 목록 페이지로 이동
    }
    */
    
    // 글 작성 처리
    /*
    @PostMapping("/new")
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            HttpSession session
    ) {
        var user = session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/users/login";
        }
        String username = ((User) user).getUsername();
        postService.addPost(title, content, username);
        return "redirect:/posts";
    }
    */

    @PostMapping
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content
    ) {
        // 현재 로그인한 사용자 정보 가져오기
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        postService.addPost(title, content, username);
        return "redirect:/posts";
    }
    
    // 게시글 상세 페이지
    @GetMapping("/{id}")
    public String viewPost(@PathVariable(name = "id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post-detail"; // post-detail.html 렌더링
    }

    // 게시글 수정 페이지
    /*
    @GetMapping("/{id}/edit")
    public String editPostForm(
            @PathVariable(name = "id") Long id,
            Model model
    ) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post-edit";
    }
    */

    // 글 수정 페이지 (작성자만 가능)
    /*
    @GetMapping("/{id}/edit")
    public String editPostForm(
            @PathVariable(name = "id") Long id,
            Model model,
            HttpSession session
    ) {
        var user = session.getAttribute("loggedInUser");    // 세션에서 사용자 정보를 가져온다
        if(user == null) {
            return "redirect:/users/login";     // 사용자가 없는 경우
        }

        String username = ((User) user).getUsername();
        Post post = postService.getPostById(id);
        if(!post.getAuthor().equals(username)) {
            return "redirect:/posts";   // 작성자가 아니라면 목록으로 이동
        }
        model.addAttribute("post", post);
        return "post-edit";
    }
    */

    // 게시글 수정 페이지
    @GetMapping("/{id}/edit")
    public String editPostForm(
            @PathVariable(name = "id") Long id,
            Model model
    ) {
        Post post = postService.getPostById(id);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 작성자만 수정 가능
        if(!post.getAuthor().equals(username)) {
            return "redirect:/posts";
        }

        model.addAttribute("post", post);
        return "post-edit";
    }

    // 게시글 수정 처리
    /*
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
    */

    // 게시글 수정 처리 (작성자만 가능)
    /*
    @PostMapping("/{id}/update")
    public String updatePost(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            HttpSession session
    ) {
        var user = session.getAttribute("loggedInUser");
        if(user == null) {
            return "redirect:/users/login";     // 사용자가 없는 경우
        }

        String username = ((User) user).getUsername();
        postService.updatePost(id, title, content, username);
        return "redirect:/posts/" + id;
    }
    */

    // 게시글 수정 처리 (작성자만 가능)
    public String updatePost(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.updatePost(id, title, content, username);
        return "redirect:/posts/" + id;
    }


    // 게시글 삭제 기능
    /*
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return "redirect:/posts"; // 삭제 후 목록 페이지로 이동
    }
    */

    // 게시글 삭제 기능
    /*
    @PostMapping("/{id}/delete")
    public String deletePost(
            @PathVariable(name = "id") Long id,
            HttpSession session
    ) {
        var user = session.getAttribute("loggedInUser");
        if(user == null) {
            return "redirect:/users/login";     // 사용자가 없는 경우
        }

        String username = ((User) user).getUsername();
        postService.deletePost(id, username);
        return "redirect:/posts";
    }
    */

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable(name = "id") Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        postService.deletePost(id, username);
        return "redirect:/posts";
    }
}
