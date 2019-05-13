package edu.med.umich.michr.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.med.umich.michr.domain.Questionnaire;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;

@Repository("questionnaireDaoImpl")
public class QuestionnaireDaoImpl implements QuestionnaireDao {

	private EntityManager entityManager;

	@Autowired
	public QuestionnaireDaoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override

	public Long save(Questionnaire questionnaire) {
		Session session = entityManager.unwrap(Session.class);
		return ((Long) session.save(questionnaire));
	}

	@Override
	public Questionnaire getById(long id) {
		Session session = entityManager.unwrap(Session.class);

		return session.get(Questionnaire.class, id);
	}

	@Override
	public void update(Questionnaire questionnaire) {
		Session session = entityManager.unwrap(Session.class);

		session.merge(questionnaire);
	}

	@Override
	public List<Questionnaire> getAll() {
		Session session = entityManager.unwrap(Session.class);

		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Questionnaire> query = cb.createQuery(Questionnaire.class);
		query.from(Questionnaire.class);

		return session.createQuery(query).getResultList();
	}

	@Override
	public void delete(Questionnaire questionnaire) {
		Session session = entityManager.unwrap(Session.class);

		session.delete(questionnaire);
	}
}
