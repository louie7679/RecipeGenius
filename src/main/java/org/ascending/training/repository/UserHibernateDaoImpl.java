package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserHibernateDaoImpl implements IUserDao{
    private static final Logger logger = LoggerFactory.getLogger(UserHibernateDaoImpl.class);
    @Override
    public void save(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        ;
        try {
            transaction = session.beginTransaction();
            if (transaction == null) {
                logger.error("Encountered an error while saving the user: Transaction is null");
                throw new HibernateException("Unable to save the user: Transaction is null");
            }
            session.save(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Save transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Open session exception or close session exception", e);
            session.close();
            throw e;
        }
    }

    @Override
    public List<User> getUsers() {
        logger.info("Start to getUsers from Postgres via Hibernate.");
        //Prepare the required data model
        List<User> users = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            //Open a connection
            Session session = sessionFactory.openSession();
            //Execute a query
            String hql = "from User";
            //Extract data from result set
            Query<User> query = session.createQuery(hql);
            users = query.list();
            //Close resources
            session.close();
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            throw e;
        }

        logger.info("Get users {}", users);
        return users;
    }

    @Override
    public User getById(Long id) {
        User user = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
           Session session = sessionFactory.openSession();
           //Retrieve the object to be updated
           user = session.get(User.class, id);
           session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            throw e;
        }
        return user;
    }

    @Override
    public void delete(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            session.close();
        } catch(HibernateException e) {
            if(transaction != null) {
                logger.error("Delete transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Open session exception or close session exception", e);
        }
    }

    @Override
    public User getUserEagerBy(Long id) {
        String hql = "FROM User u LEFT JOIN FETCH u.recipes where u.id = :Id"; //LEFT JOIN FETCH: HQL里面的left join
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", id);
            User result = query.uniqueResult();
            session.close();
            return result;
        } catch(HibernateException e) {
            logger.error("Failed to retrieve user data record", e);
            session.close();
            return null;
        }
    }
}
