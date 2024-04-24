package edu.poly.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.*;

@NamedNativeQueries({
	@NamedNativeQuery(name = "Report.random10",
			query = "Select top 10 * from Videos order by newid()",
			resultClass = Video.class)
})

@NamedQueries({
	@NamedQuery(name = "Video.findByKeyword",
			query = "Select distinct o.video from Favorite o where o.video.title like :keyword"),
	@NamedQuery(name = "Video.findByUser",
	query = "Select o.video from Favorite o where o.user.id =:id"),
	@NamedQuery(name = "Video.findInRange",
	query = "Select distinct o.video from Favorite o where o.likeDate between :min and :max"),
	@NamedQuery(name = "Video.findInMonths",
	query = "Select distinct o.video from Favorite o where month(o.likeDate) in (:months)"),
	@NamedQuery(name = "Video.selectYear",
	query = "Select distinct year(o.likeDate) from Favorite o order by year(o.likeDate) desc")
})
@Entity
@Table(name = "Videos")
public class Video {
	@Id
	@Column(name = "Id")
	String id;
	@Column(name = "Title")
	String title;
	@Column(name = "Poster")
	String poster;
	@Column(name = "Description")
	String description;
	@Column(name = "Views")
	Integer views = 0;
	@Column(name = "Active")
	Boolean active;
	@Column(name = "Category")
	String category;
	@OneToMany(mappedBy = "video")
	List<Favorite> favorites;
	
	public Video() {

	}
	public Video(String id, String title, String poster, String description, Integer views, Boolean active,
			List<Favorite> favorites) {
		super();
		this.id = id;
		this.title = title;
		this.poster = poster;
		this.description = description;
		this.views = views;
		this.active = active;
		this.favorites = favorites;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<Favorite> getFavorites() {
		return favorites;
	}
	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

}
