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

import java.util.ArrayList;
import java.util.List;

public class RecipeHibernateDaoImpl implements IRecipeDao{
    private static final Logger logger = LoggerFactory.getLogger(RecipeHibernateDaoImpl.class);
    @Override
    public void save(Recipe recipe) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(recipe);
            transaction.commit();
            session.close();
        } catch(HibernateException e) {
            if(transaction != null) {
                logger.error("Save transaction failed, rolling back");
            }
            logger.error("Open session exception or close session exception", e);
        }
    }

    @Override
    public List<Recipe> getRecipes() {
        logger.info("Start to getRecipes from Postgres via Hibernate.");
        //Prepare the required data model
        List<Recipe> recipes = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            //Open a connection
            Session session = sessionFactory.openSession();
            //Execute a query
            String hql = "from Recipe";
            //Extract data from result set
            Query<Recipe> query = session.createQuery(hql);
            recipes = query.list();
            //Close resources
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }

        logger.info("Get recipes {}", recipes);
        return recipes;

    }

    @Override
    public Recipe getById(Long id) {
        Recipe recipe = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            //Retrieve the object to be updated
            recipe = session.get(Recipe.class, id);
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
        return recipe;
    }

    @Override
    public void delete(Recipe recipe) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        try {
            Session session = sessionFactory.openSession();
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
        }
    }
}
