package com.amritha.polling.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amritha.polling.model.PartialSub;
import com.amritha.polling.model.PollCategory;
import com.amritha.polling.model.PollQuestions;


@Repository("pqDao")
public class PollQuestionsDaoImpl implements PollQuestionsDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	public PollQuestionsDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;}
	
	@Transactional
	public List<PollQuestions> listquestions(int id){
		return sessionFactory.getCurrentSession().createQuery("from PollQuestions where POLLCATEGORY_ID='"+id+"'",PollQuestions.class).getResultList();

}
	@Transactional
	public void savequestion(PollQuestions question) {
		sessionFactory.getCurrentSession().saveOrUpdate(question);
	}
	
	@Transactional
	public PollQuestions getquestionbyid(int id) {
		PollQuestions pollquestion=sessionFactory.getCurrentSession().get(PollQuestions.class, id);
		return pollquestion;
	}
	@Transactional
	public void deletequestion(PollQuestions question, int id) {
		question.setId(id);
		
		
		sessionFactory.getCurrentSession().delete(question);
		
	}
	@Transactional
	public PollQuestions getquestionbycid(int cid) {
		
		try {
		return sessionFactory.getCurrentSession().createQuery("from PollQuestions where POLLCATEGORY_ID='"+cid+"'",PollQuestions.class).getSingleResult();
	}
	catch(Exception e) {
		System.out.println(e);
		return null;}
	}
	
	@Transactional
	public PartialSub getquestionbycidanduid(int cid, int uid) {
		

		try {
		return sessionFactory.getCurrentSession().createQuery("from PartialSub where POLLCATEGORY_ID='"+cid+"'and UID='"+uid+"'",PartialSub.class).getSingleResult();
	}
	catch(Exception e) {
		System.out.println(e);
		return null;}
	}
		@Transactional
	public void savepartialquestion(PartialSub qs) {
			
			sessionFactory.getCurrentSession().saveOrUpdate(qs);
	}
	}


