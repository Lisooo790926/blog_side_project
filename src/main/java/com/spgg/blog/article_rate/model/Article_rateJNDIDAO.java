package com.article_rate.model;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Article_rateJNDIDAO implements Article_rateDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = "INSERT INTO ARTICLE_RATE VALUES(?,?,?)";
	private static final String UPDATE = "UPDATE ARTICLE_RATE SET ARTICLE_MEM_RATE=? WHERE ARTICLE_NO=? AND MEM_NO=?";
	private static final String UPDATE_ARTICLE = "update article set article_rate=(select sum(article_mem_rate)/count(1) as article_rate from article_rate group by article_no having article_no=?) where article_no=?";
	private static final String FIND_BY_PRIMARYKEY = "SELECT ARTICLE_MEM_RATE FROM ARTICLE_RATE WHERE ARTICLE_NO=? AND MEM_NO=?";
	private static final String FIND_BY_ARTICLE_NO = "SELECT SUM(ARTICLE_MEM_RATE)/COUNT(1) FROM ARTICLE_RATE GROUP BY ARTICLE_NO HAVING ARTICLE_NO=?";
	
	@Override
	public void insert(Article_rateVO article_rateVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, article_rateVO.getArticle_no());
			pstmt.setString(2, article_rateVO.getMem_no());
			pstmt.setInt(3, article_rateVO.getArticle_mem_rate());
			
			pstmt.executeUpdate();
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			
			pstmt = con.prepareStatement(UPDATE_ARTICLE);
			pstmt.setString(1,article_rateVO.getArticle_no());
			pstmt.setString(2,article_rateVO.getArticle_no());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("Database error ocurred " 
					+ se.getMessage());
		}finally{
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void update(Article_rateVO article_rateVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, article_rateVO.getArticle_mem_rate());
			pstmt.setString(2, article_rateVO.getArticle_no());
			pstmt.setString(3, article_rateVO.getMem_no());
			
			pstmt.executeUpdate();
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
			
			pstmt = con.prepareStatement(UPDATE_ARTICLE);
			pstmt.setString(1,article_rateVO.getArticle_no());
			pstmt.setString(2,article_rateVO.getArticle_no());
			
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("Database error ocurred " 
					+ se.getMessage());
		}finally{
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public Integer findByPrimaryKey(String article_no, String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_PRIMARYKEY);
			
			pstmt.setString(1,article_no);
			pstmt.setString(2,mem_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("Database error ocurred " 
						+ se.getMessage());
		}finally{
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
			
		return 0;
	}
	
	@Override
	public Double findByArticleno(String article_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ARTICLE_NO);
			
			pstmt.setString(1,article_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getDouble(1);
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("Database error ocurred " 
						+ se.getMessage());
		}finally{
			if(rs!=null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return 0D;
	}
}
