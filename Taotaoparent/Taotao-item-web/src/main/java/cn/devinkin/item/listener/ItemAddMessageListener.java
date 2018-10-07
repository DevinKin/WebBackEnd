package cn.devinkin.item.listener;

import cn.devinkin.item.pojo.Item;
import cn.devinkin.pojo.TbItem;
import cn.devinkin.pojo.TbItemDesc;
import cn.devinkin.manager.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author devinkin
 * <p>Title: ItemAddMessageListener</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 22:55 2018/9/27
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${HTML_OUT_PATH}")
    private String HTML_OUT_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            // 从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            // 从消息中取商品id
            String strId = textMessage.getText();
            Long itemId = Long.parseLong(strId);
            // 等待事务提交
            Thread.sleep(1000);
            // 根据商品id查询商品信息及商品描述
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);

            // 使用freemarker生成静态页面
            Configuration configuration = freeMarkerConfigurer.createConfiguration();
            // 1. 创建模板
            Template template = configuration.getTemplate("item.ftl");
            // 2. 加载模板对象
            // 3. 准备模板需要的数据
            Map data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            // 4. 指定输出的目录和文件名
            Writer out = new FileWriter(new File(HTML_OUT_PATH + strId + ".html"));
            // 5. 生成静态页面
            template.process(data, out);
            // 关闭文件输出流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
