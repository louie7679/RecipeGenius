package org.ascending.training.repository;

import org.ascending.training.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJDBCDaoImpl implements IUserDao{
    static final String DB_URL = "jdbc:postgresql://localhost:5430/RecipeRecDB";
    static final String USER = "admin";
    static final String PASS = "Training123!";

    @Override
    public void save(User user) {

    }

    @Override
    public List<User> getUsers() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.debug("Start to getUsers from Postgres via JDBC.");
        //Step1: Prepare the required data model
        List<User> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //Step2: Open a connection ->5 key points to connect db
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Step3: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM Users";
            rs = stmt.executeQuery(sql);
            logger.info("Connects to DB successfully and execute the query.");

            //Step4: Extract data from result set
            while(rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String dietaryRestrictions = rs.getString("dietary_restrictions");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setDietaryRestrictions(dietaryRestrictions);
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Unable to connect to db or execute query", e);
            //e.printStackTrace();
        } finally {
            //Step6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Unable to close db connection", e);
                //e.printStackTrace();
            }
        }
        logger.info("Finish getUsers {}", users);
        return users;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getUserEagerBy(Long id) {
        return null;
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User update(User user) {
        return null;
    }
}
