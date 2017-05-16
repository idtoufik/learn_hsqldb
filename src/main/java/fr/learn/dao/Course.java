package fr.learn.dao;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


@Entity
@Table(name = "course")

@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Course {
	
	private Long id;
	private String subject;
	private String description;
	private Member member;
	private Set<Lesson> lessons;
	private Date dateOfCreation;
	private String image;
	//private String b64img;
	
	
	public Course(){
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Pattern(regexp=".{4,}")
	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotNull
	//@Pattern(regexp=".{4,}")
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	@OneToMany(mappedBy="course")
	public Set<Lesson> getLessons() {
		return lessons;
	}
	
	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	
	@Column(length = 20971520)
	public String getImage() {
		return image;
	}
	
	
	public void setImage(String image) {
		this.image = image;
	}

	
	
	
	
}
