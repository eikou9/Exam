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
									<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
									<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
								</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set }">
									<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
									<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
								</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="subject-f3-select">科目</label>
						<select class="form-select" id="subject-f3-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subjects }">
									<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
									<option value="${subject.cd }" <c:if test="${subject.cd==f3 }">selected</c:if>>${subject.name }</option>
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
			
			<c:choose>
				<c:when test="${not empty testListSubject }">
					<div>科目：${subjectName }</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>１回</th>
							<th>２回</th>
						</tr>
						<c:forEach var="testListSubject" items="${testListSubject }">
							<tr>
								<td>${testListSubject.entYear }</td>
								<td>${testListSubject.classNum }</td>
								<td>${testListSubject.studentNo }</td>
								<td>${testListSubject.studentName }</td>
								<%-- １回目 --%>
								<td>
									<%-- データがある場合はその値を、ない場合は「ー」を表示 --%>
									<c:choose>
										<c:when test="${testListSubject.getPoint(1) != 'null' }">
											${testListSubject.getPoint(1) }
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
								<%-- ２回目 --%>
								<td>
									<%-- データがある場合はその値を、ない場合は「ー」を表示 --%>
									<c:choose>
										<c:when test="${testListSubject.getPoint(2) != 'null' }">
											${testListSubject.getPoint(2) }
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>学生情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>

		</section>
	</c:param>
</c:import>