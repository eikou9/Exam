package scoremanager.main;

// Listを使うために読み込み
import java.util.List;

import bean.Subject;      // 科目情報を扱うBean
import bean.Teacher;     // 先生情報を扱うBean
import dao.SubjectDao;   // 科目テーブルを操作するDAO
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/*
 * 科目一覧画面を表示するクラス
 * subject_list.jsp に科目一覧を渡す役割
 */
public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res) throws Exception {

        // ===== セッション情報取得 =====

        // セッションを取得
        HttpSession session = req.getSession();

        // ログイン中の先生情報を取得
        Teacher teacher =
            (Teacher) session.getAttribute("user");


        // ===== DAO作成 =====

        // SubjectDaoのインスタンス作成
        SubjectDao dao = new SubjectDao();


        // ===== 科目一覧取得 =====

        // ログイン中の先生の学校に属する科目一覧を取得
        List<Subject> subjects =
            dao.filter(teacher.getSchool());


        // ===== JSPへデータ渡し =====

        // subjectsという名前でJSPにデータを渡す
        req.setAttribute("subjects", subjects);


        // ===== 画面遷移 =====

        // 科目一覧画面へ移動
        req.getRequestDispatcher("subject_list.jsp")
           .forward(req, res);
    }
}