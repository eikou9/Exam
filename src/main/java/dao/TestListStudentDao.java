package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.TestListStudent;
 
public class TestListStudentDao extends Dao {

	public String baseSql="select * from test where school_cd=?";

	public TestListStudent get(String no)throws Exception{

			TestListStudent testliststudent=new TestListStudent();

		Connection connection=getConnection();

		PreparedStatement statement=null;

		try {

			statement =connection.prepareStatement("select * from test where school_cd=?");

			statement.setString(1,no);

			ResultSet resultSet=statement.executeQuery();

//			TestListStudentDao testListStudentDao=new TestListStudentDao();

			if (resultSet.next()) {

				testliststudent.setSubjectName(resultSet.getString("subject_num"));

				testliststudent.setSubjectCd(resultSet.getString("student_cd"));

				testliststudent.setNum(resultSet.getInt("num"));

				testliststudent.setPoint(resultSet.getInt("point"));

//				testliststudent.setschool(SchoolDao.get(resultSet.getString("school_cd")));

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

	private List<TestListStudent> postFilter(ResultSet resultSet, School school) throws Exception {

	    List<TestListStudent> list = new ArrayList<>();

	    try {

	        while (resultSet.next()) {

	            TestListStudent testliststudent = new TestListStudent();

	            // 科目名
	            testliststudent.setSubjectName(
	                resultSet.getString("subject_name")
	            );

	            // 科目コード
	            testliststudent.setSubjectCd(
	                resultSet.getString("subject_cd")
	            );

	            // 回数
	            testliststudent.setNum(
	                resultSet.getInt("no")
	            );

	            // 点数
	            testliststudent.setPoint(
	                resultSet.getInt("point")
	            );

	            list.add(testliststudent);
	        }

	    } catch (SQLException | NullPointerException e) {

	        e.printStackTrace();
	    }

	    return list;
	}

	public List<TestListStudent> filter(Student student) throws Exception {

	    List<TestListStudent> list = new ArrayList<>();

	    Connection connection = getConnection();

	    PreparedStatement statement = null;

	    ResultSet resultSet = null;

	    try {

	        String sql =
	            "SELECT " +
	            "sub.cd AS subject_cd, " +
	            "sub.name AS subject_name, " +
	            "t.no, " +
	            "t.point " +
	            "FROM test t " +
	            "INNER JOIN subject sub " +
	            "ON t.subject_cd = sub.cd " +
	            "WHERE t.student_no = ? " +
	            "ORDER BY t.no";

	        statement = connection.prepareStatement(sql);

	        // 学生番号
	        statement.setString(1, student.getNo());

	        resultSet = statement.executeQuery();

	        // ResultSet → List変換
	        list = postFilter(resultSet, student.getSchool());

	    } catch (Exception e) {

	        throw e;

	    } finally {

	        if (resultSet != null) {
	            resultSet.close();
	        }

	        if (statement != null) {
	            statement.close();
	        }

	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return list;
	}
}

 