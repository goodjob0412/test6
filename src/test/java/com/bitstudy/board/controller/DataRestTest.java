package com.bitstudy.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

@DisplayName("Data REST - API 테스트") /* 테스트 이름 주기 */
@Transactional
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void articleAll() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk()) // 현재 상태가 200 인가 (존재하는가?)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }
    /////////////////////////////////////////////////////

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void articleOne() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles/1")) /* 테스트 데이터가 있다고 가정하고 하는거임. 1번글 하나 가져와라  */
                .andExpect(status().isOk()) // 현재 상태가 200 인가 (존재하는가?)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 게시글 댓글 리스트 조회")
    @Test
    void articleCommentAllByArticle() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles/1/articleComments")) /* 1번글의 모든 댓글들 가져와라  */
                .andExpect(status().isOk()) // 현재 상태가 200 인가 (존재하는가?)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 리스트 전체 조회")
    @Test
    void articleCommentAll() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articleComments")) /* 모든 댓글들 가져와라  */
                .andExpect(status().isOk()) // 현재 상태가 200 인가 (존재하는가?)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void articleCommentOne() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articleComments/1")) /* 1번 댓글 가져와라  */
                .andExpect(status().isOk()) // 현재 상태가 200 인가 (존재하는가?)
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }


}
