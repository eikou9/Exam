package scoremanager.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
public class SubjectDeleteExecuteAction extends Action {
   @Override
   public void execute(
           HttpServletRequest request,
           HttpServletResponse response
   ) throws Exception {
       // 科目コード取得
       String cd = request.getParameter("cd");
       // DB接続
       Class.forName("org.h2.Driver");
       Connection con =
           DriverManager.getConnection(
               "jdbc:h2:tcp://localhost/~/exam",
               "sa",
               ""
               );
       
       // DELETE
       String sql =
    		   "DELETE FROM subject WHERE cd = ?";
       
       PreparedStatement ps =
    		   con.prepareStatement(sql);
       
       ps.setString(1,  cd);
       
       ps.executeUpdate();
       
       ps.close();
       con.close();
       
       // 完了画面へ
       request.getRequestDispatcher("/scoremanager/main/subject_delete_done.jsp").forward(request, response);
   }
}