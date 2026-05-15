package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		TestDao testDao = new TestDao(); // テストDao
		
		// リクエストパラメーターの取得 2
		String[] studentNoList = req.getParameterValues("student_no_list"); // 学生番号の配列
		String[] pointList = req.getParameterValues("point_list"); // 点数の配列
		// String entYearStr = req.getParameter("f1"); // 入学年度
		String classNum = req.getParameter("f2"); // クラス番号
		String subjectCd = req.getParameter("f3"); // 科目コード
		String countStr = req.getParameter("f4"); // 回数

		// ビジネスロジック 4
		if (studentNoList != null && pointList != null) {
			// 保存用のリストを作成
			List<Test> tests = new ArrayList<>();
			
			for (int i = 0; i < studentNoList.length; i++) {
				Test test = new Test();
				
				// Studentオブジェクト作成
				Student student = new Student();
				// 画面から送られてきた各学生の情報をセット
				student.setNo(studentNoList[i]);
				
				// Subjectオブジェクト作成
				Subject subject = new Subject();
				subject.setCd(subjectCd);
				
				// 点数が空文字（未入力）の場合は -1 や特定の数値で扱う等の処理が必要
				int point = -1;
				if (!pointList[i].isEmpty()) {
					point = Integer.parseInt(pointList[i]);
				}
				
				// Testへセット
				test.setStudent(student);
				test.setSubject(subject);
				test.setPoint(point);
				test.setClassNum(classNum);
				test.setNo(Integer.parseInt(countStr));
				test.setSchool(teacher.getSchool());
				
				tests.add(test);
			}
			
			for(Test test : tests) { 
			// TestDaoの保存メソッド（一括更新）を呼び出し
			// ※Dao側に save(List<Test> tests) のようなメソッドがある想定
			testDao.save(test);
			}

		// レスポンス値をセット 6
		// なし（完了画面へ飛ばすため）

		// JSPへフォワード 7
		// 登録完了画面にフォワード
		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
		}
	}

}