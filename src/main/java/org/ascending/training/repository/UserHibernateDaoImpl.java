package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        try {
            Session session = sessionFactory.openSession();
            session.save(user);
            session.close();
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
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
        }

        logger.info("Get departments {}", users);
        return users;
    }

    @Override
    public User getById(Long id) {
        User user = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
           Session session = sessionFactory.openSession();
           session.beginTransaction();
           //Retrieve the object to be updated
           user = session.get(User.class, id);
           session.getTransaction().commit();
           session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
        return user;
    }

    @Override
    public void delete(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
    }
}
