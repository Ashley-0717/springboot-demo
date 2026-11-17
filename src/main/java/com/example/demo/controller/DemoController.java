package com.example.demo.controller;

import com.example.demo.dto.EchoRequest;
import com.example.demo.dto.EchoResponse;
import com.example.demo.dto.GreetingResponse;
import com.example.demo.dto.TimeResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 演示用的三个 REST 接口：
 * <ul>
 *     <li>GET  /api/hello  —— 返回问候语</li>
 *     <li>GET  /api/time   —— 返回服务器当前时间（支持指定时区）</li>
 *     <li>POST /api/echo   —— 回显请求内容</li>
 * </ul>
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    /**
     * 接口 1：问候接口。
     * 示例：GET /api/hello?name=Spring
     */
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public GreetingResponse hello(@RequestParam(name = "name", defaultValue = "World") String name) {
        String message = "Hello, " + name + "!";
        return new GreetingResponse(message, name, Instant.now().toString());
    }

    /**
     * 接口 2：时间接口，默认返回东八区时间。
     * 示例：GET /api/time?zone=Asia/Shanghai
     */
    @GetMapping(value = "/time", produces = MediaType.APPLICATION_JSON_VALUE)
    public TimeResponse time(@RequestParam(name = "zone", defaultValue = "Asia/Shanghai") String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        return new TimeResponse(
                now.format(FORMATTER),
                now.toInstant().toEpochMilli(),
                zoneId.getId(),
                now.getOffset().getId()
        );
    }

    /**
     * 接口 3：回显接口，把请求里的 message 原样返回并附加一些派生信息。
     * 示例：POST /api/echo  {"message": "你好，Spring Boot"}
     */
    @PostMapping(value = "/echo", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EchoResponse echo(@Valid @RequestBody EchoRequest request) {
        String message = request.message() == null ? "" : request.message();
        return new EchoResponse(
                message,
                message.length(),
                message.toUpperCase(Locale.ROOT),
                Instant.now().toString()
        );
    }
}
