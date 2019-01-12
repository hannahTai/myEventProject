package com.member.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

public class MemberVO implements Serializable{
	
	private String memberNo;
	private String memberFullname;
	private String email;
	private String phone;
	private String idcard;
	private String memberAccount;
	private String memberPassword;
	private Integer ewalletBalance;
	private Timestamp creationDate;
	private byte[] profilePicture;
	private String memberStatus;
	private String thirduid;

	public MemberVO() {
		// TODO Auto-generated constructor stub
	}

	public MemberVO(String memberNo, String memberFullname, String email, String phone, String idcard, String memberAccount,
			String memberPassword, Integer ewalletBalance, Timestamp creationDate, byte[] profilePicture,
			String memberStatus, String thirduid) {
		super();
		this.memberNo = memberNo;
		this.memberFullname = memberFullname;
		this.email = email;
		this.phone = phone;
		this.idcard = idcard;
		this.memberAccount = memberAccount;
		this.memberPassword = memberPassword;
		this.ewalletBalance = ewalletBalance;
		this.creationDate = creationDate;
		this.profilePicture = profilePicture;
		this.memberStatus = memberStatus;
		this.thirduid = thirduid;
	}
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberFullname() {
		return memberFullname;
	}

	public void setMemberFullname(String memberFullname) {
		this.memberFullname = memberFullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public Integer getEwalletBalance() {
		return ewalletBalance;
	}

	public void setEwalletBalance(Integer ewalletBalance) {
		this.ewalletBalance = ewalletBalance;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getThirduid() {
		return thirduid;
	}

	public void setThirduid(String thirduid) {
		this.thirduid = thirduid;
	}

	@Override
	public String toString() {
		return "MemberVO [會員編號=" + memberNo + ", 會員姓名=" + memberFullname + ", 會員email=" + email + ", 會員手機號碼="
				+ phone + ", 會員身分證字號=" + idcard + ", 會員帳號=" + memberAccount + ", 會員密碼="
				+ memberPassword + ", 會員電子錢包餘額=" + ewalletBalance + ", 會員帳號創立日期=" + creationDate
				+ ", 會員狀態=" + memberStatus + ", 會員第三方登入UID=" + thirduid + "]";
	}
	
}
