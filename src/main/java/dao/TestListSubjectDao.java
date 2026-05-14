package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	public String baseSql ="select " + "s.ent_year, " + "s.no, " + "s.name, " + "s.class_num, " + "t.no as test_no, " + "t.point " + "from student s " + "left join test t " + "on s.no = t.student_no " + "and t.subject_cd = ?" + "where s.school_cd = ? ";
	
	private List<TestListSubject> postFilter(ResultSet resultSet, School school) throws Exception {

		List<TestListSubject> list = new ArrayList<>();

		// 学生番号ごとに管理
		Map<String, TestListSubject> studentMap = new HashMap<>();

		try {

			while (resultSet.next()) {

				String studentNo = resultSet.getString("no");

				TestListSubject testlistsubject = studentMap.get(studentNo);

				// まだ作成されていない場合
				if (testlistsubject == null) {

					testlistsubject = new TestListSubject();

					testlistsubject.setEntYear(resultSet.getInt("ent_year"));
					testlistsubject.setStudentNo(studentNo);
					testlistsubject.setStudentName(resultSet.getString("name"));
					testlistsubject.setClassNum(resultSet.getString("class_num"));

					testlistsubject.setPoints(new HashMap<>());

					studentMap.put(studentNo, testlistsubject);

					list.add(testlistsubject);
				}
				// 点数を追加
				testlistsubject.getPoints().put(
					resultSet.getInt("test_no"),
					resultSet.getInt("point")
				);
			}

		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<TestListSubject>filter(School school, int entYear, String classNum, String subjectCd) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String condition = "and s.ent_year = ? " + "and s.class_num = ? ";
		// SQL文のソート
		String order = " order by s.no, t.no asc";

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(2, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(3, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(4, classNum);
			// プリペアードステートメントに科目をバインド
			statement.setString(1, subjectCd);
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(resultSet, school);
			
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}
}
