package liuyang.testclient.modules.app.spring.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 依赖easyexcel
 *
 * 视频参考：
 * https://www.bilibili.com/video/BV17m4y1Q7gn?p=11&spm_id_from=333.880.my_history.page.click
 *
 * @author liuyang(wx)
 * @since 2022/3/21
 */
@Data
public class ExcelVO {
    // 编号
    @ExcelProperty("编号")
    private Integer id;
    // 姓名
    @ExcelProperty("姓名")
    private String name;
    // 性别
    @ExcelProperty("性别")
    private String gender;
    // 年龄
    @ExcelProperty("年龄")
    private Integer age;
    // 班级
    @ExcelProperty("班级")
    private String classes;
}
