<%-- 成績一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			<form method="get" action="TestListSubjectExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2 text-nowrap">科目情報</div>
					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set }">
									<option value="${year }">${year }</option>
								</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set }">
									<option value="${num }">${num }</option>
								</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="subject-f3-select">科目</label>
						<select class="form-select" id="subject-f3-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subjects }">
									<option value="${subject.cd }">${subject.name }</option>
								</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
							<button class="btn btn-secondary" id="subject-search-button">検索</button>
					</div>
					<p class="text-warning">${errors.f1 }</p>
				</div>
			</form>
			
			<form method="get" action="TestListStudentExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2 text-nowrap">学生情報</div>
					<div class="col-5">
						<label class="form-label" for="student-f4-select">学生番号</label>
							<input type="text"  class="form-control" name="student_no" value="${param.student_no }" required maxlength="10" placeholder="学生番号を入力してください">
					</div>
					<div class="col-2 text-center">
							<button class="btn btn-secondary" id="student-search-button">検索</button>
					</div>
				</div>
			</form>
			
			<div class="text-info mt-2">
				科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
			</div>
		</section>
	</c:param>
</c:import>