package com.devinkin.demo2;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 演示值栈对象的目录结构
 * @author king
 */
public class ValueStack1Action extends ActionSupport{

    private User user = new User("小泽", "1234");

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String execute() throws Exception {
        // 使用值栈对象
//        HttpServletRequest request = ServletActionContext.getRequest();
//        ValueStack vs = (ValueStack) request.getAttribute("struts.valueStack");

        // 获取到值栈对象，先获取到ActionContext对象
        ValueStack vs = ActionContext.getContext().getValueStack();
        // 栈顶是小风，字符串
//        vs.push("小风");
        // 栈顶
//        vs.set("msg", "美美");
//        vs.set("info", "小苍");
//        System.out.println(vs);
        return SUCCESS;
    }


    /**
     * 演示从值栈中获取值
     * @return
     * @throws Exception
     */
    public String save() throws Exception {
        // 获取值栈
        ValueStack vs = ActionContext.getContext().getValueStack();
//        vs.push("美美");
//        vs.set("msg", "小风");
        // 创建User对象
//        User user = new User("小苍", "123");
//        vs.push(user);
//        vs.set("user", user);

        List<User> ulist = new ArrayList<User>();
        ulist.add(new User("熊大","123"));
        ulist.add(new User("熊而","456"));
        ulist.add(new User("熊先","456"));
//        vs.push(ulist);
        vs.set("ulist", ulist);

        // 从context栈中获取值
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("msg", "美美");

        request.getSession().setAttribute("msg", "小风");
        return SUCCESS;
    }
}
