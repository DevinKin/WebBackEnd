import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author devinkin
 * <p>Title: TestFreeMarker</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 21:20 2018/9/27
 */
public class TestFreeMarker {

    @Test
    public void testFreemarker() throws Exception {
        // 1. 创建一个模板文件
        // 2. 创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 3. 设置模板所在的路径,所在目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        // 4. 设置模板的字符集,一般是utf-8
        configuration.setDefaultEncoding("UTF-8");
        // 5. 使用Configuration对象加载一个模板文件,需要指定模板文件的文件名
//        Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        // 6. 创建一个数据集,可以是pojo也可以是map,推荐使用map
        Map data = new HashMap<>();
        Student student = new Student(1,"小明", 11, "北京仓平");
        data.put("hello", "hello freemarker");
        data.put("student", student);
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "张三",11, "广东佛山"));
        studentList.add(new Student(2, "张三",12, "广东佛山"));
        studentList.add(new Student(3, "张三",13, "广东佛山"));
        studentList.add(new Student(4, "张三",14, "广东佛山"));
        studentList.add(new Student(5, "张三",15, "广东佛山"));
        studentList.add(new Student(6, "张三",16, "广东佛山"));
        studentList.add(new Student(7, "张三",17, "广东佛山"));
        studentList.add(new Student(8, "张三",18, "广东佛山"));
        data.put("studentList", studentList);
        data.put("date", new Date());
        // 7. 创建一个Writer对象,指定输出文件的路径及文件名
//        Writer out = new FileWriter(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\out\\hello.html"));
        Writer out = new FileWriter(new File("D:\\WebBackEnd\\Taotaoparent\\Taotao-item-web\\src\\main\\webapp\\out\\student.html"));
        // 8. 使用模板对象的process方法来输出文件
        template.process(data, out);
        // 9. 关闭流
        out.close();
    }
}
