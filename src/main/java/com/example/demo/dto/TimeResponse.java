package com.example.demo.dto;

/**
 * 时间接口的返回体。
 */
public record TimeResponse(String formatted, long epochMillis, String zone, String offset) {
}
