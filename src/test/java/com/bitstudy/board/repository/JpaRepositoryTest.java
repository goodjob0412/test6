package com.bitstudy.board.repository;

import com.bitstudy.board.config.JpaConfig;
import com.bitstudy.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@DataJpaTest
@Import(JpaConfig.class)
class JpaRepositoryTest {
//    @Autowired private ArticleRepository articleRepository;
//    @Autowired private ArticleCommentRepository articleCommentRepository;


    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;


    // 생성자 만들기. 여기선 다른 파일에서 매개변수로 보내주는거를 받는거라서 위에랑 상관 없이 @Autowired 붙여야함
    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    /* select 테스트 */
    @DisplayName("select 테스트") // 이 메소드의 테스트 이름은 'select 테스트'
    @Test
    // 테스트 데이터가 주어진 상태에서 selecting 할때 잘 동작하는 경우
    void selectTest() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles).isNotNull().hasSize(100); // 게시글은 100개
    }

    @DisplayName("insert 테스트") // 이 메소드의 테스트 이름은 'select 테스트'
    @Test // 테스트 데이터가 주어진 상태에서 inserting 할때 잘 동작하는 경우
    void insertTest() {
        // Given
        /* 기존카운트 구하고 */
        long previousCount = articleRepository.count();

        /* Article 에 정보 넣고 */
        Article article = Article.of("new article", "new content", "#spring");

        // When - 테스트 해야 하는 내용
        /* 윗줄에서 셋팅한 article 을 insert(save) 해라 */
        Article savedArticle = articleRepository.save(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
        // 카운트를 새로 구했더니 기존 카운트에 1 더해진거랑 같냐? 라는 말임.

        /* !!주의: 이상태로 테스트 돌리면 createdAt 이거 못찾는다고 에러남.
         * 이유: jpaConfig 파일에 auditing 을 쓰겠다고 셋업을 해놨는데
         *      해당 엔티티(지금은 Article.java)에서도 auditing 을 쓴다고 명시해줘야 한다.
         *       Article.java 가서 클래스 맨 위에 어노테이션
         *           @EntityListeners(AuditingEntityListener.class) 이거 넣자
         *  */
    }
}