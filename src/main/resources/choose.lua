---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by Ken-Chy.
--- DateTime: 2023/2/27 14:42
---

local studentId = KEYS[1]
local courseClassId = KEYS[2]
local credits = tonumber(ARGV[1])

--- 获取学生已选学分和最大可选学分
local chosenCreditKey = "student:credits:chosen:" .. studentId
local chosenCredit = tonumber(redis.call("get", chosenCreditKey))
local maxCreditKey = "student:credits:max:" .. studentId
local maxCredit = tonumber(redis.call("get", maxCreditKey))

--- 学分不够选择
if chosenCredit + credits > maxCredit then
    return 1
end

--- 获取课程已选人数和容量
local chosenCourseClassKey = "course:chosen:" .. courseClassId
local chosenCourseClass = tonumber(redis.call("get", chosenCourseClassKey))
local maxCourseClassKey = "course:max:" .. courseClassId
local maxCourseClass = tonumber(redis.call("get", maxCourseClassKey))

--- 课程容量不足
if chosenCourseClass >= maxCourseClass then
    return 2
end

--- 选课
redis.call("set", chosenCreditKey, tostring(chosenCredit + credits))
redis.call("set", chosenCourseClassKey, tostring(chosenCourseClass + 1))
redis.call("sadd", "student:class:choose:" .. studentId, tostring(courseClassId))

return 0
