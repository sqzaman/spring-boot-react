package edu.med.umich.michr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Questionnaire")
public class Questionnaire implements Serializable {

		private static final long serialVersionUID = 7476458559052451727L;

		@Id
		@GeneratedValue
		@Column(name = "ID")
		private long id;

		@Column(name = "TEXT")
		String text;

		@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
		@OrderColumn(name = "_index")
		@JoinTable(
		            name="QUESTIONNAIRE_QUESTION",
		            joinColumns = @JoinColumn( name="QUESTIONNAIRE_ID"),
		            inverseJoinColumns = @JoinColumn( name="QUESTION_ID")
		)
		private List<Question> questions = new ArrayList<Question>();

		public Questionnaire() {
			
		}
		
		public Questionnaire(String text) {
			this.text = text;
		}
		
		public Questionnaire(long id, String text) {
			this.id = id;
			this.text = text;
		}
		
		public Questionnaire(String text, List<Question> questions) {
			this.text = text;
			this.questions = questions;
		}

		public Questionnaire(long id, String text, List<Question> questions) {
			this.id = id;
			this.text = text;
			this.questions = questions;
		}


		public long getId() {
			return id;
		}


		public void setId(long id) {
			this.id = id;
		}		


		public List<Question> getQuestions() {
			return questions;
		}


		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}
		
		public void addQuestions(Question question) {
			this.questions.add(question);
		}
		
		public void removeQuestions(Question question) {
			this.questions.remove(question);
		}


		public String getText() {
			return text;
		}


		public void setText(String text) {
			this.text = text;
		}


		@Override
		public String toString() {
			return "Questionnaire [id=" + id + ", text=" + text + ", questions=" + questions + "]";
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (id ^ (id >>> 32));
			result = prime * result + ((questions == null) ? 0 : questions.hashCode());
			result = prime * result + ((text == null) ? 0 : text.hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Questionnaire other = (Questionnaire) obj;
			if (id != other.id)
				return false;
			if (questions == null) {
				if (other.questions != null)
					return false;
			} else if (!questions.equals(other.questions))
				return false;
			if (text == null) {
				if (other.text != null)
					return false;
			} else if (!text.equals(other.text))
				return false;
			return true;
		}	

}
