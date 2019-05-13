package edu.med.umich.michr.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Question")
public class Question implements Serializable {

	private static final long serialVersionUID = 7476458559052451727L;

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private long id;

	@Column(name = "TEXT")
	private String text;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderColumn(name = "_idx")
	@JoinColumn(name = "QUESTION_ID")
	private List<AnswerOption> options = new ArrayList<AnswerOption>();

	public Question() {
	}

	public Question(long id, String text) {
		this.text = text;
		this.id = id;
	}

	public Question(long id, String text, List<AnswerOption> options) {
		this.text = text;
		this.id = id;
		this.options = options;
	}

	public Question(String text, List<AnswerOption> options) {
		this.text = text;
		this.options = options;
	}

	public Question(String text) {
		this.text = text;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setOptions(List<AnswerOption> options) {
		this.options = options;
	}

	public List<AnswerOption> getOptions() {
		return options;
	}

	public void addToOptions(AnswerOption option) {
		this.options.add(option);
	}

	public void removeOption(AnswerOption option) {
		this.options.remove(option);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Question)) {
			return false;
		}
		Question question = ((Question) obj);
		return this.id == question.getId() && this.text.equals(question.getText());
	}
}
