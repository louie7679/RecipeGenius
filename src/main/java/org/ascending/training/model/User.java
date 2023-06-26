package org.ascending.training.model;

public class User {

    public User() {}

    private long userId;

    private String name;

    private String email;

    private String password;

    private String dietaryRestrictions;

    public void setId(long userId) { this.userId = userId; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setDietaryRestrictions(String dietaryRestrictions) { this.dietaryRestrictions = dietaryRestrictions; }


}
