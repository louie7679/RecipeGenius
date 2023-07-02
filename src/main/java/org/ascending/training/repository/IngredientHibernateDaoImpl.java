package org.ascending.training.repository;

import org.ascending.training.model.Ingredient;
import org.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        try {
            Session session = sessionFactory.openSession();
            session.save(ingredient);
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }

    }

    @Override
    public List<Ingredient> getIngredients() {
        logger.info("Start to getIngredients from Postgres via Hibernate.");
        //Prepare the required data model
        List<Ingredient> ingredients = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        try {
            //Open a connection
            Session session = sessionFactory.openSession();
            //Execute a query
            String hql = "from Ingredient";
            //Extract data from result set
            Query<Ingredient> query = session.createQuery(hql);
            ingredients = query.list();
            //Close resources
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }

        logger.info("Get ingredients {}", ingredients);
        return ingredients;
    }

    @Override
    public Ingredient getById(Long id) {
        Ingredient ingredient = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            //Retrieve the object to be updated
            ingredient = session.get(Ingredient.class, id);
            session.getTransaction().commit();
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
        return ingredient;
    }

    @Override
    public void delete(Ingredient ingredient) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(ingredient);
            session.getTransaction().commit();
            session.close();
        } catch(HibernateException e) {
            logger.error("Open session exception or close session exception", e);
        }
    }
}
