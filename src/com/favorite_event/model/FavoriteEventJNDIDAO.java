package com.favorite_event.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
     

public class FavoriteEventJNDIDAO implements FavoriteEventDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/ETIckeTsDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO FAVORITE_EVENT (MEMBER_NO,EVETIT_NO) VALUES (?, ?)";
	
	private static final String DELETE_STMT = 
			"DELETE FROM FAVORITE_EVENT WHERE MEMBER_NO=? and EVETIT_NO=?";

	private static final String GET_ALL_STMT = 
			"SELECT MEMBER_NO,EVETIT_NO FROM FAVORITE_EVENT WHERE MEMBER_NO=?";

	@Override
	public void insert(FavoriteEventVO favoriteEventVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,favoriteEventVO.getMember_no());
			pstmt.setString(2,favoriteEventVO.getEvetit_no());
			
			pstmt.executeUpdate();
			
			System.out.println("----------Inserted----------");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String member_no, String evetit_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, member_no);
			pstmt.setString(2, evetit_no);

			pstmt.executeUpdate();
			
			System.out.println("----------Deleted----------");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<FavoriteEventVO> findByMember(String member_no) {
		List<FavoriteEventVO> list = new ArrayList<>();
		FavoriteEventVO favoriteEventVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1, member_no); 
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				favoriteEventVO = new FavoriteEventVO();
				favoriteEventVO.setMember_no(rs.getString("MEMBER_NO"));
				favoriteEventVO.setEvetit_no(rs.getString("EVETIT_NO"));
				list.add(favoriteEventVO);
			}
			
			System.out.println("----------findByMember finished----------");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void insertbyNo(String member_no, String evetit_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,member_no);
			pstmt.setString(2,evetit_no);
			
			pstmt.executeUpdate();
			
			System.out.println("----------insertbyNo finished----------");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

}
