package com.example.demo.dto;

/**
 * 回显接口的返回体。
 */
public record EchoResponse(String message, int length, String upperCase, String receivedAt) {
}
