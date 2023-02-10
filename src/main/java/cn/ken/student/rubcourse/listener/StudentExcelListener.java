package cn.ken.student.rubcourse.listener;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.ken.student.rubcourse.dto.req.StudentExcel;
import cn.ken.student.rubcourse.entity.Student;
import cn.ken.student.rubcourse.mapper.StudentMapper;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/12/14 0:00
 */
@Slf4j
public class StudentExcelListener implements ReadListener<StudentExcel> {

    /**
     * 每隔5条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    
    /**
     * 缓存的数据
     */
    private List<StudentExcel> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private StudentMapper studentMapper;

    public StudentExcelListener(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    // 每一条数据解析都会来调用
    @Override
    public void invoke(StudentExcel studentExcel, AnalysisContext analysisContext) {
        cachedDataList.add(studentExcel);
        if (cachedDataList.size() >= BATCH_COUNT) {
            save();
            // 清理list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    // 所有数据解析完成了都会来调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        save();
    }
    
    private void save() {
        for (StudentExcel excel : cachedDataList) {
            Student student = new Student();
            excel.fillStudent(student);
            String salt = IdUtil.simpleUUID();
            student.setSalt(salt);
            String md5Password = DigestUtil.md5Hex(student.getPassword() + salt);
            student.setPassword(md5Password);
            log.info(JSON.toJSONString(student));
            studentMapper.insert(student);
        }
    }
}
