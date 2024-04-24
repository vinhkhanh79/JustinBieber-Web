package edu.poly.entity;

import java.util.Date;

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
@Table(name = "Shares")
public class Share {
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@ManyToOne @JoinColumn(name = "UserId")
	Users user;
	@ManyToOne @JoinColumn(name = "VideoId")
	Video video;
	@Temporal(TemporalType.DATE)
	Date shareDate = new Date();
	
	public Share() {
		super();
	}
	public Share(Long id, Users user, Video video, Date shareDate) {
		super();
		this.id = id;
		this.user = user;
		this.video = video;
		this.shareDate = shareDate;
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
	public Date getShareDate() {
		return shareDate;
	}
	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}
}
