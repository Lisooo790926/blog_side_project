package com.article.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class ArticleDAO implements ArticleDAO_interface {
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERID = "DA105G3";
	private static final String PASSWD = "123456";
	
	private static final String UPDATE = "UPDATE ARTICLE SET article_title=?,article_date=CURRENT_TIMESTAMP,article_content=?,"
			+ "article_type=? WHERE ARTICLE_NO=?";
	private static final String UPDATE_ARTICLE_TITLE = "UPDATE ARTICLE SET ARTICLE_TITLE =? WHERE ARTICLE_NO = ?";
	private static final String UPDATE_ARTICLE_STATUS = "UPDATE ARTICLE SET ARTICLE_STATUS=?  WHERE ARTICLE_NO = ?";
	// 下面兩者是用在相同方法
	private static final String UPDATE_ARTICLE_RATE = "UPDATE ARTICLE SET ARTICLE_RATE=? WHERE ARTICLE_NO=?";
	private static final String UPDATE_ARTICLE_SEEN = "UPDATE ARTICLE SET article_seen=article_seen+1 WHERE ARTICLE_NO=?";
	
	private static final String INSERT = "INSERT INTO ARTICLE VALUES('A'||LPAD(to_char(article_seq.NEXTVAL),7,0),?,?,CURRENT_TIMESTAMP,?,0,?,0,1)";
	
	private static final String DELETE = "DELETE FROM ARTICLE WHERE article_no = ?";
	private static final String DELETE_REPLY = "DELETE FROM REPLY WHERE ARTICLE_NO=?";
	
	private static final String FIND_BY_TYPE = "SELECT * FROM ARTICLE WHERE ARTICLE_TYPE= ? ORDER BY ARTICLE_DATE DESC";
	private static final String FIND_BY_MEMNO = "SELECT * FROM ARTICLE WHERE MEM_NO =? ORDER BY ARTICLE_DATE DESC";
	private static final String FIND_BY_MEMNO_SEEN = "SELECT * FROM ARTICLE WHERE MEM_NO =? AND ARTICLE_STATUS=1 ORDER BY ARTICLE_DATE DESC";
	private static final String FIND_BY_KEYWORD = "SELECT * FROM ARTICLE WHERE UPPER(ARTICLE_TITLE) LIKE ? ORDER BY ARTICLE_DATE DESC";
	private static final String FIND_BY_PRIMARYKEY = "SELECT * FROM ARTICLE WHERE ARTICLE_NO = ?";
	private static final String FIND_BY_STATUS = "SELECT * FROM ARTICLE WHERE ARTICLE_STATUS = ?";
	
	// rate_time 預設要改成1, 不然相除會有問題
	private static final String GETALL = "SELECT * FROM ARTICLE WHERE ARTICLE_STATUS!=2"; // 不包含刪除文章
	private static final String GETALL_DELETE = "SELECT * FROM ARTICLE WHERER ARTICLE_STATUS=2";
	private static final String GETALL_SEEN = "SELECT * FROM ARTICLE WHERE ARTICLE_STATUS=1";
	private static final String GET_GREAT_ARTICLE = "SELECT * FROM ARTICLE WHERE ARTICLE_TYPE=? ORDER BY ARTICLE_RATE DESC";
	private static final String GET_NTH_ARTICLE = "SELECT * FROM ARTICLE WHERE ARTICLE_TYPE=? AND ROWNUM<3 ORDER BY ARTICLE_RATE DESC";
	
	@Override
	public void update(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1,articleVO.getArticle_title());
			pstmt.setString(2,articleVO.getArticle_content());
			pstmt.setInt(3,articleVO.getArticle_type());
			pstmt.setString(4, articleVO.getArticle_no());
			
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
	public void updateArticleTitle(String article_no, String new_title) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(UPDATE_ARTICLE_TITLE);
			
			// 只有撰寫文章的會員才能修改文張標題( by Controller )
			pstmt.setString(1, new_title);
			pstmt.setString(2,article_no);
			
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
	public void updateArticleStatus(String article_no, Integer status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(UPDATE_ARTICLE_STATUS);
			
			// 只有該名會員與員工能更改狀態( by Controller )
			pstmt.setInt(1, status);
			pstmt.setString(2,article_no);
			
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
	public void updateArticleRate(String article_no, Double rate) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(UPDATE_ARTICLE_RATE);
			
			pstmt.setDouble(1, rate);
			pstmt.setString(2,article_no);
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
	public void updateArticleSeen(String article_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(UPDATE_ARTICLE_SEEN);
			
			pstmt.setString(1,article_no);
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
	public void insert(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(INSERT);
			
			// 修改文章所有東西 ( by Controller to check)
			pstmt.setString(1,articleVO.getMem_no());
			pstmt.setString(2,articleVO.getArticle_title());
			pstmt.setString(3,articleVO.getArticle_content());
			pstmt.setInt(4,articleVO.getArticle_type());
			
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
	public void delete(String article_no) { // 先不做! 
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			Class.forName(DRIVER);
//			con = DriverManager.getConnection(URL,USERID,PASSWD);
//			pstmt = con.prepareStatement(DELETE_REPLY);
//			
//			pstmt.setString(1,article_no);
//			pstmt.executeUpdate();
//			
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				}catch(Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//			
//			pstmt = con.prepareStatement(DELETE);
//			
//			pstmt.setString(1, article_no);
//			pstmt.executeUpdate();
//			
//		}catch(ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver " 
//					+ e.getMessage());
//		}catch(SQLException se) {
//			throw new RuntimeException("Database error ocurred " 
//					+ se.getMessage());
//		}finally{
//			if(pstmt!=null) {
//				try {
//					pstmt.close();
//				}catch(SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if(con!=null) {
//				try {
//					con.close();
//				}catch(Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
	}
	
	@Override
	public List<ArticleVO> findByStatus(Integer article_status) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_STATUS);
			pstmt.setInt(1,article_status);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}

	@Override
	public List<ArticleVO> findByType(Integer type) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_TYPE);
			pstmt.setInt(1,type);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}

	@Override
	public List<ArticleVO> findByMemno(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_MEMNO);
			pstmt.setString(1,mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}
	
	@Override
	public List<ArticleVO> findByMemnoSeen(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_MEMNO_SEEN);
			pstmt.setString(1,mem_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}

	@Override
	public List<ArticleVO> findByKeyword(String keyword) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_KEYWORD);
			pstmt.setString(1,"%"+keyword.toUpperCase()+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}

	@Override
	public ArticleVO findByPrimaryKey(String article_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(FIND_BY_PRIMARYKEY);
			pstmt.setString(1,article_no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
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
		
		return articleVO;
	}

	@Override
	public List<ArticleVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(GETALL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}
	
	@Override
	public List<ArticleVO> getAllDelete() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(GETALL_DELETE);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}
	
	@Override
	public List<ArticleVO> getAllSeen() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(GETALL_SEEN);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}

	@Override
	public List<ArticleVO> getGreatArticle(Integer type) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(GET_GREAT_ARTICLE);
			
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}
	
	@Override
	public List<ArticleVO> getNthArticle(Integer type) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			pstmt = con.prepareStatement(GET_NTH_ARTICLE);
			
			pstmt.setInt(1, type);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}
	
	
	
	@Override
	public List<ArticleVO> getCompositeQuery(Map<String, String[]> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArticleVO> list = new ArrayList<>();
		ArticleVO articleVO = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USERID,PASSWD);
			String finalSQL = "select * from article "
			          + ArticleCompositeQuery.get_WhereCondition(map)
			          + "order by article_date";
			
