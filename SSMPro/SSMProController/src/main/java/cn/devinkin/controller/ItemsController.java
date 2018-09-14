package cn.devinkin.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.devinkin.exception.CustomException;
import cn.devinkin.service.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.devinkin.pojo.Items;
import cn.devinkin.service.ItemsService;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @RequestMapping(value = {"/list", "/test"}, method = RequestMethod.GET)
    public ModelAndView itemsList() throws Exception {
        List<Items> list = itemsService.list();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("itemList", list);
        modelAndView.setViewName("itemList");

        return modelAndView;
    }

    /**
     * springMVC中默认支持的参数类型:也就是说在controller方法中可以加入这些参数,也可以不加入,加不加看需求
     * @PathVariable可以接受url传过来的参数
     * @param request  请求
     * @param response 响应
     * @param session  会话
     * @param model    模型,模型中放入了返回给页面的数据,model底层其实就是用的是request域来传送数据,但是对request域进行扩展
     * @return 请求页面路径
     * @throws Exception
     */
    @RequestMapping("/itemEdit/{id}/{name}")
    public String itemEdit(@PathVariable("id") Integer id, @PathVariable("name") String name,
                           HttpServletRequest request, HttpServletResponse response,
                           HttpSession session, Model model) throws Exception {
        String trueName = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        Items item = itemsService.findItemsById(id);
        if (item == null) {
            throw new CustomException("商品信息不存在!");
        }
        model.addAttribute("item", item);
        // 如果springMVC返回一个简单的string字符串,那么springMVC就认为这个字符串就是页面的名称
        return "editItem";
    }


    // SpringMVC可以直接接收基本数据类型,包括String,SpringMVC能够自动进行类型转换
    // Controller方法接收的参数的变量名称必须要等于页面上input框的name属性的值
//    public String updateItem(Integer id, String name, Float price, String detail) throws Exception {
    // SpringMVC可以直接接收pojo类型,要求页面上input的name属性名称必须与pojo类的属性名相同
    @RequestMapping("/updateitem")
//    public void updateItem(Items items, HttpServletRequest request, HttpServletResponse response) throws Exception {
    public String updateItem(MultipartFile pictureFile, Items items, Model model, HttpServletRequest request) throws Exception {
//    public String updateItem(@RequestParam("id") Integer iddddd,
//                        @RequestParam("name") String xxx, Float price, String detail) throws Exception {

        // 1. 获取图片的完整名称
        String fileStr = pictureFile.getOriginalFilename();
        // 2. 使用随机生成的字符串+原图片扩展名组成新的图片名称,防止图片重名
        String newFileName = UUID.randomUUID().toString() + fileStr.substring(fileStr.lastIndexOf("."));

        // 3. 将图片保存到硬盘
        pictureFile.transferTo(new File("F:\\SpringMVC\\upload\\temp\\" + newFileName));

        // 4. 将图片名称保存到数据库
        items.setPic(newFileName);

        items.setCreatetime(new Date());
        itemsService.updateItems(items);
        // 请求转发:浏览器中的url不变,request域中的数据能被带到转发后的方法中
//        model.addAttribute("id", items.getId());
        // springMVC中请求转发:返回的字符串以forward:开头的都是请求转发
        // 后面表示相对路径,相对于当前类指定的路径,当前路径下可以任意跳转到当前类的任意一个方法
        // forward:以/开头,表示绝对路径,绝对路径从项目名开始算
//        return "forward:/items/itemEdit";

        // 重定向:浏览器的url改变,request域中的数据不能被带到转发后的方法中

//        request.setAttribute("id", items.getId());
        return "redirect:itemEdit/" + items.getId() + "/" + newFileName;
        // 在springMVC中凡是以redirect字符串开头的返回值,都是重定向


        // 返回数据
//        request.setAttribute("items", items);
        // 指定返回的页面
//        request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);

    }

    @RequestMapping("/search")
    public String search(QueryVo vo) throws Exception {
        System.out.println(vo);
        return "";
    }


    @RequestMapping("/delAll")
    public String delAll(QueryVo vo) throws Exception {
//        如果批量删除,一堆input复选框框,可以提交数组,用来接收复选框的值
        System.out.println(vo);
        return "";
    }

    @RequestMapping("/updateAll")
    public String updateAll(QueryVo vo) throws Exception {
        System.out.println(vo);
        return "";
    }


    // 导入jacksonjar包,在controller的方法中,可以使用@RequestBody让SpringMVC将json格式的字符串自动转换成Java中的pojo
    // 页面json的key要等于java中pojo的属性名称
    // controller方法范围pojo类型的对象并且用@RequestResponse注解,springMVC会自动将pojo对象转换成json格式字符串
    @RequestMapping("/sendJson")
    @ResponseBody
    public Items json(@RequestBody Items items) throws Exception {
        System.out.println(items);
        return items;
    }
}
