package scoremanager.main;

import bean.Subject;      // 科目情報を扱うBean
import bean.Teacher;     // 先生情報を扱うBean
import dao.SubjectDao;   // 科目テーブルを操作するDAO
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/*
 * 科目変更画面を表示するクラス
 * 選択した科目の情報をDBから取得し、
 * subject_update.jsp に渡す
 */
public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res)
                        throws Exception {

        // ===== セッション取得 =====

        // セッション情報を取得
        HttpSession session = req.getSession();

        // ログイン中の先生情報を取得
        Teacher teacher =
            (Teacher)session.getAttribute("user");


        // ===== 科目コード取得 =====

        // 画面から送られてきた科目コードを取得
        String cd =
            req.getParameter("cd");


        // ===== DAO作成 =====

        // SubjectDaoのインスタンス作成
        SubjectDao subjectDao =
            new SubjectDao();


        // ===== DBから科目情報取得 =====

        // 科目コードと学校情報を使って
        // DBから対象の科目を取得
        Subject subject =
            subjectDao.get(
                cd,
                teacher.getSchool()
            );

        // ===== エラー処理追加 =====

        if (subject == null) {

            req.setAttribute(
                "error",
                "科目が存在していません"
            );

        }


        // ===== JSPへデータ渡し =====

        // subjectという名前でJSPへ渡す
        req.setAttribute(
            "subject",
            subject
        );


        // ===== 画面遷移 =====

        // 科目変更画面を表示
        req.getRequestDispatcher(
            "subject_update.jsp"
        ).forward(req, res);
    }
}