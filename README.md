# 学生选课系统 (Student Course Choosing)

基于 Spring Boot 的学生选课系统，支持学生端选课和管理端课程管理，采用 Redis + Lua 脚本实现高并发选课，RabbitMQ 异步处理消息通知。

## 技术栈

- **后端框架**: Spring Boot 2.7.5 + Java 17
- **ORM**: MyBatis-Plus 3.5.2
- **数据库**: MySQL + Redis
- **消息队列**: RabbitMQ
- **实时通信**: WebSocket
- **API 文档**: Knife4j (Swagger)
- **工具库**: Hutool、FastJSON、EasyExcel、Lombok

## 项目结构

```
src/main/java/cn/ken/student/rubcourse/
├── annotation/       # 自定义注解（管理员鉴权、参数校验）
├── aop/              # AOP 切面（管理员权限、日志记录）
├── common/           # 公共模块
│   ├── constant/     # 常量定义
│   ├── entity/       # 通用实体（分页、Result、Redis 分布式锁）
│   ├── enums/        # 枚举（错误码、消息类型）
│   ├── exception/    # 自定义异常
│   └── util/         # 工具类（缓存、雪花 ID、验证码、API 签名验证等）
├── config/           # 配置类（CORS、Redis、RabbitMQ、Swagger、WebSocket）
├── controller/
│   ├── frontend/     # 学生端接口（登录、选课、课程查询、学分、消息）
│   └── sys/          # 管理端接口（课程、班级、院系、学生、公告、日志管理）
├── handle/           # 全局异常处理、MyBatis 自动填充
├── interceptor/      # Token 拦截器（学生端 & 管理端）
├── listener/         # 监听器（RabbitMQ 消息、Redis Key 过期、Excel 导入）
├── mapper/           # MyBatis Mapper 接口
├── model/
│   ├── dto/          # 数据传输对象（请求/响应）
│   └── entity/       # 数据库实体
└── service/          # 业务逻辑层（接口 + 实现）
```

## 核心功能

### 学生端
- 登录认证（Token + 拦截器）
- 课程查询与筛选
- **高并发选课 / 退课**（Redis Lua 脚本保证原子性：学分校验 + 容量校验 + 选课操作）
- 已选课程与学分查询
- 消息通知（WebSocket 实时推送）

### 管理端
- 管理员登录与权限控制（AOP 注解鉴权）
- 课程、课程班级、课程依赖关系管理
- 选课轮次管理
- 院系 / 专业 / 班级管理
- 学生管理（支持 Excel 批量导入）
- 紧急调课处理
- 系统公告发布（RabbitMQ 广播通知）
- 前后端操作日志查询

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- RabbitMQ 3.x+

### 初始化数据库

```bash
mysql -u root -p < sql/schema.sql
```

### 配置

复制配置模板并修改为实际值：

```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
```

按实际环境修改 `application.yml` 中的数据库、Redis、RabbitMQ 连接信息。

### 运行

```bash
mvn spring-boot:run
```

启动后访问 API 文档：`http://localhost:8080/doc.html`

## 选课并发设计

选课操作通过 Redis Lua 脚本（`choose.lua`）保证原子性：

1. 检查学生已选学分是否超过上限
2. 检查课程班级剩余容量
3. 扣减学分、增加已选人数、记录选课信息

三步操作在 Redis 中原子执行，避免超卖问题。选课结果通过 RabbitMQ 异步写入 MySQL。

## 许可证

[Apache License 2.0](LICENSE)
