package com.example.demo.dto;

/**
 * 问候接口的返回体。
 */
public record GreetingResponse(String message, String name, String timestamp) {
}
