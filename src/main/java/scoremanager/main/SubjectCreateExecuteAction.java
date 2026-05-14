package scoremanager.main;

// 必要なクラスを読み込む
import java.util.HashMap;
import java.util.Map;

import bean.Subject;      // 科目情報を扱うBean
import bean.Teacher;      // 先生情報を扱うBean
import dao.SubjectDao;    // 科目テーブルを操作するDAO
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

/*
 * 科目登録を実行するクラス
 * 「登録」ボタンを押した後に動く処理
 */
public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res) throws Exception {

        // セッション情報を取得
        HttpSession session = req.getSession();

        // ログイン中の先生情報を取得
        Teacher teacher =
            (Teacher) session.getAttribute("user");

        // 入力された科目コードを取得
        String cd = req.getParameter("cd");

        // 入力された科目名を取得
        String name = req.getParameter("name");

        // SubjectDaoのインスタンス作成
        SubjectDao subjectDao = new SubjectDao();

        // エラーメッセージを保存するMap
        Map<String, String> errors = new HashMap<>();


        // ===== 科目コードの重複チェック =====

        // 同じ科目コードがDBに存在するか確認
        Subject old =
            subjectDao.get(cd, teacher.getSchool());

        // すでに存在していた場合
        if (old != null) {

            // errorsにエラーメッセージを追加
            errors.put("cd", "科目コードが重複しています");
        }


        // ===== エラーが無い場合 =====

        if (errors.isEmpty()) {

            // 新しいSubjectオブジェクトを作成
            Subject subject = new Subject();

            // 科目コードをセット
            subject.setCd(cd);

            // 科目名をセット
            subject.setName(name);

            // 学校情報をセット
            // （どの学校の科目かを保存するため）
            subject.setSchool(teacher.getSchool());

            // DBへ保存
            subjectDao.save(subject);

            // 登録完了画面へ移動
            req.getRequestDispatcher("subject_create_done.jsp")
               .forward(req, res);

        } else {

            // ===== エラーがある場合 =====

            // エラー内容をJSPへ渡す
            req.setAttribute("errors", errors);

            // 入力値を保持
            // エラー時に再入力しなくて済むようにする
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);

            // 登録画面へ戻る
            req.getRequestDispatcher("subject_create.jsp")
               .forward(req, res);
        }
    }
}