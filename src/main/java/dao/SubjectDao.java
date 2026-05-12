package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class SubjectDao  extends Dao{	
	public Subject get(String school_cd)throws Exception{
		//科目インスタンスの初期化
		Subject subject=new Subject();
		//データベースへのコネクションを確立
		Connection connection=getConnection();
		//プリペアードステートメント
		PreparedStatement statement=null;
		
		try {
			// プリペアードステートメントにSQL文をセット
			statement=connection.prepareStatement("select * from subject where=?");
			// プリペアードステートメントに       をバインド
            statement.setString(1, school_cd);
            // プリペアードステートメントを実行
            ResultSet rSet=statement.executeQuery();
            
            if (rSet.next()) {
				subject.setsubject_Cd(rSet.getString("subject_cd"));
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				
				subject.setSchool(schoolDao.get(ResultSet.getString("school_cd")));
			}else {
				subject=null;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			//プリペアードステートメントを閉じる
			if(statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;				
				}
			}
			//コネクションを閉じる
			if (connection !=null) {
				try {
					connection.close();
				}catch (SQLException sqle) {
					throw sqle;				}
			}
		}return subject;	
	}
	public List<Subject>filter(Subject subject)throws Exception{
			//リストの初期化
		   List<Subject>list=new ArrayList<>();
		   //コネクションの確立
		   Connection connection=getConnection();
		   //プリペアードステートメント
		   PreparedStatement statement=null;
		   //リザルトセット
		   ResultSet resultSet=null;
		   
		   try {
			statement=connection.prepareStatement("select * from where subject ");
			statement.setString(1,subject.subject_Cd());
			resultSet=statement.executeQuery();
		} catch (Exception e) {
			throw e;		
		}finally {
			if (statement != null) {
				try {
					statement.close();
				}catch (SQLException sqle) {
					throw sqle;				}
			}
		}
		   //コネクションを閉じる
		   if(connection !=null) {
			   try {
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;			
			}
		   }
		   return list;
	}
	
	public boolean save(Subject subject)throws Exception{
		//コネクションをを確立
		Connection connection=getConnection();
		//プリペアードステートメント
		PreparedStatement statement=null;
		//実行件数
		int count=0;
		
		try {
			Subject old=get(subject.getsubject_Cd());
			if (old==null) {
				statement=connection.prepareStatement("insert into subject(subject_cd,setCd,setName(?,?,?))");
				
				statement.setString(1,subject.getsubject_Cd());
				statement.setString(2, subject.getCd());
				statement.setString(3,subject.getName());
			}
			count=statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		}finally {
			//プリペアードステートメントを閉じる
			if (statement !=null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;				
				}
			}
			//コネクションを閉じる
			if(connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;				
				}
			}
		}
		if (count>0) {
			//実行件数が1以上ある場合
			return true;
		}else {
			//実行件数が0件の場合
			return false;
		}
	}
	@Override
	public boolean delete(Subject subject) {
	    String sql = "DELETE FROM subject WHERE id = ?";

	    try {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setInt(1, subject.getId());

	        int result = ps.executeUpdate();

	        return result > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	
}

