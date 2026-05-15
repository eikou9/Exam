<%-- 成績登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
			<div id="wrap_box">
				<%-- 完了メッセージ --%>
				<p class="text-center py-3" style="background-color:#8cc3a9">登録が完了しました</p>
				<br>
				<br>
				<br>
				<%-- リンクボタンを並べるエリア --%>
				<div class="text-center">
					<%-- ③戻る --%>
					<a href="TestRegist.action" class="me-4">戻る</a>
					
					<%-- ④成績参照 --%>
					<a href="TestList.action">成績参照</a>
				</div>
			</div>
		</div>
	</c:param>
</c:import>