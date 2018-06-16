<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head></head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="<c:url value='/store'></c:url>/css/bootstrap.min.css" type="text/css"/>
<script src="<c:url value='/store'></c:url>/js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="<c:url value='/store'></c:url>/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="<c:url value='/store'></c:url>/css/style.css" type="text/css"/>

<style>
    body {
        margin-top: 20px;
        margin: 0 auto;
    }

    .carousel-inner .item img {
        width: 100%;
        height: 300px;
    }

    .container .row div {
        /* position:relative;
        float:left; */
    }

    font {
        color: #3164af;
        font-size: 18px;
        font-weight: normal;
        padding: 0 10px;
    }

    span font {
        color: red;
        font-size: 18px;
        font-weight: normal;
        padding: 0 10px;
    }
</style>
</head>
<body>

<jsp:include page="head.jsp"></jsp:include>


<div class="container" style="width:100%;background:url('<c:url value='/store'></c:url>/image/regist_bg.jpg');">
    <div class="row">

        <div class="col-md-2"></div>


        <div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
            <font>会员注册</font>USER REGISTER
            <form class="form-horizontal" style="margin-top:5px;" method="post"
                  action="<c:url value='/user?method=regist'></c:url> ">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="username" placeholder="请输入用户名" name="username"
                               value="${reguser.username}">
                        <span id="spid1"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password" placeholder="请输入密码"
                               name="password" value="${reguser.password}">
                        <span id="spid2"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="confirmpwd" placeholder="请输入确认密码"
                               value="${reguser.password}">
                        <span id="spid3"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" id="email" placeholder="Email" name="email"
                               value="${reguser.email}">
                        <span id="spid4"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="telephone" class="col-sm-2 control-label">TelePhone</label>
                    <div class="col-sm-6">
                        <input type="tel" class="form-control" id="telephone" placeholder="Telephone" name="telephone"
                               value="${reguser.telephone}">
                        <span id="spid5"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="name" placeholder="请输入姓名" name="name"
                               value="${reguser.name}">
                        <span id="spid6"></span>
                    </div>
                </div>
                <div class="form-group opt">
                    <label for="sexRadio1" class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-6">
                        <label class="radio-inline">
                            <input type="radio" id="sexRadio1" value="male" name="sex" checked="checked"> 男
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="sexRadio2" value="female" name="sex"> 女
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="birthday" class="col-sm-2 control-label">出生日期</label>
                    <div class="col-sm-6">
                        <input type="date" class="form-control" id="birthday" name="birthday">
                    </div>
                </div>

                <div class="form-group">
                    <label for="verifycode" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" id="verifycode" name="vcode">
                        <span id="spid7"><font>${vcodeError}</font></span>
                    </div>
                    <div class="col-sm-2">
                        <img src="<c:url value='/VerifyCodeServlet'></c:url>" id="vcimg" alt="验证码" title="看不清，换一张"/>
                    </div>

                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" width="100" value="注册" name="submit" border="0"
                               style="background: url('<c:url
                                       value='/store'></c:url>/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
                                       height:35px;width:100px;color:white;" id="sub">
                    </div>
                </div>
            </form>
        </div>

        <div class="col-md-2"></div>

    </div>
</div>


<div style="margin-top:50px;">
    <img src="<c:url value='/store'></c:url>/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势"/>
</div>

<div style="text-align: center;margin-top: 5px;">
    <ul class="list-inline">
        <li><a>关于我们</a></li>
        <li><a>联系我们</a></li>
        <li><a>招贤纳士</a></li>
        <li><a>法律声明</a></li>
        <li><a>友情链接</a></li>
        <li><a target="_blank">支付方式</a></li>
        <li><a target="_blank">配送方式</a></li>
        <li><a>服务声明</a></li>
        <li><a>广告声明</a></li>
    </ul>
</div>
<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
    Copyright &copy; 2005-2016 传智商城 版权所有
</div>

