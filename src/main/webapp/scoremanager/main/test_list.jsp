<%-- 成績参照JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

			<%-- 検索フォーム --%>
			<form action="TestList.action" method="get">
				<div class="row border mx-3 mb-3 py-3 align-items-end rounded" id="filter">
					<div class="col-2">
						<label class="form-label" for="ent-year-select">入学年度</label>
						<select class="form-select" id="ent-year-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="class-num-select">クラス</label>
						<select class="form-select" id="class-num-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-3">
						<label class="form-label" for="subject-select">科目</label>
						<select class="form-select" id="subject-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subjects}">
								<option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="count-select">回数</label>
						<select class="form-select" id="count-select" name="f4">
							<option value="0">--------</option>
							<c:forEach begin="1" end="5" var="i">
								<option value="${i}" <c:if test="${i == f4}">selected</c:if>>${i}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
				</div>
				<%-- エラーメッセージ表示 --%>
				<div class="mx-3 mt-2 text-danger">${errors.get("filter")}</div>
			</form>

			<%-- 一覧表示部分 --%>
			<c:choose>
				<c:when test="${not empty students}">
					<div class="px-4 mb-2 fw-bold">
						科目：${subjectName}（${f4}回目）
					</div>
					
					<table class="table table-hover">
						<thead>
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>点数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="student" items="${students}">
								<tr class="align-middle">
									<td>${student.entYear}</td>
									<td>${student.classNum}</td>
									<td>${student.no}</td>
									<td>${student.name}</td>
									<td>
										<c:choose>
											<c:when test="${student.point == -1}">
												未入力
											</c:when>
											<c:otherwise>
												${student.point}
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty f1 && f1 != 0}">
						<div class="mx-4 mt-3">学生情報が存在しませんでした。</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>