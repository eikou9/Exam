package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

import bean.Teacher;
import bean.Test; // 成績（テスト）情報のBean
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao; // テスト用DAO
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		String entYearStr = ""; // 入力された入学年度
		String classNum = ""; // 入力されたクラス番号
		String subjectCd = ""; // 入力された科目コード
		String countStr = ""; // 入力された回数
		int entYear = 0; // 入学年度
		int count = 0; // 回数
		List<Test> tests = null; // テストリスト
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得
		
		TestDao testDao = new TestDao(); // テストDao
		SubjectDao subjectDao = new SubjectDao(); // 科目Dao
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Dao
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得 2
		entYearStr = req.getParameter("f1"); // 入学年度
		classNum = req.getParameter("f2"); // クラス番号
		subjectCd = req.getParameter("f3"); // 科目コード
		countStr = req.getParameter("f4"); // 回数

		// ビジネスロジック 4
		if (entYearStr != null && !entYearStr.equals("0")) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		if (countStr != null && !countStr.equals("0")) {
			// 数値に変換
			count = Integer.parseInt(countStr);
		}

		// リストを初期化（入学年度用）
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classList = classNumDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとに科目一覧を取得
		List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

		// 全ての検索条件が指定されている場合のみ検索実行
		if (entYear != 0 && classNum != null && !classNum.equals("0") && subjectCd != null && !subjectCd.equals("0") && count != 0) {
			// 入学年度、クラス、科目、回数、学校を指定してテスト一覧を取得
			tests = testDao.filter(entYear, classNum, subjectCd, count, teacher.getSchool());
		} else if (entYearStr != null) {
			// 検索ボタンが押されたが条件が不足している場合
			errors.put("filter", "入学年度、クラス、科目、回数を選択してください");
			req.setAttribute("errors", errors);
		}

		// レスポンス値をセット 6
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", count);
		
		// リクエストに取得したデータをセット
		req.setAttribute("students", tests); // JSP側でstudentsとして回しているため
		req.setAttribute("class_num_set", classList);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subjects", subjectList);

		// JSPへフォワード 7
		// 設計書に合わせて成績登録用のJSPを指定
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}