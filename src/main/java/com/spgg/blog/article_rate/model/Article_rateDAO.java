package com.article_rate.model;

import java.sql.*;

public class Article_rateDAO implements Article_rateDAO_interface{
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERID = "DA105G3";
	private static final String PASSWD = "123456";
	
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
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
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver " 
					+ e.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
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
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver " 
					+ e.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_PRIMARYKEY);
			
			pstmt.setString(1,article_no);
			pstmt.setString(2,mem_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver " 
					+ e.getMessage());
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
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_ARTICLE_NO);
			
			pstmt.setString(1,article_no);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getDouble(1);
			}
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver " 
					+ e.getMessage());
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
	
	
	
	static public void main(String args[]) {
		Article_rateDAO dao = new Article_rateDAO();
		Article_rateVO rate = new Article_rateVO();
		
		// test insert
//		rate.setArticle_no("A0000002");
//		rate.setMem_no("MM000001");
//		rate.setArticle_mem_rate(5);
//		dao.insert(rate);
		
		// test update
		rate.setArticle_no("A0000001");
		rate.setMem_no("MM000001");
		rate.setArticle_mem_rate(5);
		dao.update(rate);
		
		// test findbyprimarykey
//		int a = dao.findByPrimaryKey("A0000002", "MM000001");
//		System.out.println(a);
		
		// test findbyarticleno
//		double a = dao.findByArticleno("A0000001");
//		System.out.println(a);
	}

}
