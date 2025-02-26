package practice.notice_board.post.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity             // JPA에서 관리하는 엔티티임을 선언
@Table(name = "posts")  // 엔티티가 posts라는 테이블과 매핑되도록 설정
@Getter                 // getter 알아서 생성
@Setter                 // setter 알아서 생성
@NoArgsConstructor      // 기본 생성자를 자동으로 생성
public class Post {
    @Id     // 해당 필드가 테이블의 PK 역할
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // MySQL에서 AUTO_INCREMENT 사용
    private Long id;

    @Column(nullable = false, length = 255)     // null값 하용 불가, 길이 255로 설정
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)    // DB에서 TEXT 사용 -> 긴 글 저장 가능
    private String content;

    @Column(nullable = false)
    private String author;

    @Column(updatable = false)      // 이 필드는 한 번 저장되면 수정할 수 없음
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
