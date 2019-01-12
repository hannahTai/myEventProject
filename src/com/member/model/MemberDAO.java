package com.member.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO implements MemberDAO_interface {

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
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
			"INSERT INTO MEMBER (MEMBER_NO,MEMBER_FULLNAME,EMAIL,PHONE,IDCARD,MEMBER_ACCOUNT,MEMBER_PASSWORD,EWALLET_BALANCE,CREATION_DATE,PROFILE_PICTURE,MEMBER_STATUS,THIRDUID) VALUES ('M'||LPAD(to_char(member_no_seq.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?, ?, ?,CURRENT_TIMESTAMP, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM MEMBER ORDER BY MEMBER_NO";
	private static final String DELETE = 
			"DELETE FROM MEMBER WHERE MEMBER_NO = ?";
	private static final String UPDATE = 
			"UPDATE MEMBER SET MEMBER_FULLNAME = ?, EMAIL = ?, PHONE = ?, MEMBER_ACCOUNT = ?, MEMBER_PASSWORD = ?, PROFILE_PICTURE = ?, MEMBER_STATUS = ? WHERE MEMBER_NO = ?";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM MEMBER WHERE MEMBER_NO = ?";
	private static final String MEMBER_CHECK = 
			"SELECT * FROM MEMBER WHERE (MEMBER_ACCOUNT=? AND MEMBER_PASSWORD = ?) OR THIRDUID = ?";
	private static final String GET_ONE_MEMBER_BY_ACCOUNT = 
			"SELECT * FROM MEMBER WHERE MEMBER_ACCOUNT=?";

	@Override
	public void insert(MemberVO member) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, member.getMemberFullname());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getIdcard());
			pstmt.setString(5, member.getMemberAccount());
			pstmt.setString(6, member.getMemberPassword());
			pstmt.setInt(7, member.getEwalletBalance());
//			pstmt.setTimestamp(8, member.getCreationDate());
			pstmt.setBytes(8, member.getProfilePicture());
			pstmt.setString(9, member.getMemberStatus());
			pstmt.setString(10, member.getThirduid());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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
	public void update(MemberVO member) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, member.getMemberFullname());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
//			pstmt.setString(4, member.getIdcard());
			pstmt.setString(4, member.getMemberAccount());
			pstmt.setString(5, member.getMemberPassword());
//			pstmt.setInt(7, member.getEwalletBalance());
//			pstmt.setTimestamp(7, member.getCreationDate());
			pstmt.setBytes(6, member.getProfilePicture());
			pstmt.setString(7, member.getMemberStatus());
//			pstmt.setString(9, member.getThirduid());
			pstmt.setString(8, member.getMemberNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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
	public void delete(String memberNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, memberNo);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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
	public MemberVO findByPrimaryKey(String memberNo) {

		MemberVO member = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, memberNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new MemberVO();
				member.setMemberNo(rs.getString("MEMBER_NO"));
				member.setMemberFullname(rs.getString("MEMBER_FULLNAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setPhone(rs.getString("PHONE"));
				member.setIdcard(rs.getString("IDCARD"));
				member.setMemberAccount(rs.getString("MEMBER_ACCOUNT"));
				member.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
				member.setEwalletBalance(rs.getInt("EWALLET_BALANCE"));
				member.setCreationDate(rs.getTimestamp("CREATION_DATE"));
				member.setProfilePicture(rs.getBytes("PROFILE_PICTURE"));
				member.setMemberStatus(rs.getString("MEMBER_STATUS"));
				member.setThirduid(rs.getString("THIRDUID"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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
		return member;
	}

	@Override
	public List<MemberVO> getAll() {

		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO member = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new MemberVO();
				member.setMemberNo(rs.getString("MEMBER_NO"));
				member.setMemberFullname(rs.getString("MEMBER_FULLNAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setPhone(rs.getString("PHONE"));
				member.setIdcard(rs.getString("IDCARD"));
				member.setMemberAccount(rs.getString("MEMBER_ACCOUNT"));
				member.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
				member.setEwalletBalance(rs.getInt("EWALLET_BALANCE"));
				member.setCreationDate(rs.getTimestamp("CREATION_DATE"));
				member.setProfilePicture(rs.getBytes("PROFILE_PICTURE"));
				member.setMemberStatus(rs.getString("MEMBER_STATUS"));
				member.setThirduid(rs.getString("THIRDUID"));
				list.add(member);
			}

		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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

	public MemberVO memberCheck(String member_account, String member_password,String thirdUID) {
		
		MemberVO member = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MEMBER_CHECK);

			pstmt.setString(1, member_account);
			pstmt.setString(1, member_password);
			pstmt.setString(3, thirdUID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new MemberVO();
				member.setMemberNo(rs.getString("MEMBER_NO"));
				member.setMemberFullname(rs.getString("MEMBER_FULLNAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setPhone(rs.getString("PHONE"));
				member.setIdcard(rs.getString("IDCARD"));
				member.setMemberAccount(rs.getString("MEMBER_ACCOUNT"));
				member.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
				member.setEwalletBalance(rs.getInt("EWALLET_BALANCE"));
				member.setCreationDate(rs.getTimestamp("CREATION_DATE"));
				member.setProfilePicture(rs.getBytes("PROFILE_PICTURE"));
				member.setMemberStatus(rs.getString("MEMBER_STATUS"));
				member.setThirduid(rs.getString("THIRDUID"));
			}

		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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
		return member;
	}
	
	public MemberVO findByAccount(String member_account) {
		
		MemberVO member = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_MEMBER_BY_ACCOUNT);
			
			pstmt.setString(1, member_account);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				member = new MemberVO();
				member.setMemberNo(rs.getString("MEMBER_NO"));
				member.setMemberFullname(rs.getString("MEMBER_FULLNAME"));
				member.setEmail(rs.getString("EMAIL"));
				member.setPhone(rs.getString("PHONE"));
				member.setIdcard(rs.getString("IDCARD"));
				member.setMemberAccount(rs.getString("MEMBER_ACCOUNT"));
				member.setMemberPassword(rs.getString("MEMBER_PASSWORD"));
				member.setEwalletBalance(rs.getInt("EWALLET_BALANCE"));
				member.setCreationDate(rs.getTimestamp("CREATION_DATE"));
				member.setProfilePicture(rs.getBytes("PROFILE_PICTURE"));
				member.setMemberStatus(rs.getString("MEMBER_STATUS"));
				member.setThirduid(rs.getString("THIRDUID"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("BuBu!"
					+ se.getMessage());
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
		return member;
	}
	
}

	