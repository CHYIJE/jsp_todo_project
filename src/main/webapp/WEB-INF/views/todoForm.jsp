<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 할 일 추가</title>
<link rel="stylesheet" type="text/css" href="../css/styles.css">
</head>
<body>
    <div class="container todoForm-container">
        <h1>새 할 일 추가</h1>
        <form action="add" method="post">
            <div class="form-group">
                <label for="title">제목:</label>
                <input type="text" id="title" name="title">
            </div>
            <div class="form-group">
                <label for="description">설명:</label>
                <textarea rows="5" id="description" name="description"></textarea>
            </div>
            <div class="form-group">
                <label for="dueDate">마감기한:</label>
                <input type="date" id="dueDate" name="dueDate">
            </div>
            <div class="form-group">
                <label for="completed">완료 여부:</label>
                <input type="checkbox" id="completed" name="completed">
            </div>
            <button type="submit" class="btn">추가하기</button>
        </form>
        <br>
        <div class="help-text">
            <a href="list">목록으로 돌아가기</a>
        </div>
    </div>
</body>
</html>
