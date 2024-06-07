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
    }

    /* update 테스트 */
    @DisplayName("update 테스트") // 이 메소드의 테스트 이름은 'update 테스트'
    @Test // 테스트 데이터가 주어진 상태에서 updateing 할때 잘 동작하는 경우
    void updateTest() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashtag = "#springboot";
        article.setHashtag(updateHashtag);

        // When - 테스트 해야 하는 내용
        Article savedArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);

    }
    /* delete 테스트 */
    @DisplayName("delete 테스트") // 이 메소드의 테스트 이름은 'delete 테스트'
    @Test // 테스트 데이터가 주어진 상태에서 deleteing 할때 잘 동작하는 경우
    void deleteTest() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();

        long previousArticleCount =  articleRepository.count();
        long previousArticleCommentCount =  articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When - 테스트 해야 하는 내용
        /* 게시글(article) 삭제하기 */
        articleRepository.delete(article); // delete 에 마우스 올려보면 리턴타입이 void라고 나옴. 별도로 저장할 값 없어서 저장 안함


        // Then
        /* 2번에서 구한거랑 지금 순간의 갯수 비교해서 1 차이나면 테스트 통과한거임. */

        /** 현재 게시글(articleRepository) 의 개수(count()) 가 아까구한 previousArticleCount 보다 1 적으면 테스트 통과 라는 뜻 */
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        /** 현재 게시글에 대한 댓글(articleCommentRepository) 개수(count()) 가 아까 구한 previousArticleCommentCount */
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}