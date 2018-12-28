package com.advertisement.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class AdvertisementJNDIDAO implements AdvertisementDAO_interface{
	
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
			"INSERT INTO ADVERTISEMENT (AD_NO, EVETIT_NO, AD_STARTDATE, AD_ENDDATE) VALUES ('AD'||LPAD(TO_CHAR(AD_SEQ.NEXTVAL),4,'0'), ?, ?, ?)";

	private static final String UPDATE_STMT = 
			"UPDATE ADVERTISEMENT SET EVETIT_NO=?,AD_STARTDATE=?,AD_ENDDATE=? WHERE AD_NO=?";
	
	private static final String DELETE_STMT = 
			"DELETE FROM ADVERTISEMENT WHERE AD_NO=?";

	private static final String GET_ONE_STMT = 
			"SELECT AD_NO,EVETIT_NO,AD_STARTDATE,AD_ENDDATE FROM ADVERTISEMENT WHERE AD_NO = ?";

	private static final String GET_ALL_STMT = 
			"SELECT AD_NO,EVETIT_NO,AD_STARTDATE,AD_ENDDATE FROM ADVERTISEMENT";
	
	@Override
	public void insert(AdvertisementVO advertisementVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,advertisementVO.getEvetit_no());
			pstmt.setDate(2,advertisementVO.getAd_startdate());
			pstmt.setDate(3,advertisementVO.getAd_enddate());
			
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
	public void update(AdvertisementVO advertisementVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1,advertisementVO.getEvetit_no());
			pstmt.setDate(2,advertisementVO.getAd_startdate());
			pstmt.setDate(3,advertisementVO.getAd_enddate());
			pstmt.setString(4,advertisementVO.getAd_no());
		
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
	public void delete(String ad_no) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, ad_no);

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
	public AdvertisementVO findByPrimaryKey(String ad_no) {
		AdvertisementVO advertisementVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, ad_no); 
					
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				advertisementVO = new AdvertisementVO();
				advertisementVO.setAd_no(rs.getString("AD_NO"));
				advertisementVO.setEvetit_no(rs.getString("EVETIT_NO"));
				advertisementVO.setAd_startdate(rs.getDate("AD_STARTDATE"));
				advertisementVO.setAd_enddate(rs.getDate("AD_ENDDATE"));
			}
			
			System.out.println("----------findByPrimaryKey finished----------");

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
		return advertisementVO;
	}

	@Override
	public List<AdvertisementVO> getAll() {
		List<AdvertisementVO> list = new ArrayList<>();
		AdvertisementVO advertisementVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				advertisementVO = new AdvertisementVO();
				advertisementVO.setAd_no(rs.getString("AD_NO"));
				advertisementVO.setEvetit_no(rs.getString("EVETIT_NO"));
				advertisementVO.setAd_startdate(rs.getDate("AD_STARTDATE"));
				advertisementVO.setAd_enddate(rs.getDate("AD_ENDDATE"));
				list.add(advertisementVO);
			}
			
			System.out.println("----------findByPrimaryKey finished----------");

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
 
}
