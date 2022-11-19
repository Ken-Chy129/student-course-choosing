package cn.ken.student.rubcourse.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <pre>
 * <p></p>
 * </pre>
 *
 * @author <a href="https://github.com/kil1ua">Ken-Chy129</a>
 * @date 2022/11/19 16:25
 */
public class PageUtil {
    
    public static <T> IPage<T> getPage(Page<T> page, Integer currentPage, Integer pageSize, List<T> result) {
        page.setTotal(result.size());
        page.setCurrent(currentPage);
        page.setPages((result.size() / pageSize) + ((result.size() % pageSize == 0) ? 0 : 1));
        page.setRecords(result.subList(pageSize * (currentPage-1), Math.min(result.size(), pageSize * currentPage)));
        return page;
    }
}
