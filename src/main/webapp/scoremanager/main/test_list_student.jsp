<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.TestListStudent" %>
<%

List<TestListStudent> list =

    (List<TestListStudent>)request.getAttribute("list");

String studentName =

    (String)request.getAttribute("studentName");

String studentNo =

    (String)request.getAttribute("studentNo");

String message =

    (String)request.getAttribute("message");

String error =

    (String)request.getAttribute("error");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績一覧（学生）</title>
<style>

body{

    margin:0;

    font-family:sans-serif;

    background-color:#f5f5f5;

}

/* 上ヘッダー */

.header{

    background:#e8edf7;

    padding:20px;

    font-size:40px;

    font-weight:bold;

}

/* 全体 */

.main{

    width:1000px;

    margin:20px auto;

    display:flex;

}

/* 左メニュー */

.menu{

    width:180px;

    padding-top:20px;

}

.menu a{

    display:block;

    margin-bottom:20px;

    color:#3366cc;

}

/* 右側 */

.content{

    flex:1;

    background:white;

    padding:20px;

}

/* タイトル */

.title{

    background:#f0f0f0;

    padding:15px;

    font-size:30px;

    margin-bottom:20px;

}

/* 検索枠 */

.search-box{

    border:1px solid #ddd;

    padding:20px;

    margin-bottom:20px;

}

.search-row{

    margin-bottom:20px;

}

.label{

    display:inline-block;

    width:100px;

}

input[type=text]{

    width:250px;

    height:35px;

}

button{

    width:80px;

    height:38px;

    background:#666;

    color:white;

    border:none;

    border-radius:5px;

}


/* テーブル */

table{

    width:100%;

    border-collapse:collapse;

}

th, td{

    border-bottom:1px solid #ddd;

    padding:10px;

    text-align:left;

}

th{

    background:#f7f7f7;

}

.footer{

    text-align:center;

    margin-top:40px;

    color:#888;

}
</style>
</head>
<body>
<div class="header">

    得点管理システム
</div>
<div class="main">
<!-- 左メニュー -->
<div class="menu">

<a href="${pageContext.request.contextPath}/Menu.action">
    メニュー
</a>

<a href="StudentList.action">
    学生管理
</a>

<div>成績管理</div>

<a href="TestRegist.action">
    成績登録
</a>

<a href="TestList.action">
    成績参照
</a>

<a href="SubjectList.action">
    科目管理
</a>

</div>
<!-- 右側 -->
<div class="content">
<div class="title">

            成績一覧（学生）
</div>
<!-- 検索枠 -->
<div class="search-box">
<!-- 検索フォーム -->
<form action="TestListStudentExecute.action"
         method="post">
<div class="search-row">
<span class="label">
               学生番号
</span>
<input type="text" name="student_no">
<button type="submit">
               検索
</button>
</div>
</form>
<!-- 氏名表示 -->
<% if(studentName != null){ %>
<p>
           氏名：
<%= studentName %>
           （<%= studentNo %>）
</p>
<% } %>
<!-- 学生エラー -->
<% if(error != null){ %>
<p>
<%= error %>
</p>
<% } %>
<!-- 成績なし -->
<% if(message != null){ %>
<p>
<%= message %>
</p>
<% } %>
<!-- 成績表 -->
<% if(list != null && list.size() > 0){ %>
<table>
<tr>
<th>科目名</th>
<th>科目コード</th>
<th>回数</th>
<th>点数</th>
</tr>
<% for(TestListStudent s : list){ %>
<tr>
<td>
<%= s.getSubjectName() %>
</td>
<td>
<%= s.getSubjectCd() %>
</td>
<td>
<%= s.getNum() %>
</td>
<td>
<%= s.getPoint() %>
</td>
</tr>
<% } %>
</table>
<% } %>
</div>

            © 2023 TIC
</div>
</div>
</div>
</body>
</html>
 