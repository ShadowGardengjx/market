<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>超市运营管理系统</title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="${pageContext.request.contextPath }/statics/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="${pageContext.request.contextPath }/statics/css/nprogress.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="${pageContext.request.contextPath }/statics/css/animate.min.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="${pageContext.request.contextPath }/statics/css/custom.min.css" rel="stylesheet">

    <style>
        .captcha-container {
            margin: 15px 0;
            text-align: center;
        }
        .captcha-img {
            cursor: pointer;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 4px;
            margin-bottom: 10px;
            height: 40px;
        }
        .captcha-input {
            width: 150px;
            display: inline-block;
            margin-right: 10px;
        }
    </style>

    <script>
        function refreshCaptcha() {
            var captchaImg = document.getElementById('captchaImage');
            // 添加时间戳防止缓存
            captchaImg.src = '${pageContext.request.contextPath}/captcha/generate?t=' + new Date().getTime();
        }
    </script>
  </head>

  <body class="login">
    <div>
      <a class="hiddenanchor" id="signup"></a>
      <a class="hiddenanchor" id="signin"></a>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form action="dologin" method="post">
              <h1>超市运营管理系统</h1>
              <h2>员工入口</h2>
              <div>
                <input type="text" class="form-control" name="staffid" placeholder="请输入用户名" required="" />
              </div>
              <div>
                <input type="password" class="form-control" name="pwd" placeholder="请输入密码" required="" />
              </div>

              <!-- 验证码区域 -->
              <div class="captcha-container">
                <img id="captchaImage" src="${pageContext.request.contextPath}/captcha/generate"
                     alt="验证码" class="captcha-img" onclick="refreshCaptcha()" title="点击刷新验证码">
                <div>
                    <input type="text" name="captcha" class="form-control captcha-input" placeholder="请输入验证码" required>
                </div>
              </div>

              <span>${error }</span>
              <div>
              	<button type="submit" class="btn btn-success">登     录</button>
              	<button type="reset" class="btn btn-default">重　填</button>
              </div>

              <div class="clearfix"></div>
            </form>
          </section>
        </div>
      </div>
    </div>
  </body>
</html>