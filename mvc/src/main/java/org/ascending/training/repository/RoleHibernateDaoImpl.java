package org.ascending.training.repository;

import org.ascending.training.model.Role;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleHibernateDaoImpl implements IRoleDao{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SessionFactory sessionFactory;

    @Override
    public void save(Role role) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(role);
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
    public List<Role> getRoles() {
        //Prepare the required data model
        List<Role> roles = new ArrayList<>();
        //Open a connection
        Session session = sessionFactory.openSession();
        try {
            //Execute a query
            String hql = "from Role";
            Query<Role> query = session.createQuery(hql);
            roles = query.list();
            //Close resources
            session.close();
        } catch (HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            session.close();
            throw e;
        }
        logger.info("Get roles {}", roles);
        return roles;
    }

    @Override
    public Role getById(Long id) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Role r WHERE id = :Id";
        try {
            Query<Role> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Role result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Session close exception try again", e);
            session.close();
            return null;
        }
    }

    @Override
    public Role update(Role role) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
            Role r = getById(role.getId());
            session.close();
            return r;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("failed to insert record", e);
            session.close();
            return null;
        }
    }

    @Override
    public void delete(Role role) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(role);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            if (transaction != null) {
                logger.error("Delete transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Open session exception or close session exception", e);
            session.close();
            throw e;
        }
    }
}
