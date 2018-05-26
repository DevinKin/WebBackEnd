package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "AccountServlet", urlPatterns = {"/AccountServlet"})
public class AccountServlet extends HttpServlet {
    private AccountService accountService = new AccountService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 接受3个参数
         * 3. 检查用户是否存在
         * 4. 调用AccountService.account(fromuser, touser, account);
         * 5. 打印信息
         */
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter w = response.getWriter();

        String fromUser = request.getParameter("fromuser");
        String toUser = request.getParameter("touser");
        String money = request.getParameter("money");
        BigDecimal bdmoney = new BigDecimal(money);

        String msg = "";

        try {
            if (!accountService.ifUserExist(fromUser) || !accountService.ifUserExist(toUser)) {
                msg = "用户不存在";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/pro1/account.jsp").forward(request,response);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (bdmoney.compareTo(accountService.getMoneyByUser(fromUser)) == 1) {
                msg = "转账金额超过 " + fromUser + " 的余额";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("/pro1/account.jsp").forward(request,response);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            accountService.transfer(fromUser,toUser,bdmoney);
            msg = fromUser + " 向 " + toUser + " 转入了 " + money + " 元!";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/pro1/account.jsp").forward(request,response);
            return;
        } catch (SQLException e) {
            request.setAttribute("msg", "转账失败");
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
