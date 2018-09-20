package cn.devinkin.pagehelper;

import cn.devinkin.mapper.TbItemMapper;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author devinkin
 * <p>Title: </p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 16:16 2018/9/19
 */
public class TestPageHelper {
    @Test
    public void testPageHelper() throws Exception {
        // 1. 在执行查询之前配置分页条件,使用PageHelper的静态方法
        PageHelper.startPage(1, 10);
        // 2. 执行查询
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        // 创建Example对象
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        // 3. 取分页信息.使用PageInfo对象获取.
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println("总记录数: " + pageInfo.getTotal());
        System.out.println("总页数: " + pageInfo.getPages());
        System.out.println("返回的记录数: " + list.size());
    }
}
