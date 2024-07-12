<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>
    <div class="container">
        <h2>로그인</h2>
        <!-- 회원 가입 성공 메세지 출력 -->
        <c:if test="${not empty param.message}">
            <p class="success-message">${param.message}</p>
        </c:if>
        
       
        <!-- 절대 경로 사용 해보기 -->
        <form action="/mvc/user/signIn" method="post">
            <div class="form-group">
                <label for="username">사용자 이름:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn">로그인</button>
        </form>
        <div class="help-text">
            <p>Todo에 처음 오셨나요? <a href="/mvc/user/signUp">지금 가입하세요</a>.</p>
        </div>
    </div>
</body>
</html>
