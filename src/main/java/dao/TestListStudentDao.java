package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class TestListStudentDao extends Dao {
	public String baseSql="select * from test school_cd=?";
	
	public TestListStudebt get(String STUDENT_NO)throws Exception{
		
		TestListStudent testliststudent=new TestListStudent();
		
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		try {
			statement =connection.prepareStatement("select * from test school_cd=?");
			
			statement.setString(1,student_no);
			
			ResultSet resultSet=statement.executeQuery();
			
			TestListStudentDao testListStudentDao=new TestListStudentDao();
			
			if (resultSet.next()) {
				testliststudent.setStudent_No(resultSet.getString("student_no"));
				testliststudent.setSubject_Cd(resultSet.getString("subject_cd"));
				testliststudent.setSchool_Cd(resultSet.getString("school_cd"));
				testliststudent.setNo(resultSet.getString("no"));
				testliststudent.setPoint(resultSet.getString("point"));
				testliststudent.setClass_Num(resultSet.getString("class_num"));
				
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
	private List<Student> postFilter(ResultSet resultSet, School school) throws Exception {

		// リストを初期化
		List<Student> list = new ArrayList<>();
		try {
			//リザルトセットを全権走査
			while (resultSet.next()) {
				//テストインスタンスを初期化
				TestListStudent testliststudent=new TestListStudent();
				testliststudent.setStudent_No(resultSet.getString("student_no"));
				testliststudent.setSubject_Cd(resultSet.getString("subject_cd"));
				testliststudent.setSchool_Cd(resultSet.getString("school_cd"));
				testliststudent.setNo(resultSet.getString("no"));
				testliststudent.setPoint(resultSet.getString("point"));
				testliststudent.setClass_Num(resultSet.getString("class_num"));
				
				list.add(testliststudent);
				
			}
		} catch (SQLException | NullPointerException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public List<TestListStudent>filter(Student student)throws Exception{
		List<TestListStudent>list=new ArrayList<E>();
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		ResultSet resultSet=null;
		try {
			statement=connection.prepareStatement("select * from where test");
			statement.setString(1,TestListStudent.testliststudent_cd());
			resultSet=statement.executeQuery();
			} catch (Exception e) {
				throw e;
			// TODO: handle exception
		    }finally {
		    	if (statement !=null) {
		    		try {
						connection.close();
					} catch (SQLException sqle) {
						throw sqle;			
					}
				   
		    	}
		    	return list;
		    }
	}
}
