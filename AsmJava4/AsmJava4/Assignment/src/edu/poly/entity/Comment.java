package edu.poly.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Comments")
public class Comment {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne @JoinColumn(name = "UserId")
	Users user;
	@ManyToOne @JoinColumn(name = "VideoId")
	Video video;
	@Temporal(TemporalType.DATE)
	Date cmtDate = new Date();
	@Column(name = "cmtValue")
	String cmtValue;
	
	public Comment() {
		super();
	}
	public Comment(Long id, Users user, Video video, Date cmtDate, String cmtValue) {
		super();
		this.id = id;
		this.user = user;
		this.video = video;
		this.cmtDate = cmtDate;
		this.cmtValue = cmtValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public Date getCmtDate() {
		return cmtDate;
	}
	public void setCmtDate(Date cmtDate) {
		this.cmtDate = cmtDate;
	}
	public String getCmtValue() {
		return cmtValue;
	}
	public void setCmtValue(String cmtValue) {
		this.cmtValue = cmtValue;
	}
}