</body>
<script type="text/javascript">
    $(function () {
        var flags = [false, false, false, false, false, false];
        //校验用户名
        $("#username").blur(function () {
            var $username = $("#username").val();
            var $spid1 = $("#spid1");

            //ajax+jquery校验用户是否被注册
            var url = "<c:url value='/user?method=ajaxFindUserByName'></c:url>";
            var params = "username=" + $username;
            $.get(url, params, function (self) {
                if ($username.length < 3 || $username.length > 15) {
                    $spid1.html("<font>用户名长度不能超过15个字符，不能小于3个字符</font>");
                    flags[0] = false;
                } else {
                    if (self == "1") {
                        $spid1.html("<font>用户名已经被注册了</font>");
                        flags[0] = false;
                    } else if (self == "0") {
                        $spid1.html("");
                        flags[0] = true;
                    }
                }
            });

            if ($.inArray(false, flags) == -1) {
                //所有条件都成立
                $("#sub").show();
            } else {
                $("#sub").hide();
            }
        });

        //校验密码
        $("#password").blur(function () {
            var $password = $("#password").val();
            var $spid2 = $("#spid2");
            if ($password.length < 5) {
                $spid2.html("<font>用户密码长度不能小于5位</font>");
                flags[1] = false;
            } else {
                $spid2.html("");
                flags[1] = true;
            }
            if ($.inArray(false, flags) == -1) {
                //所有条件都成立
                $("#sub").show();
            } else {
                $("#sub").hide();
            }
        });

        //校验确认密码
        $("#confirmpwd").blur(function () {
            var $password = $("#password").val();
            var $confirmpwd = $("#confirmpwd").val();
            var $spid3 = $("#spid3");
            if ($password != $confirmpwd) {
                flags[2] = false;
                $spid3.html("<font>密码不匹配</font>");
            } else {
                $spid3.html("");
                flags[2] = true;
            }
            if ($.inArray(false, flags) == -1) {
                //所有条件都成立
                $("#sub").show();
            } else {
                $("#sub").hide();
            }
        });

        //校验邮箱
        $("#email").blur(function () {
            $email = $("#email").val();
            $spid4 = $("#spid4");
            var emailReg = new RegExp(/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/);

            //ajax+jquery校验用户是否被注册
            var url = "<c:url value='/user?method=ajaxFindUserByEmail'></c:url>";
            var params = "email=" + $email;
            $.get(url, params, function (self) {
                if (!emailReg.test($email)) {
                    //不符合
                    $spid4.html("<font>邮箱格式不正确</font>");
                    flags[3] = false;
                } else {
                    if (self == "1") {
                        $spid4.html("<font>邮箱已经被注册了</font>");
                        flags[3] = false;
                    } else if (self == "0") {
                        $spid4.html("");
                        flags[3] = true;
                    }
                }
            });


            if ($.inArray(false, flags) == -1) {
                //所有条件都成立
                $("#sub").show();
            } else {
                $("#sub").hide();
            }
        });

        //校验电话号码
        $("#telephone").blur(function () {
            $telephone = $("#telephone").val();
            $spid5 = $("#spid5");
            var teleReg = new RegExp(/^[1][3,4,5,7,8][0-9]{9}$/);
            if (!teleReg.test($telephone)) {
                $spid5.html("<font>电话号码格式不正确</font>")
                flags[4] = false;
            } else {
                $spid5.html("");
                flags[4] = true;
            }
            if ($.inArray(false, flags) == -1) {
                //所有条件都成立
                $("#sub").show();
            } else {
                $("#sub").hide();
            }
        });

        //校验姓名
        $("#name").blur(function () {
            $name = $("#name").val();
            $spid6 = $("#spid6");
            if ($name.length == 0) {
                $spid6.html("<font>姓名不能为空</font>")
                flags[5] = false;
            } else {
                $spid6.html("");
                flags[5] = true;
            }
            if ($.inArray(false, flags) == -1) {
                //所有条件都成立
                $("#sub").show();
            } else {
                $("#sub").hide();
            }
        });

        //校验验证码
        $("#vcimg").click(function () {
            this.src = "<c:url value='/VerifyCodeServlet?i=" + Math.random() + "'></c:url>"
        });

        $("#verifycode").focus(function () {
            $("#spid7").html("");
        });

    });
</script>
</html>