//			System.out.println(finalSQL);
			
			pstmt = con.prepareStatement(finalSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticle_no(rs.getString(1));
				articleVO.setMem_no(rs.getString(2));
				articleVO.setArticle_title(rs.getString(3));
				articleVO.setArticle_date(rs.getTimestamp(4));
				articleVO.setArticle_content(rs.getString(5));
				articleVO.setArticle_rate(rs.getDouble(6));
				articleVO.setArticle_type(rs.getInt(7));
				articleVO.setArticle_seen(rs.getInt(8));
				articleVO.setArticle_status(rs.getInt(9));
				list.add(articleVO);
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
		
		return list;
	}

	public static void main(String args[]) {
		ArticleDAO dao = new ArticleDAO();
		ArticleVO articleVO = new ArticleVO();
		
//		List<ArticleVO> list = null;
		
		// test update
//		articleVO.setMem_no("MM000001");
//		articleVO.setArticle_no("A0000012");
//		articleVO.setArticle_title("title2");
//		articleVO.setArticle_content("any_content2222222222");
//		articleVO.setArticle_type(0);
//		dao.update(articleVO);
//		
		// test update title
//		dao.updateArticleTitle("A0000005", "title333");
		
		// test update status
//		dao.updateArticleStatus("A0000005",0);
		
//		ArticleService articleSvc = new ArticleService();
//		articleSvc.updateArticleStatus("A0000005", 1);

		// test insert sql date
//		JSONObject jsonStr = new JSONObject();
//		Date date = new Date();
//		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//		
//		try {
//			jsonStr.append("testKey1", "testValue");
//			jsonStr.append("testKey2", sqlDate);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		articleVO.setMem_no("MM000001");
//		articleVO.setArticle_title("title2");
//		articleVO.setArticle_content(jsonStr.toString());
//		articleVO.setArticle_type(0);
//		dao.insert(articleVO);
		
		// find by primary key sql date
//		ArticleVO article = dao.findByPrimaryKey("A0000031");
//		JSONObject divideObj = null;
//		try {
//			divideObj = new JSONObject(article.getArticle_content());
//			System.out.println(divideObj.get("testKey1"));
//			System.out.println(divideObj.get("testKey2"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
		// test update sql date
//		Date date = new Date();
//		java.sql.Date sqlDate2 = new java.sql.Date(date.getTime());
//		try {
//			divideObj.accumulate("testKey2", sqlDate2);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		
//		articleVO.setMem_no("MM000001");
//		articleVO.setArticle_no("A0000031");
//		articleVO.setArticle_title("title2");
//		articleVO.setArticle_content(divideObj.toString());
//		articleVO.setArticle_type(0);
//		dao.update(articleVO);
		
		
		// find by primary key sql date
		System.out.println("--------------------------");
//		ArticleVO article2 = dao.findByPrimaryKey("A0000031");
//		JSONObject divideObj2 = null;
//		try {
//			divideObj2 = new JSONObject(article2.getArticle_content());
//			System.out.println(divideObj2.get("testKey2"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		
		// delete article 
//		dao.delete("A0000005");
		
		// find by type
//		list = dao.findByType(0);
//		for(ArticleVO article : list) {
//			System.out.println(article.getArticle_no());
//		}
		
		// find by mem_no
//		list = dao.findByMemno("MM000001");
//		for(ArticleVO article : list) {
//			System.out.println(article.getArticle_no());
//		}
		
		// find by keyword
//		list = dao.findByKeyword("title");
//		for(ArticleVO article : list) {
//			System.out.println(article.getArticle_no());
//		}
		
		// getAll 
//		list = dao.getAll();
//		for(ArticleVO article : list) {
//			System.out.println(article.getArticle_no());
//		}
		
		// get Great article  
//		list = dao.getGreatArticle(0);
//		for(ArticleVO article : list) {
//			System.out.println(article.getArticle_no());
//		}
		
		// update rate test
//		dao.updateArticleRate("A0000004", 5);
		
		
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("article_type", new String[] { "1" });
//		map.put("article_no", new String[] { "A0000001" });
//		String finalSQL = "select * from article "
//		          + ArticleCompositeQuery.get_WhereCondition(map)
//		          + "order by article_date";
//		System.out.println("finalSQL = " + finalSQL);
//		
//		List<ArticleVO> list= dao.getCompositeQuery(map);
//		for(ArticleVO article : list) {
//			System.out.println(article.getArticle_no());
//		}
		
		
		List<ArticleVO> list = dao.getNthArticle(1);
		for(ArticleVO vo :list) {
			System.out.println("-------------------");
			System.out.println(vo.getArticle_no());
			System.out.println(vo.getArticle_content());
		}
		
	} 
}
