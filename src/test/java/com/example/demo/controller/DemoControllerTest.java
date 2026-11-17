package com.example.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/hello 返回问候语")
    void hello() throws Exception {
        mockMvc.perform(get("/api/hello").param("name", "Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Spring!"))
                .andExpect(jsonPath("$.name").value("Spring"));
    }

    @Test
    @DisplayName("GET /api/hello 默认 name=World")
    void helloDefault() throws Exception {
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, World!"));
    }

    @Test
    @DisplayName("GET /api/time 返回指定时区时间")
    void time() throws Exception {
        mockMvc.perform(get("/api/time").param("zone", "UTC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zone").value("UTC"))
                .andExpect(jsonPath("$.formatted").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/echo 回显请求内容")
    void echo() throws Exception {
        mockMvc.perform(post("/api/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"abc\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("abc"))
                .andExpect(jsonPath("$.length").value(3))
                .andExpect(jsonPath("$.upperCase").value("ABC"));
    }

    @Test
    @DisplayName("POST /api/echo 空 message 返回 400")
    void echoInvalid() throws Exception {
        mockMvc.perform(post("/api/echo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
