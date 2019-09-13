<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->
		<script src="js/setDate.js" type="text/javascript"></script>
	</head>

	<body>
	<script>
		function isValidUser() {
			var usernameElement = document.getElementById("username");
			var username = usernameElement.value;
			// 1、创建XMLHttpRequest对象
			var xmlHttpRequest = new XMLHttpRequest();
			// 2、注册状态监控回调函数
			xmlHttpRequest.onreadystatechange = function () {
				var readyState = xmlHttpRequest.readyState;
				//alert(readyState);
				//alert(xmlHttpRequest.status);
				if(readyState == 4 && xmlHttpRequest.status == 200){
					var responseText = xmlHttpRequest.responseText;
					var user = document.getElementById("userExist");
					if(responseText == "false"){
						user.style = "display:block;color:red";
						user.innerText = "当前用户名不可用";
						usernameElement.value = "";
					}else{
						user.style = "display:block;color:green";
						user.innerText = "当前用户名可用";
					}
				}
			};

			// 3、建立与服务器的异步连接
			//xmlHttpRequest.open("get","${pageContext.request.contextPath}/ajax?username=" + username);
			xmlHttpRequest.open("post","${pageContext.request.contextPath}/Ajax");
			xmlHttpRequest.setRequestHeader("content-type","application/x-www-form-urlencoded");
			// 4、发出异步请求
			//xmlHttpRequest.send(null);
			xmlHttpRequest.send("op=isUsernameExist&username=" + username);
		}
		function isValidEmail() {
			var emailElement = document.getElementById("email");
			var email = emailElement.value;
			var xmlHttpRequest = new XMLHttpRequest();
			xmlHttpRequest.onreadystatechange = function () {
				if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200){
					var responseText = xmlHttpRequest.responseText;
					var email = document.getElementById("emailExist");
					if(responseText == "false"){
						email.style = "display:block;color:red";
						email.innerText = "当前邮箱不可用";
						usernameElement.value = "";
					}else{
						email.style = "display:block;color:green";
						email.innerText = "当前邮箱可用";
					}
				}
			};
			xmlHttpRequest.open("post","${pageContext.request.contextPath}/Ajax",true);
			xmlHttpRequest.setRequestHeader("content-type","application/x-www-form-urlencoded");
			xmlHttpRequest.send("op=isEmailExist&email=" + email);
		}
	</script>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/frontEndUserServlet">
				<input type="hidden" name="op" value="regist" />
				<ul class="left-form">
					<li>
						${msg.error.username }<br/>
							<span id="userExist" style="display: none"></span>
						<input type="text" id="username" name="username" placeholder="用户名" value="${msg.username}" required="required" onblur="isValidUser()"/><br>请输入6~18个字符</br>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.nickname }<br/>
						<input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.email }<br/>
							<span id="emailExist" style="display: none"></span>
						<input type="text" id="email" name="email" placeholder="邮箱" value="${msg.email}" required="required" onblur="isValidEmail()"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.birthday }<br/>
						<input type="text" placeholder="出生日期" name="birthday" value="${msg.birthday}" size="15" />
						<div class="clear"> </div>
					</li>
					<li>
						<input type="submit" value="创建账户">
						<div class="clear"> </div>
					</li>
			</ul>

			<div class="clear"> </div>

			</form>

		</div>
		<!-----start-copyright---->

		<!-----//end-copyright---->

	</body>

</html>