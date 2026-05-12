package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import jdk.incubator.vector.VectorOperators.Test;

public class TestDao extends Dao {
	private String baseSql="select * from Test where school_cd=?";
	
	public Test get(String no)throws Exception{
		Test test =new Test();
		
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		try {
			statement =connection.prepareStatement("select *from test where no=?");
			statement.setString(1, no);
			ResultSet resultSet=statement.executeQuery();
			
			SchoolDao schoolDao=new SchoolDao();
			
			if (resultSet.next()) {
				test.setStudent_No(resultSet.getString("student_no"));
				test.setSubject_Cd(resultSet.getString("subject_cd"));
				test.setSchool_Cd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getString("no"));
				test.setPoint(resultSet.getString("point"));
				test.setClass_Num(resultSet.getString("class_num"));
				
			    test.setshool(schoolDao.get(resultSet.getString("school_cd")));
			    
			}else {
				test=null;
			}
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}finally {
			if (statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
					// TODO: handle exception
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
		return test;
	      }
	public Test get(String no)throws Exception{
		Test test =new Test();
		Connection connection=getConnection();
		PreparedStatement statement=null;
		
		try {
			statement=connection.prepareStatement("select * from test where no=?");
			
			statement.setString(1, no);
			ResultSet resultSet=statement.executeQuery();
			
			SchoolDao schoolDao=new SchoolDao();
			
			if (resultSet.next()) {
				test.setStudent_No(resultSet.getString("student_no"));
				test.setSubject_Cd(resultSet.getString("subject_cd"));
				test.setSchool_Cd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getString("no"));
				test.setPoint(resultSet.getString("point"));
				test.setClass_Num(resultSet.getString("class_num"));
				
				test.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			}else {
				test=null;
			}
			
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}finally {
			if (statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
					// TODO: handle exception
				}
			}
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
					// TODO: handle exception
				}
			}
		}
		return test;
	}
	public List<Test>postFilter(ResultSet resultSet,School school)throws Exception{
		
		List<Test>list=new ArrayList<>();
		try {
			while (resultSet.next()) {
				Test test=new Test();
				test.setStudent_No(resultSet.getString("student_no"));
				test.setSubject_Cd(resultSet.getString("subject_cd"));
				test.setSchool_Cd(resultSet.getString("school_cd"));
				test.setNo(resultSet.getString("no"));
				test.setPoint(resultSet.getString("point"));
				test.setClass_Num(resultSet.getString("class_num"));
				
				list.add(test);
			}
		} catch ( SQLException|NullPointerException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}
	public List<Test>filter(int entYear,String classNum,Subject subject,int num,School school)throws Exception{
		
		List<Test>list=new ArrayList<>();
		
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		ResultSet resultSet=null;
		String condition = "and ent_year = ? and class_num = ?";
		// SQL文のソート
		String order = " order by no asc";
		
		String conditionIsAttend="";
		if (isAttend) {
			conditionIsAttend="and is_attend = true";
		}
		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			
			statement.setInt(1, entYear);
			
			statement.setString(2, classNum);
			
			statement.setSubject(3,subject);
			
			statement.setInt(4, num);
			
			statement.setSchool(5,school);
			
			resultSet = statement.executeQuery();
			
			list = postFilter(resultSet, school);
		}catch (Exception e) {
			throw e;
			// TODO: handle exception
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
		return list;
		
	}
	public boolean save(List list)throws Exception{
		
		Connection connection=getConnection();
		
		PreparedStatement statement=null;
		
		int count=0;
		
		try {
			Test old=get(test.get());
			if (old=null) {
				statement=connection.prepareStatement("insert into test(student_no,subject_cd,school_cd,no,point,class_num)values(?,?,?,?,?,?)");
				
				test.setStudent_No(1,test.getstudent_no);
				test.setSubject_Cd(2,test.getsubject_cd);
				test.setSchool_Cd(3,test.getschool_cd);
				test.setNo(4,test.getno);
				test.setPoint(5,test.getpoint);
				test.setClass_Num(6,test.class_num);
			
			}else {
				statement=connection.prepareStatement("updatet test set name=?,student_no=?,subject_cd=?,school_cd=?,no=?,point=?,class_num=? where no=?");
				
				test.setStudent_No(1,test.getstudent_no);
				test.setSubject_Cd(2,test.getsubject_cd);
				test.setSchool_Cd(3,test.getschool_cd);
				test.setNo(4,test.getno);
				test.setPoint(5,test.getpoint);
				test.setClass_Num(6,test.class_num);
			}
			count=statement.executeUpdate();
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}finally {
			if (statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
					// TODO: handle exception
				}
			}
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
					// TODO: handle exception
				}
			}
		}
		if (count>0) {
			return true;
		}else {
			return false;
		}
	}
	private boolean save(Test test,
            Connection connection) {
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
            	 String insertSql ="INSERT INTO test " +"(student_no, subject_cd, no, point) " +"VALUES (?, ?, ?, ?)";
            	 
            	 ps = connection.prepareStatement(insertSql);
            	 
            	 ps.setString(1,test.getStudent().getNo());
            	 
            	 ps.setString(2,test.getSubject().getCd());
            	 
            	 ps.setInt(3,test.getNo());
            	 
            	 ps.setInt(4,test.getPoint());
             }
             
             int executeResult =ps.executeUpdate();
             
             result = executeResult > 0;
             
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }
        
        return result;
	}
}

