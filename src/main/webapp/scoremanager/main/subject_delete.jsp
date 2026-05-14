<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目削除</title>
</head>
<body>
<h2>科目情報削除</h2>
<%
String cd = (String)request.getAttribute("cd");
%>
<p>
科目コード「<%= cd %>」を削除してもよろしいですか？
</p>
<form action="SubjectDeleteDone.action" method="post">
<input type="hidden" name="cd" value="<%= cd %>">
<button type="submit"
       style="background:red;color:white;">
       削除
</button>
</form>
<br>
<a href="SubjectList.action">戻る</a>
</body>
</html>