package edu.med.umich.michr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "AnswerOption")
public class AnswerOption {
	@Id
	@GeneratedValue
	@JsonIgnore
	private long id;

	@Column(name = "TEXT")
	private String text;
	
	public AnswerOption() {		
	}
	
	 public AnswerOption(String text) {
	        this.text = text;
	 }

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
