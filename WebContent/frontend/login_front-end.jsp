<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.member.model.*"%>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>ETIckeTs - 會員登入</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
						<c:if test="${not empty errorMsgs}">
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<p style="color: red">${message}</p>
								</c:forEach>
							</ul>
						</c:if>
						<form  METHOD="post" ACTION="/CA105G2/member/member.do" role="form">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" id="account" placeholder="account" name="member_account" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" id="password" placeholder="password" name="member_password" type="text">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <input type="hidden" name="action" value="find_By_Account">
								<input type="submit" value="Login">
<!--                                 <a href="index.html" class="btn btn-info btn-block">Login</a> -->
                            </fieldset>
                        </form>
<!--                         <select size="1" name="memberNo"> -->
<%-- 						<c:forEach var="memberNo" items="member" >  --%>
<%-- 							<option value="${memberNo}">${member.memberNo} --%>
<%-- 						</c:forEach>    --%>
<!-- 						</select> -->
						<jsp:useBean id="memberservice" scope="page"
				class="com.member.model.MemberService" />

			  <li>
			       選擇會員帳號
			       <select size="1" name="memberA" onchange="changeA" id="userlist">
			         <c:forEach var="member" items="${memberservice.all}" > 
			          <option value="${member.memberAccount},${member.memberPassword}">${member.memberAccount},${member.memberPassword}
			         </c:forEach>
			       </select>
			  </li>
					</div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
			$(document).ready(function(){
				$('#userlist').on('change',function(){
					var str = $(this).val();
					$('#account').val(str.substring(0,str.indexOf(',',1)));
					$('#password').val(str.substring(str.indexOf(',',1)+1,str.lastIndexOf('')));
				});
			})
	</script>
</body>

</html>