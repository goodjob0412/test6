package com.bitstudy.board.repository;


import com.bitstudy.board.domain.ArticleComment;
import com.bitstudy.board.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>
        , QuerydslPredicateExecutor<ArticleComment>
        , QuerydslBinderCustomizer<QArticleComment>
{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true); // 선택적으로 검색 할 수 있게 true 로 바꿈
        bindings.including(root.content, root.createdAt, root.createdBy); // 이건 원하는 필드를 추가하는 부분. 엑셀 api 명세서 가서 보기. 안봐도 root. 까지 치면 자동완성으로 나옴.
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 대소문자 상관 없이 검색
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 대소문자 상관 없이 검색
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // 대소문자 상관 없이 검색
    }

}