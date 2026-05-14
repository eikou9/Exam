package scoremanager.main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.TestListStudent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
public class TestListStudentExecuteAction extends Action {
   public void execute(
           HttpServletRequest request,
           HttpServletResponse response) throws Exception {
       // 学生番号取得
       String studentNo = request.getParameter("student_no");
       
       System.out.println(studentNo);
       // DB接続
       Class.forName("org.h2.Driver");
       Connection con = DriverManager.getConnection(
               "jdbc:h2:tcp://localhost/~/exam",
               "sa",
               "");
       
       // 学生情報取得
       String studentName = "";
       String studentSql =
           "SELECT name FROM student WHERE no = ?";
       PreparedStatement studentPs =
           con.prepareStatement(studentSql);
       studentPs.setString(1, studentNo);
       ResultSet studentRs =
           studentPs.executeQuery();
       // 学生が存在する場合
       if (studentRs.next()) {
           studentName = studentRs.getString("name");
           System.out.println(studentName);
       } else {
           request.setAttribute(
               "error",
               "成績情報が存在しませんでした");
           
           request.getRequestDispatcher("/scoremanager/main/test_list_student.jsp").forward(request, response);
           
           return;
       }
       
       // 成績情報取得
       String sql =
           "SELECT sub.name AS subject_name, " +
           "t.subject_cd, " +
           "t.no, " +
           "t.point " +
           "FROM test t " +
           "JOIN subject sub " +
           "ON t.subject_cd = sub.cd " +
           "WHERE t.student_no = ?";
       PreparedStatement ps =
           con.prepareStatement(sql);
       ps.setString(1, studentNo);
       ResultSet rs = ps.executeQuery();
       List<TestListStudent> list =
           new ArrayList<>();
       while (rs.next()) {
           TestListStudent tls =
               new TestListStudent();
           tls.setSubjectName(
               rs.getString("subject_name"));
           tls.setSubjectCd(
               rs.getString("subject_cd"));
           tls.setNum(
               rs.getInt("no"));
           tls.setPoint(
               rs.getInt("point"));
           list.add(tls);
       }
       
       // JSPへ渡す
       request.setAttribute("studentNo", studentNo);
       request.setAttribute("studentName", studentName);
       request.setAttribute("list", list);
       // 成績0件
       if (list.size() == 0) {
           request.setAttribute(
               "message",
               "成績情報が存在しませんでした");
       }
       
       request.getRequestDispatcher(

    		    "/scoremanager/main/test_list_student.jsp")

    		    .forward(request, response);
       
       return;
    		 
   }
}