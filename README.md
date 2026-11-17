# springboot-demo

一个最小的 Spring Boot 3.5 示例项目，对外暴露三个 REST 接口，用于演示项目搭建、测试与 CI/发布流程。

## 技术栈

| 组件 | 版本 |
| --- | --- |
| JDK | 21 |
| Spring Boot | 3.5.16 |
| 构建工具 | Maven 3.9 |
| 内嵌容器 | Tomcat（spring-boot-starter-web） |

## 接口一览

| # | 方法 | 路径 | 说明 |
| --- | --- | --- | --- |
| 1 | GET | `/api/hello` | 问候接口，`name` 参数可选，默认 `World` |
| 2 | GET | `/api/time` | 服务器当前时间，`zone` 参数可选，默认 `Asia/Shanghai` |
| 3 | POST | `/api/echo` | 回显请求体中的 `message`，并返回长度与大写形式 |

### 1. GET /api/hello

```bash
curl "http://localhost:8080/api/hello?name=Spring"
```

```json
{
  "message": "Hello, Spring!",
  "name": "Spring",
  "timestamp": "2026-11-17T08:30:00.123456Z"
}
```

### 2. GET /api/time

```bash
curl "http://localhost:8080/api/time?zone=Asia/Shanghai"
```

```json
{
  "formatted": "2026-11-17 16:30:00",
  "epochMillis": 1763370600000,
  "zone": "Asia/Shanghai",
  "offset": "+08:00"
}
```

### 3. POST /api/echo

```bash
curl -X POST "http://localhost:8080/api/echo" \
  -H "Content-Type: application/json" \
  -d '{"message":"hello spring boot"}'
```

```json
{
  "message": "hello spring boot",
  "length": 17,
  "upperCase": "HELLO SPRING BOOT",
  "receivedAt": "2026-11-17T08:30:00.456789Z"
}
```

`message` 为空时返回 `400 Bad Request`（参数校验失败）。

## 本地运行

```bash
# 编译并跑单元测试
mvn clean test

# 打包
mvn clean package

# 启动
mvn spring-boot:run
# 或
java -jar target/springboot-demo-0.0.1-SNAPSHOT.jar
```

启动后服务监听 `http://localhost:8080`，健康检查：`GET /actuator/health`。

## 目录结构

```
springboot-demo
├── pom.xml
├── src
│   ├── main
│   │   ├── java/com/example/demo
│   │   │   ├── DemoApplication.java          # 启动类
│   │   │   ├── controller/DemoController.java # 三个接口
│   │   │   └── dto                          # 请求/返回体（record）
│   │   └── resources/application.properties
│   └── test/java/com/example/demo
│       └── controller/DemoControllerTest.java # MockMvc 接口测试
└── .gitignore
```
