package cn.devinkin.contant;

public interface Constant {
    /**
     * 提交方式 post get
     */
    String METHOD_POST = "post";
    String METHOD_GET = "get";

    /**
     * 动态代理重写的方法
     */
    String GETPARAMETER = "getParameter";
    String GETPARAMETERVALUES = "getParameterValues";
    String GETPARAMETERMAP = "getParameterMap";

    /**
     * 邮件主体内容
     */
    //使用双单引号表示单引号，使用MessageFormat替换参数
    String EMAILCONTENT = "欢迎你注册成为我们的一员，<a href=''http://localhost:8080/onlineshop/user?method=active&code={0}''>点击此激活</a>";

    /**
     * 激活模块相关敞亮
     */
    String CODE_INVALID="激活码无效！";
    String ACTIVE_SUCCESS="恭喜你，激活成功！";
    String ACTIVED = "你已经激活过了，请不要再激活！";

    /**
     * 用户是否激活
     */
    int USER_IS_ACTIVE = 1;

    /**
     * 用户是否自动登录，是否记住用户名
     */
    String AUTO_LOGIN = "AUTO_LOGIN";
    String SAVE_NAME = "SAVE_NAME";
}
