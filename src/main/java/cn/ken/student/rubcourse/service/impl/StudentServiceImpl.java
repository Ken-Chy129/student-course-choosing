package cn.ken.student.rubcourse.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.common.constant.GithubConstants;
import cn.ken.student.rubcourse.common.constant.RedisConstant;
import cn.ken.student.rubcourse.common.entity.Result;
import cn.ken.student.rubcourse.common.enums.ErrorCodeEnums;
import cn.ken.student.rubcourse.common.exception.BusinessException;
import cn.ken.student.rubcourse.common.util.*;
import cn.ken.student.rubcourse.mapper.StudentCourseMapper;
import cn.ken.student.rubcourse.model.dto.req.StudentExcel;
import cn.ken.student.rubcourse.model.dto.req.StudentLoginReq;
import cn.ken.student.rubcourse.model.dto.req.StudentOnConditionReq;
import cn.ken.student.rubcourse.model.dto.sys.resp.StudentResp;
import cn.ken.student.rubcourse.model.entity.Class;
import cn.ken.student.rubcourse.model.entity.Student;
import cn.ken.student.rubcourse.model.entity.StudentCourse;
import cn.ken.student.rubcourse.model.entity.StudentCredits;
import cn.ken.student.rubcourse.listener.StudentExcelListener;
import cn.ken.student.rubcourse.mapper.ClassMapper;
import cn.ken.student.rubcourse.mapper.StudentCreditsMapper;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import cn.ken.student.rubcourse.service.IChooseRoundService;
import cn.ken.student.rubcourse.service.IStudentService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.extension.toolkit.SimpleQuery.selectList;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author Ken-Chy129
 * @since 2022-11-16
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {
    
    @Autowired
    private IChooseRoundService chooseRoundService;

    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private StudentCreditsMapper studentCreditsMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public Result addStudent(HttpServletRequest httpServletRequest, Student student) throws Exception {
        Student selectById = studentMapper.selectById(student.getId());
        if (selectById != null) {
            throw new BusinessException(ErrorCodeEnums.STUDENT_EXISTS);
        }
        LambdaQueryWrapper<Class> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Class::getId, student.getClassId());
        Class result = classMapper.selectOne(queryWrapper);
        if (result == null) {
            throw new BusinessException(ErrorCodeEnums.CLASS_NOT_EXISTS);
        }
        String salt = IdUtil.simpleUUID();
        student.setSalt(salt);
        String md5Password = DigestUtil.md5Hex(student.getPassword() + salt);
        student.setPassword(md5Password);
        studentMapper.insert(student);
        // 插入各个学期的学分
        String substring = student.getId().toString().substring(0, 4);
        for (int i = 0; i <= 3; i++) {
            int integer = Integer.parseInt(substring) + i;
            for (int j = 1; j <= 2; j++) {
                Integer semester = Integer.valueOf(integer + String.valueOf(j));
                StudentCredits studentCredits = new StudentCredits(SnowflakeUtil.nextId(), student.getId(), semester, BigDecimal.valueOf(22), BigDecimal.ZERO);
                studentCreditsMapper.insert(studentCredits);
            }
        }
        return Result.success(student);
    }

    @Override
    public Result getStudentById(HttpServletRequest httpServletRequest, Long id) {
        Student selectById = studentMapper.selectById(id);
        selectById.setPhone(StringUtils.phoneDesensitization(selectById.getPhone()));
        selectById.setName(StringUtils.nameDesensitization(selectById.getName()));
        selectById.setIdCard(StringUtils.custNoDesensitization(selectById.getIdCard()));
        StudentResp studentResp = new StudentResp(selectById);
        studentResp.setClassName(classMapper.selectById(selectById.getClassId()).getClassName());
        return Result.success(studentResp);
    }

    @Override
    public Result getStudentOnCondition(HttpServletRequest httpServletRequest, StudentOnConditionReq studentOnConditionReq) {
        List<Student> studentList = studentMapper.selectByCondition(studentOnConditionReq);
        List<StudentResp> studentRespList = new ArrayList<>();
        for (Student student : studentList) {
            StudentResp studentResp = new StudentResp(student);
            studentResp.setPhone(StringUtils.phoneDesensitization(studentResp.getPhone()));
            studentResp.setName(StringUtils.nameDesensitization(studentResp.getName()));
            studentResp.setIdCard(StringUtils.custNoDesensitization(studentResp.getIdCard()));
            studentResp.setClassName(classMapper.selectById(student.getClassId()).getClassName());
            studentRespList.add(studentResp);
        }
        IPage<StudentResp> page = PageUtil.getPage(new Page<>(), studentOnConditionReq.getPageNo(), studentOnConditionReq.getPageSize(), studentRespList);
        return Result.success(page);
    }

    @Override
    public Result login(HttpServletRequest httpServletRequest, StudentLoginReq studentLoginReq) {
        Student selectById = studentMapper.selectById(studentLoginReq.getId());
        if (selectById == null || selectById.getStatus() == 1) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        String salt = selectById.getSalt();
        String md5Password = DigestUtil.md5Hex(studentLoginReq.getPassword() + salt);
        if (!md5Password.equals(selectById.getPassword())) {
            return Result.fail(ErrorCodeEnums.ACCOUNT_PASSWORD_ERROR);
        }
        Long token = SnowflakeUtil.nextId();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", selectById.getId().toString());
        hashMap.put("name", selectById.getName());
        hashMap.put("classId", selectById.getClassId().toString());
        redisTemplate.opsForValue().set(RedisConstant.STUDENT_TOKEN_PREFIX + token.toString(), JSON.toJSONString(hashMap), 86400, TimeUnit.SECONDS);
        redisTemplate.delete(RedisConstant.CHECK_CODE_PREFIX + studentLoginReq.getId());
        hashMap.put("token", token.toString());
        return Result.success(hashMap);
    }

    @Override
    public Result getCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Long studentId) throws IOException {
        StringBuilder randomString = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            String rand = ValidateCodeUtil.getRandomString(new Random().nextInt(62));
            randomString.append(rand);
        }
        redisTemplate.opsForValue().set((RedisConstant.CHECK_CODE_PREFIX + studentId), randomString.toString(), 120, TimeUnit.SECONDS);
        BufferedImage image = ValidateCodeUtil.getRandomCodeImage(randomString.toString());
        ImageIO.write(image, "PNG", httpServletResponse.getOutputStream());
        return null;
    }

    @Override
    public Result logout(HttpServletRequest httpServletRequest, Long token) {
        redisTemplate.delete(RedisConstant.STUDENT_TOKEN_PREFIX + token.toString());
        return Result.success();
    }

    @Override
    public Result batchAddStudent(HttpServletRequest httpServletRequest, MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StudentExcel.class, new StudentExcelListener(studentMapper)).sheet().doRead();
        return Result.success();
    }

    @Override
    public Result getGithubUrl(HttpServletRequest httpServletRequest) {
        String state = UUID.randomUUID().toString().replace("-", "");
        String url = String.format(GithubConstants.CODE_URL, GithubConstants.CLIENT_ID, state, GithubConstants.CALLBACK);
        redisTemplate.opsForValue().set("STATE_" + state, "1", 5, TimeUnit.MINUTES);
        return Result.success(url);
    }

    @Override
    public Result githubCallback(HttpServletRequest httpServletRequest, String code, String state) throws Exception {
        // 校验state，确保是自己发出的请求
        if (redisTemplate.opsForValue().get("STATE_"+state) != null) {
            throw new BusinessException("state值不合法");
        }
        // 通过github返回的code请求获取accessToken
        String getTokenUrl = String.format(GithubConstants.TOKEN_URL, GithubConstants.CLIENT_ID, GithubConstants.CLIENT_SECRET, code, GithubConstants.CALLBACK);
        String tokenResponse = HttpClientUtils.doGet(getTokenUrl);
        if (tokenResponse == null) {
            throw new BusinessException("请求回调失败");
        }
        String accessToken = HttpClientUtils.parseResponseEntity(tokenResponse).get("access_token");
        
        // 通过accessToken获取用户数据
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Authorization", "token " + accessToken);
        String infoResponse = HttpClientUtils.doGetWithHeaders(GithubConstants.USER_INFO_URL, headers);
        if (infoResponse == null) {
            throw new BusinessException("获取信息失败");
        }
        Map<String, String> responseMap = HttpClientUtils.parseResponseEntityJson(infoResponse);
        
        // todo:校验用户是否登录
        return Result.success();
    }

    @Override
    public void preheatStudentInfo() throws BusinessException {
        List<Student> studentList = studentMapper.selectList(new LambdaQueryWrapper<Student>().eq(Student::getStatus, 0));
        Integer semester = chooseRoundService.getPresentRound().getSemester();
        for (Student student : studentList) {
            StudentCredits studentCredits = studentCreditsMapper.selectByStudentAndSemester(student.getId(), semester);
            // 缓存学生已选学分
            redisTemplate.opsForValue().set(RedisConstant.STUDENT_CREDITS_CHOSEN + student.getId(), studentCredits.getChooseSubjectCredit().toString());
            // 缓存学生最大可选学分
            redisTemplate.opsForValue().set(RedisConstant.STUDENT_CREDITS_MAX + student.getId(), studentCredits.getMaxSubjectCredit().toString());
        }
    }

}
