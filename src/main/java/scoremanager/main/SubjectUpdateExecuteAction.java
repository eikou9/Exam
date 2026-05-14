package scoremanager.main;

import bean.Subject;      // 科目情報を扱うBean
import bean.Teacher;     // 先生情報を扱うBean
import dao.SubjectDao;   // 科目テーブルを操作するDAO
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/*
 * 科目変更を実行するクラス
 * 入力された内容で科目情報を更新する
 */
public class SubjectUpdateExecuteAction extends Action {

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


        // ===== パラメータ取得 =====

        // 画面から送られてきた科目コードを取得
        String cd =
            req.getParameter("cd");

        // 画面から送られてきた科目名を取得
        String name =
            req.getParameter("name");


        // ===== Bean作成 =====

        // Subjectオブジェクト作成
        Subject subject =
            new Subject();

        // 科目コードをセット
        subject.setCd(cd);

        // 科目名をセット
        subject.setName(name);

        // 学校情報をセット
        subject.setSchool(
            teacher.getSchool()
        );


        // ===== DAO作成 =====

        // SubjectDaoのインスタンス作成
        SubjectDao dao =
            new SubjectDao();


        // ===== 存在確認 =====

        // DBに対象の科目が存在するか確認
        Subject old =
            dao.get(cd, teacher.getSchool());

        // 科目が存在しない場合
        if (old == null) {

            // エラーメッセージを設定
            req.setAttribute(
                "error",
                "科目が存在していません"
            );

            // 入力情報をJSPへ戻す
            req.setAttribute(
                "subject",
                subject
            );

            // 変更画面へ戻る
            req.getRequestDispatcher(
                "subject_update.jsp"
            ).forward(req, res);

            // 処理終了
            return;
        }


        // ===== 更新処理 =====

        // DBの科目情報を更新
        dao.save(subject);


        // ===== 完了画面 =====

        // 更新完了画面へ移動
        req.getRequestDispatcher(
            "subject_update_done.jsp"
        ).forward(req, res);
    }
}