package org.ascending.training.repository;

import org.ascending.training.model.Ingredient;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class IngredientHibernateDaoImpl implements IIngredientDao{
    private static final Logger logger = LoggerFactory.getLogger(IngredientHibernateDaoImpl.class);
    @Override
    public void save(Ingredient ingredient) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(ingredient);
            transaction.commit();
            session.close();
        } catch(HibernateException e) {
            if(transaction != null) {
                logger.error("Save transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Open session exception or close session exception", e);
            session.close();
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }
    }

    @Override
    public List<Ingredient> getIngredients() {
        logger.info("Start to getIngredients from Postgres via Hibernate.");
        //Prepare the required data model
        List<Ingredient> ingredients = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        //Open a connection
        Session session = sessionFactory.openSession();

        try {
            //Execute a query
            String hql = "from Ingredient";
            //Extract data from result set
            Query<Ingredient> query = session.createQuery(hql);
            ingredients = query.list();
            //Close resources
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            session.close();
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }

        logger.info("Get ingredients {}", ingredients);
        return ingredients;
    }

    @Override
    public Ingredient getById(Long id) {
        Ingredient ingredient = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            //Retrieve the object to be updated
            ingredient = session.get(Ingredient.class, id);
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            session.close();
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }
        return ingredient;
    }

    @Override
    public void delete(Ingredient ingredient) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(ingredient);
            transaction.commit();
            session.close();
        } catch(HibernateException e) {
            if(transaction != null) {
                logger.error("Delete transaction failed, rolling back");
                transaction.rollback();
            }
            logger.error("Open session exception or close session exception", e);
            session.close();
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }
    }

    @Override
    public Ingredient getIngredientEagerBy(Long id) {
        String hql = "FROM Ingredient i LEFT JOIN FETCH i.recipes where i.id = :Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Ingredient> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Ingredient result = query.uniqueResult();
            session.close();
            return result;
        } catch(HibernateException e) {
            logger.error("Failed to retrieve ingredient data record", e);
            session.close();
            // return null;
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }
    }
}
