<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页-中关村招聘后台管理系统</title>
    <link href="/assets/images/favicon.ico" rel="icon"">

	<link href="/assets/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/emirio-css/animate.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/emirio-css/emirio.form.min.css" rel="stylesheet" type="text/css">
	<link href="/assets/emirio-css/emirio.login.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form action="/passport/signin" class="login100-form validate-form" method="POST" id="login-form">
					<span class="login100-form-title p-b-43 ">
						中关村招聘管理系统
					</span>
					
					<div class="wrap-input100 validate-input" data-validate = "用户名不能为空！">
						<input class="input100" type="text" name="username">
						<span class="focus-input100"></span>
						<span class="label-input100">用户名</span>
					</div>
					
					<div class="wrap-input100 validate-input" data-validate="密码不能为空！">
						<input class="input100" type="password" name="password">
						<span class="focus-input100"></span>
						<span class="label-input100">密码</span>
					</div>

					<div class="flex-sb-m w-full p-t-3 p-b-32">
						<div class="contact100-form-checkbox">
							<input class="input-checkbox100" id="rememberMe" type="checkbox" name="rememberMe">
							<label class="label-checkbox100" for="rememberMe">
								记住密码
							</label>
						</div>

						<div>
							<a href="#" class="txt1">
								忘记密码？
							</a>
						</div>
					</div>
			
					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							登 陆
						</button>
					</div>
					
					<div class="text-center p-t-40 p-b-20">
						<span class="txt2">
							使用其他登陆方式
						</span>
					</div>

					<div class="login100-form-social flex-c-m">
						<a href="#" class="login100-form-social-item flex-c-m bg1 m-r-5">
							<i class="fa fa-weixin"></i>
						</a>

						<a href="#" class="login100-form-social-item flex-c-m bg2 m-r-5">
							<i class="fa fa-qq" aria-hidden="true"></i>
						</a>
						<a href="#" class="login100-form-social-item flex-c-m bg2 m-r-5">
							<i class="fa fa-weibo" aria-hidden="true"></i>
						</a>
					</div>

					<div class="version">
						<ul>
							<li>Copyright © 2020 emirio All rights reserved</li>
							<li>current version@ 1 . 9</li>
						</ul>
					</div>
					
				</form>

				<div class="login100-more" id="wave">
					<div class="container">
						<div class="box">
							<div class="title">
								<span class="block"></span>
								<h1>中关村招聘<span></span></h1>
							</div>
							<div class="role">
								<div class="block"></div>
								<p>&nbsp;&nbsp;&nbsp;后台管理系统</p>
							</div>
						</div>

						<div class="welcome animate__animated animate__rubberBand">
							<h1>欢迎使用</h1>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script src="/assets/emirio-script/jquery-3.2.1.min.js"></script>
<script src="/assets/emirio-script/sweetalert2@9.js"></script>
<script src="/assets/emirio-script/three.r95.min.js"></script>
<script src="/assets/emirio-script/vanta.waves.min.js"></script>
<script src="/assets/emirio-script/emirio.login.js"></script>

</html>