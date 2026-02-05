package com.example.demo.controller;

import com.example.demo.dto.PostCreateRequest;
import com.example.demo.model.Post;
import com.example.demo.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    private Post testPost;
    private PostCreateRequest createRequest;

    @BeforeEach
    void setUp() {
        testPost = Post.builder()
                .id(1L)
                .title("테스트 게시글")
                .content("테스트 내용입니다.")
                .author("테스터")
                .createdAt(LocalDate.now())
                .build();

        createRequest = new PostCreateRequest();
        createRequest.setTitle("테스트 게시글");
        createRequest.setContent("테스트 내용입니다.");
        createRequest.setAuthor("테스터");
    }

    @Test
    @DisplayName("GET /api/posts - 모든 게시글 조회")
    void getAllPosts_ReturnsPostList() throws Exception {
        List<Post> posts = Arrays.asList(testPost);
        given(postService.getAllPosts()).willReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("테스트 게시글"))
                .andExpect(jsonPath("$[0].author").value("테스터"));
    }

    @Test
    @DisplayName("GET /api/posts/{id} - 단일 게시글 조회")
    void getPostById_ReturnsPost() throws Exception {
        given(postService.getPostById(1L)).willReturn(testPost);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("테스트 게시글"))
                .andExpect(jsonPath("$.content").value("테스트 내용입니다."));
    }

    @Test
    @DisplayName("POST /api/posts - 게시글 생성")
    void createPost_ReturnsCreatedPost() throws Exception {
        given(postService.createPost(any(PostCreateRequest.class))).willReturn(testPost);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("테스트 게시글"));
    }

    @Test
    @DisplayName("POST /api/posts - 유효성 검증 실패")
    void createPost_WithInvalidData_ReturnsBadRequest() throws Exception {
        PostCreateRequest invalidRequest = new PostCreateRequest();
        invalidRequest.setTitle("");
        invalidRequest.setContent("");
        invalidRequest.setAuthor("");

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/posts/{id} - 게시글 수정")
    void updatePost_ReturnsUpdatedPost() throws Exception {
        given(postService.updatePost(eq(1L), any(PostCreateRequest.class))).willReturn(testPost);

        mockMvc.perform(put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("DELETE /api/posts/{id} - 게시글 삭제")
    void deletePost_ReturnsNoContent() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/posts/search - 제목으로 검색")
    void searchPosts_ReturnsMatchingPosts() throws Exception {
        List<Post> posts = Arrays.asList(testPost);
        given(postService.searchByTitle("테스트")).willReturn(posts);

        mockMvc.perform(get("/api/posts/search")
                        .param("keyword", "테스트"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("테스트 게시글"));
    }
}
