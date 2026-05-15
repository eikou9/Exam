package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	
	public Test get(String no) throws Exception {

	    Test test = null;

	    Connection connection = getConnection();

	    PreparedStatement statement = null;

	    ResultSet resultSet = null;

	    try {

	        statement = connection.prepareStatement(
	            "select * from test where no = ?"
	        );

	        statement.setInt(1, Integer.parseInt(no));

	        resultSet = statement.executeQuery();

	        if (resultSet.next()) {

	            test = new Test();

	            // Student
	            Student student = new Student();
	            student.setNo(resultSet.getString("student_no"));

	            // Subject
	            Subject subject = new Subject();
	            subject.setCd(resultSet.getString("subject_cd"));

	            // School
	            School school = new School();
	            school.setCd(resultSet.getString("school_cd"));

	            // Testへセット
	            test.setStudent(student);
	            test.setSubject(subject);
	            test.setSchool(school);

	            test.setNo(resultSet.getInt("no"));
	            test.setPoint(resultSet.getInt("point"));
	            test.setClassNum(resultSet.getString("class_num"));
	        }

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

	    return test;
	}
	
	private List<Test> postFilter(ResultSet resultSet, School school) throws Exception {

	    List<Test> list = new ArrayList<>();

	    try {

	        while (resultSet.next()) {

	            Test test = new Test();

	            Student student = new Student();
	            student.setNo(resultSet.getString("student_no"));
	            student.setName(resultSet.getString("student_name"));
	            student.setEntYear(resultSet.getInt("student_entyear"));
	            
	            Subject subject = new Subject();
	            subject.setCd(resultSet.getString("subject_cd"));

	            test.setStudent(student);
	            test.setSubject(subject);
	            test.setSchool(school);

	            test.setClassNum(resultSet.getString("class_num"));

	            test.setNo(resultSet.getInt("test_no"));

	            Integer point = (Integer) resultSet.getObject("point");

	            if (point != null) {
	                test.setPoint(point);
	            }

	            list.add(test);
	        }

	    } catch (SQLException | NullPointerException e) {

	        e.printStackTrace();
	    }

	    return list;
	}

	
	public List<Test>filter(int entYear,String classNum,Subject subject,int num,School school)throws Exception{
		
		String sql = "SELECT " +
			    "s.ent_year AS student_entyear, " +
			    "s.class_num AS student_classnum, " +
			    "s.no AS student_no, " +
			    "s.name AS student_name, " +
			    "t.class_num AS class_num, " +
			    "t.subject_cd AS subject_cd, " +
			    "t.school_cd AS school_cd, " +
			    "t.no AS test_no, " +
			    "t.point AS point " +
			    "FROM student s " +
			    "LEFT JOIN test t " +
			    "ON s.no = t.student_no " +
			    "AND t.subject_cd = ? " +
			    "AND t.no = ? " +
			    "WHERE s.ent_year = ? " +
			    "AND s.class_num = ? " +
			    "AND s.school_cd = ? " +
			    "ORDER BY s.no ASC";
		

		 
		List<Test>list=new ArrayList<>();
		
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		ResultSet resultSet=null;
//		String condition = "and s.ent_year = ? " + "and s.class_num = ? " + "and t.subject_cd = ? " + "and t.no = ? ";
//		// SQL文のソート
//		String order = " order by no asc";
//		
//		String conditionIsAttend="";

		try {
			statement = connection.prepareStatement(sql);
			
			statement.setString(5, school.getCd());
			statement.setInt(3, entYear);
			statement.setString(4, classNum);
			statement.setString(1, subject.getCd());
			statement.setInt(2, num);
			
			resultSet = statement.executeQuery();
			
			list = postFilter(resultSet, school);
		}catch (Exception e) {
			throw e;
			// TODO: handle exception
		}finally {
			if (resultSet != null) {
			    resultSet.close();
			}
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
		return list;
		
	}
	
	public boolean save(Test test) throws Exception{
		
		Connection connection = getConnection();
		
		boolean result = false;
		
        try {
        	
             // 既存データ確認
             String checkSql ="SELECT COUNT(*) FROM test " +"WHERE student_no=? " +"AND subject_cd=? " +"AND no=?";
             
             PreparedStatement checkPs =connection.prepareStatement(checkSql);
             
             checkPs.setString(1,test.getStudent().getNo());
             
             checkPs.setString(2,test.getSubject().getCd());
             
             checkPs.setInt(3,test.getNo());
             
             ResultSet rs = checkPs.executeQuery();
             
             rs.next();
             
             int count = rs.getInt(1);
             
             PreparedStatement ps;
             
             // データが存在するならUPDATE
             if (count > 0) {
            	 
            	 String updateSql ="UPDATE test SET point=? " +"WHERE student_no=? " +"AND subject_cd=? " +"AND no=?";
            	 
            	 ps = connection.prepareStatement(updateSql);
            	 
            	 ps.setInt(1, test.getPoint());
            	 
            	 ps.setString(2,test.getStudent().getNo());
            	 
            	 ps.setString(3,test.getSubject().getCd());
            	 
            	 ps.setInt(4,test.getNo());
            	 
             } else {
            	 
            	 // なければINSERT
            	 String insertSql ="INSERT INTO test " +"(student_no, subject_cd, school_cd, no, point, class_num) " +"VALUES (?, ?, ?, ?, ?, ?)";
            	 
            	 ps = connection.prepareStatement(insertSql);
            	 
            	 ps.setString(1, test.getStudent().getNo());
            	 ps.setString(2, test.getSubject().getCd());
            	 ps.setString(3, test.getSchool().getCd());
            	 ps.setInt(4, test.getNo());
            	 ps.setInt(5, test.getPoint());
            	 ps.setString(6, test.getClassNum());
            	 
             }
             
             int executeResult =ps.executeUpdate();
             
             result = executeResult > 0;
             
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        
        return result;
	}
}

