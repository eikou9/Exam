package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res)
                        throws Exception {

        // セッション
        HttpSession session = req.getSession();

        Teacher teacher =
            (Teacher)session.getAttribute("user");

        // パラメータ取得
        String cd =
            req.getParameter("cd");

        String name =
            req.getParameter("name");

        // Bean
        Subject subject =
            new Subject();

        subject.setCd(cd);

        subject.setName(name);

        subject.setSchool(
            teacher.getSchool()
        );

        // DAO
        SubjectDao dao =
            new SubjectDao();
        
        Subject old =
        	    dao.get(cd, teacher.getSchool());

        	if (old == null) {

        	    req.setAttribute(
        	        "error",
        	        "科目が存在していません"
        	    );

        	    req.setAttribute(
        	        "subject",
        	        subject
        	    );

        	    req.getRequestDispatcher(
        	        "subject_update.jsp"
        	    ).forward(req, res);

        	    return;
        	}
        
        // 更新
        dao.save(subject);
        
        req.getRequestDispatcher(
        	    "subject_update_done.jsp"
        	).forward(req, res);
    }
}