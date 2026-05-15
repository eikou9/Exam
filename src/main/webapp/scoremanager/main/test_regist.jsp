<%-- 成績登録JSP（タブインデント版） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

			<%-- 検索フォーム --%>
			<form action="TestRegist.action" method="get">
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
				<div class="mx-3 mt-2 text-danger">${errors.get("filter")}</div>
			</form>

			<%-- 成績入力・一括登録フォーム --%>
			<c:choose>
				<c:when test="${students.size() > 0}">
					<div>科目：${subjectName}(${f4 }回)</div>
					
					<form action="TestRegistExecute.action" method="post">
						<%-- 検索条件を引き継ぐための隠しパラメータ --%>
						<input type="hidden" name="f1" value="${f1}">
						<input type="hidden" name="f2" value="${f2}">
						<input type="hidden" name="f3" value="${f3}">
						<input type="hidden" name="f4" value="${f4}">

						<table class="table table-hover">
							<thead>
								<tr class="table-light">
									<th>入学年度</th>
									<th>クラス</th>
									<th>学生番号</th>
									<th>氏名</th>
									<th style="width: 150px;">点数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="student" items="${students}">
									<tr class="align-middle">
										<td>${student.student.entYear}</td>
										<td>${student.classNum}</td>
										<td>${student.student.no}</td>
										<td>${student.student.name}</td>
										<td>
											<input type="hidden" name="student_no_list" value="${student.student.no}">
											<input type="number" name="point_list" class="form-control form-control-sm" 
												value="${student.point}" min="0" max="100">
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						
						<div class="text-center py-4">
							<button type="submit" class="btn btn-primary px-5">登録して保存</button>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty f1}">
						<div class="mx-4 mt-3 alert alert-warning">学生情報が存在しませんでした。</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>