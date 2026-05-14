package scoremanager.main;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
public class SubjectDeleteAction extends Action {
   @Override
   public void execute(
           HttpServletRequest request,
           HttpServletResponse response
   ) throws Exception {
       // URLから科目コード取得
       String cd = request.getParameter("cd");
       // JSPへ渡す
       request.setAttribute("cd", cd);
       // 削除確認画面へ
       request.getRequestDispatcher(
           "/scoremanager/main/subject_delete.jsp"
       ).forward(request, response);
   }
}