package com.event_classification.model;

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

public class EventClassificationJNDIDAO implements EventClassificationDAO_interface{

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
			"INSERT INTO EVENT_CLASSIFICATION (EVECLASS_NO,EVECLASS_NAME) VALUES (?, ?)";

	private static final String UPDATE_STMT = 
			"UPDATE EVENT_CLASSIFICATION SET EVECLASS_NAME=? WHERE EVECLASS_NO=?";
	
	private static final String DELETE_STMT = 
			"DELETE FROM EVENT_CLASSIFICATION WHERE EVECLASS_NO=?";

	private static final String GET_ONE_STMT = 
			"SELECT EVECLASS_NO, EVECLASS_NAME FROM EVENT_CLASSIFICATION WHERE EVECLASS_NO = ?";

	private static final String GET_ALL_STMT = 
			"SELECT EVECLASS_NO, EVECLASS_NAME FROM EVENT_CLASSIFICATION";
	
	
	
	@Override
	public void insert(EventClassificationVO eventClassificationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,eventClassificationVO.getEveclass_no());
			pstmt.setString(2,eventClassificationVO.getEveclass_name());
			
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
	public void update(EventClassificationVO eventClassificationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setString(1,eventClassificationVO.getEveclass_name());
			pstmt.setString(2,eventClassificationVO.getEveclass_no());
	
			
			pstmt.executeUpdate();
			
			System.out.println("----------Updated----------");

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
	public void delete(String eveclass_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, eveclass_no);
			
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
	public EventClassificationVO findByPrimaryKey(String eveclass_no) {
		
		EventClassificationVO eventClassificationVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1,eveclass_no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventClassificationVO = new EventClassificationVO();
				eventClassificationVO.setEveclass_no(rs.getString("EVECLASS_NO"));
				eventClassificationVO.setEveclass_name(rs.getString("EVECLASS_NAME"));
			}
			
			System.out.println("----------findByPrimaryKey finished----------");

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
		return eventClassificationVO;
	}

	@Override
	public List<EventClassificationVO> getAll() {
		List<EventClassificationVO> list = new ArrayList<EventClassificationVO>();
		EventClassificationVO eventClassificationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				eventClassificationVO = new EventClassificationVO();
				eventClassificationVO.setEveclass_no(rs.getString("EVECLASS_NO"));
				eventClassificationVO.setEveclass_name(rs.getString("EVECLASS_NAME"));
				list.add(eventClassificationVO);
			}
			
			System.out.println("----------getAll finished----------");

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
		return list;
	}

}
