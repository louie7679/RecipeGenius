package org.ascending.training.repository;

import org.ascending.training.model.SystemUser;
import org.ascending.training.repository.exception.UserNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SystemUserHibernateDaoImpl implements ISystemUserDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SessionFactory sessionFactory;

    @Override
    public boolean save(SystemUser systemUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(systemUser);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Save transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Session close exception try again", e);
            session.close();
            return false;
        }
    }

    @Override
    public SystemUser getSystemUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        String hql = "FROM SystemUser u WHERE email = :email";
        try {
            Query<SystemUser> query = session.createQuery(hql);
            query.setParameter("email", email);
            SystemUser result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            session.close();
            return null;
        }
    }

    @Override
    public SystemUser getSystemUserById(Long id) {
        Session session = sessionFactory.openSession();
        String hql = "FROM SystemUser u WHERE id = :Id";
        try {
            Query<SystemUser> query = session.createQuery(hql);
            query.setParameter("Id", id);
            SystemUser result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            session.close();
            return null;
        }
    }

    @Override
    public SystemUser getSystemUserByCredentials(String email, String password) throws Exception {
        // String hql = "FROM SystemUser as u where (lower(u.email) = :email or lower(u.name) = :email) and u.password = :password";
        String hql = "FROM SystemUser as u where lower(u.email) = :email and u.password = :password";
        logger.info(String.format("SystemUser email: %s, password: %s", email, password));

        try{
            Session session = sessionFactory.openSession();
            Query<SystemUser> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);
            return query.uniqueResult();
        } catch(Exception e) {
            logger.error("error: {}", e.getMessage());
            throw new UserNotFoundException("can't find system user record with email = " + email + ", password = " + password);
        }
    }
}
