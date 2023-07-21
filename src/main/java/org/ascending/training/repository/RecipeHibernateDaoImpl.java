package org.ascending.training.repository;

import org.ascending.training.model.Recipe;
import org.ascending.training.util.HibernateUtil;
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
public class RecipeHibernateDaoImpl implements IRecipeDao{
    private static final Logger logger = LoggerFactory.getLogger(RecipeHibernateDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(recipe);
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
    public List<Recipe> getRecipes() {
        logger.info("Start to getRecipes from Postgres via Hibernate.");
        //Prepare the required data model
        List<Recipe> recipes = new ArrayList<>();
        //Open a connection
        Session session = sessionFactory.openSession();
        try {
            //Execute a query
            String hql = "from Recipe";
            //Extract data from result set
            Query<Recipe> query = session.createQuery(hql);
            recipes = query.list();
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
        logger.info("Get recipes {}", recipes);
        return recipes;
    }

    @Override
    public Recipe getById(Long id) {
        Recipe recipe = null;
        Session session = sessionFactory.openSession();
        try {
            //Retrieve the object to be updated
            recipe = session.get(Recipe.class, id);
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
            session.close();
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }
        return recipe;
    }

    @Override
    public void delete(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(recipe);
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
    public Recipe getRecipeEagerBy(Long id) {
        String hql = "FROM Recipe r LEFT JOIN FETCH r.ingredients where r.id = :Id";
        Session session = sessionFactory.openSession();
        try {
            Query<Recipe> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Recipe result = query.uniqueResult();
            session.close();
            return result;
        } catch(HibernateException e) {
            logger.error("Failed to retrieve recipe data record", e);
            session.close();
            // return null;
            // Allow the HibernateException to propagate
            // NOTE: The following line is for testing purposes only.
            // Uncomment it during testing, and comment it out in production code.
            throw e;
        }
    }

    @Override
    public Recipe update(Recipe recipe) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(recipe);
            transaction.commit();
            Recipe r = getById(recipe.getId());
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
}
