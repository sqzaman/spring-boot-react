package edu.med.umich.michr.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.med.umich.michr.domain.Question;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;

@Repository("questionDaoImpl")
public class QuestionDaoImpl implements QuestionDao {

	private EntityManager entityManager;

	@Autowired
	public QuestionDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override

	public Long save(Question question) {
		Session session = entityManager.unwrap(Session.class);
		return ((Long) session.save(question));
	}

	@Override
	public Question getById(long id) {
		Session session = entityManager.unwrap(Session.class);

		return session.get(Question.class, id);
	}

	@Override
	public void update(Question question) {
		Session session = entityManager.unwrap(Session.class);

		session.merge(question);
	}

	@Override
	public List<Question> getAll() {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Question> query = cb.createQuery(Question.class);
		query.from(Question.class);

		return session.createQuery(query).getResultList();
	}

	@Override
	public void delete(Question question) {
		Session session = entityManager.unwrap(Session.class);

		session.delete(question);
	}
}
