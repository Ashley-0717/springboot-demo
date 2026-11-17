package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 回显接口的请求体。
 */
public record EchoRequest(@NotBlank(message = "message 不能为空") String message) {
}
