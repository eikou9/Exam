package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
 
public class SubjectDao extends Dao {
 
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
 
        try {
            // 科目コードと学校コードで検索
            statement = connection.prepareStatement("select * from subject where cd = ? and school_cd = ?");
            statement.setString(1, cd);
            statement.setString(2, school.getCd());
            ResultSet resultSet = statement.executeQuery();
 
            if (resultSet.next()) {
                subject = new Subject();
                subject.setCd(resultSet.getString("cd"));
                subject.setName(resultSet.getString("name"));
                subject.setSchool(school);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subject;
    }
 
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
 
        try {
            // すでに存在するかチェック
            Subject old = get(subject.getCd(), subject.getSchool());
            if (old == null) {
                // 新規登録
                statement = connection.prepareStatement("insert into subject(cd, name, school_cd) values(?, ?, ?)");
                statement.setString(1, subject.getCd());
                statement.setString(2, subject.getName());
                statement.setString(3, subject.getSchool().getCd());
            } else {
                // 更新
                statement = connection.prepareStatement("update subject set name = ? where cd = ? and school_cd = ?");
                statement.setString(1, subject.getName());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getSchool().getCd());
            }
            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
    public List<Subject> filter(School school) throws Exception {
 
        List<Subject> list = new ArrayList<>();
 
        Connection connection = getConnection();
 
        PreparedStatement statement = null;
 
        try {
 
            statement = connection.prepareStatement(
                "select * from subject where school_cd=? order by cd"
            );
 
            statement.setString(1, school.getCd());
 
            ResultSet rs = statement.executeQuery();
 
            while (rs.next()) {
 
                Subject subject = new Subject();
 
                subject.setCd(rs.getString("cd"));
                subject.setName(rs.getString("name"));
                subject.setSchool(school);
 
                list.add(subject);
            }
 
        } catch (Exception e) {
            throw e;
 
        } finally {
 
            if (statement != null) statement.close();
 
            if (connection != null) connection.close();
        }
 
        return list;
    }

	public boolean delete(Subject subject) throws Exception {

	    String sql = "DELETE FROM subject WHERE id = ?";

	    Connection conn = getConnection();
	    PreparedStatement ps = null;

	    try {

	        ps = conn.prepareStatement(sql);

	        ps.setString(1, subject.getCd());

	        int result = ps.executeUpdate();

	        return result > 0;

	    } catch (SQLException e) {

	        e.printStackTrace();
	        return false;

	    } finally {

	        if (ps != null) {
	            ps.close();
	        }

	        if (conn != null) {
	            conn.close();
	        }
	    }
	}	
}

