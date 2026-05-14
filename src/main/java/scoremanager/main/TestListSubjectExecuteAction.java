package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String subjectCd = ""; // 入力された科目
		String subjectName = ""; // 入力された科目の名前
		int entYear = 0; // 入学年度
		ClassNumDao classNumDao = new ClassNumDao(); // クラスDao
		SubjectDao subjectDao = new SubjectDao(); // 科目Dao
		TestListSubjectDao testListSubjectDao = new TestListSubjectDao(); // 科目別一覧Dao
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		Map<String, String> errors = new HashMap<>(); //エラーメッセージ		
		
		// リクエストパラメーターの取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		
		// ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}
		
		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		// teacher.getSchool()はログインしている先生の所属している
		List<String> list = classNumDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> subjects = subjectDao.filter(teacher.getSchool());
		
		// 選択された科目を変数に入れる
		for (Subject sub : subjects) {
		    if (sub.getCd().equals(subjectCd)) {
		        subjectName = sub.getName();
		        break;
		    }
		}
		
		// エラーメッセージ用
		if(entYearStr != null) {
			if(entYearStr.equals("0") || classNum.equals("0") || subjectCd.equals("0")) {
				errors.put("f1", "入学年度とクラスと科目を選択してください");
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("TestList.action").forward(req, res);
				
				return;
			}
		}
		
		// 入学年度、クラス番号、科目を指定
		List<TestListSubject> testListSubject = testListSubjectDao.filter(teacher.getSchool(),entYear,classNum,subjectCd);
		
		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		// リクエストに科目をセット
		req.setAttribute("f3", subjectCd);
		// リクエストに科目リストをセット
		req.setAttribute("subjects", subjects);
		// リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("testListSubject", testListSubject);
		req.setAttribute("subjectName", subjectName);
		// JSPへフォワード
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}
