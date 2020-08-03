<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content= "text/html; charset=UTF-8">
<script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap-theme.min.css">
<script type="text/javascript">
 	$(document).ready(function(){
 	});
</script>
<title>게시글 수정</title>
</head>
<body>
    <h3>게시글 수정</h3>
    <div style="padding : 30px;">
        <form method="POST" action="/board/write">
          <div class="form-group">
            <label>제목</label>
            <input type="text" name="subject" class="form-control">
          </div>
          <div class="form-group">
            <label>작성자</label>
            <input type="text" name="writer" class="form-control">
          </div>
          <div class="form-group">
            <label>내용</label>
            <textarea name="content" class="form-control" rows="5"></textarea>
          </div>
          <button type="submit" class="btn btn-default">작성</button>
        </form>
    </div>
</body>
</html>
