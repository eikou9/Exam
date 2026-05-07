<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">

        <section>

            <h2 class="h3 mb-3 fw-normal
                bg-secondary bg-opacity-10
                py-2 px-4">科目情報変更</h2>

			<p class="text-center"
			   style="background-color:#8cc3a9">
			
			    変更が完了しました
			
			</p>
			
				<div style="margin-top: 200px;">
				
				    <a href="SubjectList.action">
				
				        科目一覧
				
				    </a>
				
				</div>

        </section>

    </c:param>

</c:import>