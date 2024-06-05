package com.bitstudy.board.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article article;// 내용. 연관관계 매핑임.
    // 연관관계를 주지 않는다고 하면 그냥
    // private Long articleId 이런식으로 관계형데이터베이스 스타일로 하면 된다.
    // 그런데 우리는 Article 과 관계를 맺고 있는걸 객체지향적으로 표현하려고 이렇게 사용해볼거다.
    private String content; // 본문

    // 메타데이터
    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자
}
