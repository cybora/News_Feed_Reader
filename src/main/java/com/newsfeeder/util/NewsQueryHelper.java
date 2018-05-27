package com.newsfeeder.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsfeeder.model.News;

@Service
public class NewsQueryHelper {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	/**
	 * @param id
	 * @param minute
	 * @return List of the news requested by news source and the time limit
	 */
	@SuppressWarnings("unchecked")
	public List<News> findAllBySourceNewsAndByMinutes(Integer id, Integer minute) {

		EntityManager session = entityManagerFactory.createEntityManager();

		try {
			List<News> news = null;
			if (id != null) {
				news = (List<News>) session.createNativeQuery(
						"select * from news where news_source_id = :id and publication_date >= DATE_SUB(NOW(), interval :minute minute)",
						News.class).setParameter("id", id).setParameter("minute", minute).getResultList();
			} else { // if no source for the news is provided then get from all the sources for the
						// given last minutes.
				news = (List<News>) session.createNativeQuery(
						"select * from news where publication_date >= DATE_SUB(NOW(), interval :minute minute)",
						News.class).setParameter("minute", minute).getResultList();
			}

			return news;
		} catch (NoResultException e) {
			return null;
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
	

	
	/**
	 * @param id
	 * @param hour
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<News> findAllBySourceNewsAndByHours(Integer id, Integer hour) {

		EntityManager session = entityManagerFactory.createEntityManager();

		try {
			List<News> news = null;
			if (id != null) {
				news = (List<News>) session.createNativeQuery(
						"select * from news where news_source_id = :id and publication_date >= DATE_SUB(NOW(), interval :hour hour)",
						News.class).setParameter("id", id).setParameter("hour", hour).getResultList();
			} else { // if no source for the news is provided then get from all the sources for the
						// given last minutes.
				news = (List<News>) session.createNativeQuery(
						"select * from news where publication_date >= DATE_SUB(NOW(), interval :hour hour)",
						News.class).setParameter("hour", hour).getResultList();
			}

			return news;
		} catch (NoResultException e) {
			return null;
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
	
	/**
	 * @return news list to be deleted
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<News> findNewsToBeDeleted() {

		EntityManager session = entityManagerFactory.createEntityManager();

		// delete the records older than given hours before
		try {
			List<News> newsToBeDeleted = (List<News>) session.createNativeQuery(
						"select * from news where publication_date < DATE_SUB(NOW(), interval :hours hour)",
						News.class).setParameter("hours", NewsContants.DELETE_HOURS_BEFORE).getResultList();
			
			return newsToBeDeleted;
		} catch (NoResultException e) {
			return null;
		} finally {
			if (session.isOpen())
				session.close();
		}
	}
	
	/**
	 * @return number of deleted records
	 */
	@Transactional
	public int deleteAllByHours() {

		EntityManager session = entityManagerFactory.createEntityManager();

		// delete the records older than given hours before
		try {
			int deletedNewsCount = 0;
			session.joinTransaction();
			deletedNewsCount = session.createNativeQuery(
						"delete from news where publication_date < DATE_SUB(NOW(), interval :hours hour)",
						News.class).setParameter("hours", NewsContants.DELETE_HOURS_BEFORE).executeUpdate();
			
			return deletedNewsCount;
		} catch (NoResultException e) {
			return 0;
		} finally {
			if (session.isOpen())
				session.close();
		}
	}

}
