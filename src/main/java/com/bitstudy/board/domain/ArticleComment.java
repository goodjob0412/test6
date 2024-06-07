package com.bitstudy.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment {
    @Id // '이게 PK다' 라고 알려주는 어노테이션 (아래 private Long id 가 pk 라는  뜻)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 auto_increment 를 걸어주는 코드임
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article;// 내용. 연관관계 매핑임.
    // 연관관계를 주지 않는다고 하면 그냥
    // private Long articleId 이런식으로 관계형데이터베이스 스타일로 하면 된다.
    // 그런데 우리는 Article 과 관계를 맺고 있는걸 객체지향적으로 표현하려고 이렇게 사용해볼거다.
    // 그러기 위해 필요한건 단방향(N:1) 이라는 뜻의 @ManyToOne 애너테이션이고, 이건 필수값이다 라는 뜻으로 (optional = false)
    // 댓글을 여러개:게시글은 1개 이기 떄문에 단방향을 썼던거고
    // 저쪽(Article) 에서는 양방향 바인딩을 해줘야 한다.
    @Setter @Column(nullable = false, length = 500) private String content; // 본문

    // 메타데이터 - Article꺼랑 어짜피 똑같으니까 걍 복사해오자
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자


//    그리고 Article 때처럼 똑같이 constructor 만들자


    protected ArticleComment() {}

    // 내가 필요한 본문 관련 정보만 가진 생성자 만들자 (여기서는 사용자가 입력하는 값)
    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
