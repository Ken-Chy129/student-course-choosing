-- ==========================================
-- 学生选课系统 数据库初始化脚本
-- ==========================================

CREATE DATABASE IF NOT EXISTS `student_course` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `student_course`;

-- -------------------------------------------
-- 学院表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_college`;
CREATE TABLE `scc_college` (
    `id`           SMALLINT     NOT NULL AUTO_INCREMENT COMMENT '学院id',
    `college_name` VARCHAR(50)  NOT NULL COMMENT '学院名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学院表';

-- -------------------------------------------
-- 系表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_department`;
CREATE TABLE `scc_department` (
    `id`              INT          NOT NULL AUTO_INCREMENT COMMENT '系id',
    `college_id`      INT          NOT NULL COMMENT '院id',
    `department_name` VARCHAR(50)  NOT NULL COMMENT '系名',
    PRIMARY KEY (`id`),
    KEY `idx_college_id` (`college_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系表';

-- -------------------------------------------
-- 专业表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_subject`;
CREATE TABLE `scc_subject` (
    `id`            INT          NOT NULL AUTO_INCREMENT COMMENT '专业id',
    `department_id` INT          NOT NULL COMMENT '系id',
    `subject_name`  VARCHAR(50)  NOT NULL COMMENT '专业名',
    PRIMARY KEY (`id`),
    KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='专业表';

-- -------------------------------------------
-- 班级表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_class`;
CREATE TABLE `scc_class` (
    `id`                INT          NOT NULL AUTO_INCREMENT COMMENT '班级id',
    `college_name`      VARCHAR(50)  NOT NULL COMMENT '学院名称',
    `department_name`   VARCHAR(50)  NOT NULL COMMENT '系名',
    `subject_name`      VARCHAR(50)  NOT NULL COMMENT '专业名',
    `year`              INT          NOT NULL COMMENT '年份',
    `class_no`          INT          NOT NULL COMMENT '班级序号',
    `class_name`        VARCHAR(100) NOT NULL COMMENT '班级名称',
    `graduation_credits` INT         NOT NULL DEFAULT 0 COMMENT '毕业所需学分',
    `create_time`       DATETIME     NOT NULL COMMENT '创建时间',
    `update_time`       DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- -------------------------------------------
-- 学生表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_student`;
CREATE TABLE `scc_student` (
    `id`          BIGINT       NOT NULL COMMENT '学生学号',
    `name`        VARCHAR(50)  NOT NULL COMMENT '学生姓名',
    `gender`      INT          NOT NULL DEFAULT 0 COMMENT '性别(0-女,1-男)',
    `class_id`    INT          NOT NULL COMMENT '班级代码',
    `status`      INT          NOT NULL DEFAULT 0 COMMENT '学生状态(0-正常,1-毕业,2-其他)',
    `email`       VARCHAR(100) DEFAULT NULL COMMENT '学生邮箱',
    `phone`       VARCHAR(11)  DEFAULT NULL COMMENT '手机号码',
    `id_card`     VARCHAR(18)  DEFAULT NULL COMMENT '身份证',
    `password`    VARCHAR(100) NOT NULL COMMENT '密码',
    `salt`        VARCHAR(50)  DEFAULT NULL COMMENT '密码盐',
    `create_time` DATETIME     NOT NULL COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- -------------------------------------------
-- 课程表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_course`;
CREATE TABLE `scc_course` (
    `id`           VARCHAR(50)    NOT NULL COMMENT '课程编号',
    `course_name`  VARCHAR(100)   NOT NULL COMMENT '课程名称',
    `campus`       VARCHAR(50)    DEFAULT NULL COMMENT '所在校区',
    `college`      VARCHAR(50)    DEFAULT NULL COMMENT '排课单位',
    `type`         VARCHAR(50)    DEFAULT NULL COMMENT '课程类别',
    `general_type` VARCHAR(50)    DEFAULT NULL COMMENT '通识课类别',
    `credit`       DECIMAL(4,1)   NOT NULL DEFAULT 0 COMMENT '课程学分',
    `class_num`    INT            NOT NULL DEFAULT 0 COMMENT '可选班级数',
    `create_time`  DATETIME       NOT NULL COMMENT '创建时间',
    `update_time`  DATETIME       NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- -------------------------------------------
-- 课程班表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_course_class`;
CREATE TABLE `scc_course_class` (
    `id`           BIGINT       NOT NULL COMMENT '课程班id',
    `course_id`    VARCHAR(50)  NOT NULL COMMENT '课程id',
    `course_name`  VARCHAR(100) NOT NULL COMMENT '课程名称',
    `is_mooc`      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否慕课',
    `language`     VARCHAR(20)  DEFAULT NULL COMMENT '授课语言',
    `choosing_num` INT          NOT NULL DEFAULT 0 COMMENT '选课人数',
    `capacity`     INT          NOT NULL DEFAULT 0 COMMENT '课程容量',
    `exam_type`    VARCHAR(20)  DEFAULT NULL COMMENT '考试类型',
    `exam_time`    VARCHAR(50)  DEFAULT NULL COMMENT '考试时间',
    `teacher`      VARCHAR(50)  DEFAULT NULL COMMENT '授课教师',
    `is_deleted`   TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`  DATETIME     NOT NULL COMMENT '创建时间',
    `update_time`  DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程班表';

-- -------------------------------------------
-- 课程时间地点表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_course_timeplace`;
CREATE TABLE `scc_course_timeplace` (
    `id`              BIGINT      NOT NULL COMMENT '时间地点id',
    `course_class_id` BIGINT      NOT NULL COMMENT '课程班编号',
    `duration_time`   VARCHAR(50) DEFAULT NULL COMMENT '课程持续时间',
    `week_day`        SMALLINT    NOT NULL COMMENT '每周星期几上课',
    `day_no`          VARCHAR(20) DEFAULT NULL COMMENT '每天第几节课上课',
    `place`           VARCHAR(100) DEFAULT NULL COMMENT '上课地点',
    `is_deleted`      TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`     DATETIME    NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_class_id` (`course_class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程时间地点表';

-- -------------------------------------------
-- 课程依赖（先修课）表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_course_dependence`;
CREATE TABLE `scc_course_dependence` (
    `id`             BIGINT      NOT NULL COMMENT '依赖id',
    `course_id`      VARCHAR(50) NOT NULL COMMENT '课程编号',
    `pre_course_id`  VARCHAR(50) NOT NULL COMMENT '先修课程编号',
    `is_deleted`     TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`    DATETIME    NOT NULL COMMENT '创建时间',
    `update_time`    DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程依赖表';

-- -------------------------------------------
-- 紧急调课表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_course_emergency`;
CREATE TABLE `scc_course_emergency` (
    `id`             BIGINT      NOT NULL COMMENT '紧急调课id',
    `course_id`      VARCHAR(50) NOT NULL COMMENT '课程编号',
    `only_to_class`  INT         DEFAULT NULL COMMENT '仅开放给班级',
    `only_to_grade`  INT         DEFAULT NULL COMMENT '只开放给年级',
    `is_deleted`     TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`    DATETIME    NOT NULL COMMENT '创建时间',
    `update_time`    DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='紧急调课表';

-- -------------------------------------------
-- 班级课程关联表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_class_course`;
CREATE TABLE `scc_class_course` (
    `id`              BIGINT      NOT NULL COMMENT '关联id',
    `class_id`        INT         NOT NULL COMMENT '班级编号',
    `course_id`       VARCHAR(50) NOT NULL COMMENT '课程编号',
    `commended_time`  INT         DEFAULT NULL COMMENT '推荐选课时间',
    `is_must`         TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否必修',
    `is_deleted`      TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`     DATETIME    NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME    NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_class_id` (`class_id`),
    KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级课程关联表';

-- -------------------------------------------
-- 选课轮次表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_choose_round`;
CREATE TABLE `scc_choose_round` (
    `id`          INT      NOT NULL AUTO_INCREMENT COMMENT '选课轮次id',
    `semester`    INT      NOT NULL COMMENT '学期',
    `round_no`    INT      NOT NULL COMMENT '轮次',
    `start_time`  DATETIME NOT NULL COMMENT '起始时间',
    `end_time`    DATETIME NOT NULL COMMENT '终止时间',
    `tips`        VARCHAR(500) DEFAULT NULL COMMENT '提示信息',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课轮次表';

-- -------------------------------------------
-- 学生选课记录表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_student_course`;
CREATE TABLE `scc_student_course` (
    `id`              BIGINT       NOT NULL COMMENT '记录id',
    `student_id`      BIGINT       NOT NULL COMMENT '学生学号',
    `course_class_id` BIGINT       NOT NULL COMMENT '课程班编号',
    `semester`        INT          NOT NULL COMMENT '选择学期',
    `credits`         DECIMAL(4,1) NOT NULL DEFAULT 0 COMMENT '课程学分',
    `is_deleted`      TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`     DATETIME     NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_student_id` (`student_id`),
    KEY `idx_course_class_id` (`course_class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生选课记录表';

-- -------------------------------------------
-- 学生学分表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_student_credits`;
CREATE TABLE `scc_student_credits` (
    `id`                    BIGINT       NOT NULL COMMENT '学分记录id',
    `student_id`            BIGINT       NOT NULL COMMENT '学生学号',
    `semester`              INT          NOT NULL COMMENT '学期',
    `max_subject_credit`    DECIMAL(5,1) NOT NULL DEFAULT 0 COMMENT '最高主修学分',
    `choose_subject_credit` DECIMAL(5,1) NOT NULL DEFAULT 0 COMMENT '已选主修学分',
    `create_time`           DATETIME     NOT NULL COMMENT '创建时间',
    `update_time`           DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_student_id` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生学分表';

-- -------------------------------------------
-- 系统管理员表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_sys_manager`;
CREATE TABLE `scc_sys_manager` (
    `id`           INT          NOT NULL AUTO_INCREMENT COMMENT '管理员id',
    `manager_name` VARCHAR(50)  NOT NULL COMMENT '管理员名称',
    `type`         INT          NOT NULL DEFAULT 0 COMMENT '管理员类型(0-普通,1-超级)',
    `password`     VARCHAR(100) NOT NULL COMMENT '密码',
    `salt`         VARCHAR(50)  DEFAULT NULL COMMENT '密码盐',
    `mobile_phone` VARCHAR(20)  DEFAULT NULL COMMENT '手机号码',
    `last_login`   DATETIME     DEFAULT NULL COMMENT '上次登录时间',
    `is_deleted`   TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    `create_time`  DATETIME     NOT NULL COMMENT '创建时间',
    `update_time`  DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统管理员表';

-- -------------------------------------------
-- 系统公告表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_sys_notice`;
CREATE TABLE `scc_sys_notice` (
    `id`          BIGINT       NOT NULL COMMENT '公告id',
    `student_id`  BIGINT       NOT NULL DEFAULT -1 COMMENT '接收方id,-1表示发送给全体',
    `message`     TEXT         DEFAULT NULL COMMENT '消息体',
    `status`      SMALLINT     NOT NULL DEFAULT 0 COMMENT '状态',
    `create_time` DATETIME     NOT NULL COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_student_id` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统公告表';

-- -------------------------------------------
-- 后台操作日志表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_sys_backend_log`;
CREATE TABLE `scc_sys_backend_log` (
    `id`            BIGINT       NOT NULL COMMENT '日志id',
    `type`          INT          NOT NULL DEFAULT 0 COMMENT '请求类型(0-正常,1-异常)',
    `request_ip`    VARCHAR(50)  DEFAULT NULL COMMENT '请求方ip',
    `manager_id`    INT          DEFAULT NULL COMMENT '管理员id',
    `request_api`   VARCHAR(200) DEFAULT NULL COMMENT '请求接口',
    `request_body`  TEXT         DEFAULT NULL COMMENT '请求体',
    `response_body` TEXT         DEFAULT NULL COMMENT '响应体',
    `create_time`   DATETIME     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_manager_id` (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台操作日志表';

-- -------------------------------------------
-- 前台操作日志表
-- -------------------------------------------
DROP TABLE IF EXISTS `scc_sys_frontend_log`;
CREATE TABLE `scc_sys_frontend_log` (
    `id`            BIGINT       NOT NULL COMMENT '日志id',
    `type`          INT          NOT NULL DEFAULT 0 COMMENT '请求类型(0-正常,1-异常)',
    `request_ip`    VARCHAR(50)  DEFAULT NULL COMMENT '请求ip',
    `student_id`    INT          DEFAULT NULL COMMENT '学生学号',
    `request_api`   VARCHAR(200) DEFAULT NULL COMMENT '请求接口',
    `request_body`  TEXT         DEFAULT NULL COMMENT '请求体',
    `response_body` TEXT         DEFAULT NULL COMMENT '响应体',
    `create_time`   DATETIME     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_student_id` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='前台操作日志表';

-- -------------------------------------------
-- 初始化超级管理员 (密码: admin123, 盐值需自行替换)
-- -------------------------------------------
-- INSERT INTO `scc_sys_manager` (`manager_name`, `type`, `password`, `salt`, `mobile_phone`, `is_deleted`, `create_time`, `update_time`)
-- VALUES ('admin', 1, 'your_encrypted_password', 'your_salt', '13800000000', 0, NOW(), NOW());
