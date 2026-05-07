<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">

        <section>

            <h2 class="h3 mb-3 fw-normal
                bg-secondary bg-opacity-10
                py-2 px-4">科目情報変更</h2>

            <form action="SubjectUpdateExecute.action"
                  method="post">

                <!-- 科目コード -->
                <div class="mb-3">

                    <label class="form-label">科目コード</label>

                    <div>
                        ${subject.cd}
                    </div>

                    <!-- hiddenで送信 -->
                    <input type="hidden"
                           name="cd"
                           value="${subject.cd}">

                </div>

                <!-- 科目名 -->
                <div class="mb-3">

                    <label class="form-label">科目名</label>

                    <input class="form-control"
                           type="text"
                           name="name"
                           value="${subject.name}"
                           required
                           minlength="3"
                           maxlength="30">

                </div>

                <!-- ボタン -->
                <div class="mt-3">

                    <button class="btn btn-primary">変更</button>

                </div>

            </form>

            <!-- 戻る -->
            <div class="mt-3">

                <a href="SubjectList.action">戻る</a>

            </div>

        </section>

    </c:param>

</c:import>