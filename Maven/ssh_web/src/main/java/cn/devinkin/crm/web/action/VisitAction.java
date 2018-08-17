package cn.devinkin.crm.web.action;

/**
 * 客户拜访的控制器
 * @author king
 @Controller(value = "visitAction") = <bean id="visitAction" class="..." scope="..."></bean>
 */

import cn.devinkin.crm.domain.PageBean;
import cn.devinkin.crm.domain.User;
import cn.devinkin.crm.domain.Visit;
import cn.devinkin.crm.service.VisitService;
import cn.devinkin.crm.utils.DateToString;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;

@Controller(value = "visitAction")
@Scope(value = "prototype")
public class VisitAction extends BaseAction implements ModelDriven<Visit>{

    private Visit visit = new Visit();
    @Override
    public Visit getModel() {
        return visit;
    }

    @Resource(name = "visitService")
    private VisitService visitService;

    private String beginDate;
    private String endDate;

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 分页查询
     * 查询客户的拜访记录，根据用户的主键查询
     * select * from sale_visit where visit_user_id = ?
     * @return
     */
    public String findByPage() {
        // 先获取当前登录的用户
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");

        // 判断
        if (user == null) {
            // 配置全局结果跳转
            return LOGIN;
        }
        // 查询该用户下所有的拜访记录
        DetachedCriteria criteria = DetachedCriteria.forClass(Visit.class);
        // 添加查询的条件
        criteria.add(Restrictions.eq("user.user_id", user.getUser_id()));

        // 拼接查询的条件
        if (beginDate != null && !beginDate.trim().isEmpty()) {
            Date dbeginDate = DateToString.strToUtilDate(beginDate, "yyyy-MM-dd");
            criteria.add(Restrictions.ge("visit_time", dbeginDate));
        }
        // select * from xxx where visit_time >= ? and visit_time <= ?
        if (endDate != null && !endDate.trim().isEmpty()) {
            Date dendDate = DateToString.strToUtilDate(endDate, "yyyy-MM-dd");
            criteria.add(Restrictions.le("visit_time", dendDate));
        }

        // 分页查询
        PageBean<Visit> page = visitService.findByPage(this.getPageCode(), this.getPageSize(), criteria);
        // 压栈
        this.setVs("page", page);
        return "page";
    }


    /**
     * 保存拜访记录
     * @return
     */
    public String save() {
        // 把用户获取到，设置到当前拜访记录中，再保存
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
        if (user == null) {
            // 配置全局结果跳转
            return LOGIN;
        }
        // 设置用户
        visit.setUser(user);
        // 保存数据
        System.out.println(visit.getCustomer().getCust_id());
        visitService.save(visit);
        return "save";
    }

}
