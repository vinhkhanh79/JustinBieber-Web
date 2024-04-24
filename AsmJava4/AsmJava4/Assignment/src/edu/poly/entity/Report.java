package edu.poly.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(name = "Report.favoriteByYear",
			procedureName = "spFavoriteByYear",
			parameters = {
					@StoredProcedureParameter(name = "year", type = Integer.class)
			})
})
@Entity
public class Report {
	@Id
	Serializable groupp;
	Long likes;
	@Temporal(TemporalType.DATE)
	Date newest;
	@Temporal(TemporalType.DATE)
	Date oldest;
	
	public Report() {
	
	}
	public Report(Serializable groupp, Long likes, Date newest, Date oldest) {
		super();
		this.groupp = groupp;
		this.likes = likes;
		this.newest = newest;
		this.oldest = oldest;
	}
	public Serializable getGroupp() {
		return groupp;
	}
	public void setGroupp(Serializable groupp) {
		this.groupp = groupp;
	}
	public Long getLikes() {
		return likes;
	}
	public void setLikes(Long likes) {
		this.likes = likes;
	}
	public Date getNewest() {
		return newest;
	}
	public void setNewest(Date newest) {
		this.newest = newest;
	}
	public Date getOldest() {
		return oldest;
	}
	public void setOldest(Date oldest) {
		this.oldest = oldest;
	}
}
