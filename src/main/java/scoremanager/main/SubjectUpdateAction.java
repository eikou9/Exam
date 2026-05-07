package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res)
                        throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        Teacher teacher =
            (Teacher)session.getAttribute("user");

        // 科目コード取得
        String cd =
            req.getParameter("cd");

        // DAO
        SubjectDao subjectDao =
            new SubjectDao();

        // DBから取得
        Subject subject =
            subjectDao.get(
                cd,
                teacher.getSchool()
            );

        // JSPへ渡す
        req.setAttribute(
            "subject",
            subject
        );

        // JSP表示
        req.getRequestDispatcher(
            "subject_update.jsp"
        ).forward(req, res);
    }
}