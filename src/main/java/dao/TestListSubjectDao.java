package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;

public class TestListSubjectDao extends Dao{
	public String baseSql="select * from test school_cd=?";
	
	public TestListSubject get(String STUDENT_NO)throws Exception{
		
		TestListSubject testliststudent=new TestListSubject();
		
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		try {
			statement =connection.prepareStatement("select * from subject where=?");
			
			statement.setString(1,school_cd);
			
			ResultSet resultSet=statement.executeQuery();
			
			TestListStudentDao testListStudentDao=new TestListStudentDao();
			
			if (resultSet.next()) {
		        
				
				
				
				testliststudent.setshool(SchoolDao.get(resultSet.getString("school_cd")));
			}else {
				testliststudent=null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			if (statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}
			}
		}
		return testliststudent;
	}
	private List<Subject> postFilter(ResultSet resultSet, School school) throws Exception {

		// リストを初期化
		List<Subject> list = new ArrayList<>();
		try {
			//リザルトセットを全権走査
			while (resultSet.next()) {
				//テストインスタンスを初期化
				TestListSubjectD testliststudent=new TestListSubject();
				
				
				
				
				list.add(testliststudent);
				
			}
		} catch (SQLException | NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public List<TestListSubject>filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String condition = "and ent_year = ? and class_num = ?";
		// SQL文のソート
		String order = " order by no asc";

		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend = true";
		}

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			// プリペアードステートメントを実行
			statement.setSubject(4,Subject);
			statement.setSchool(5,School);
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
