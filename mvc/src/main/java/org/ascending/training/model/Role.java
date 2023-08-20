package org.ascending.training.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "allowed_resource")
    private String allowedResource;

    @Column(name = "allowed_read")
    private Boolean allowedRead;

    @Column(name = "allowed_create")
    private Boolean allowedCreate;

    @Column(name = "allowed_update")
    private Boolean allowedUpdate;

    @Column(name = "allowed_delete")
    private Boolean allowedDelete;

    @ManyToMany(mappedBy = "roles")
    private List<SystemUser> systemUsers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllowedResource() {
        return allowedResource;
    }

    public void setAllowedResource(String allowedResource) {
        this.allowedResource = allowedResource;
    }

    public Boolean getAllowedRead() {
        return allowedRead;
    }

    public void setAllowedRead(Boolean allowedRead) {
        this.allowedRead = allowedRead;
    }

    public Boolean getAllowedCreate() {
        return allowedCreate;
    }

    public void setAllowedCreate(Boolean allowedCreate) {
        this.allowedCreate = allowedCreate;
    }

    public Boolean getAllowedUpdate() {
        return allowedUpdate;
    }

    public void setAllowedUpdate(Boolean allowedUpdate) {
        this.allowedUpdate = allowedUpdate;
    }

    public Boolean getAllowedDelete() {
        return allowedDelete;
    }

    public void setAllowedDelete(Boolean allowedDelete) {
        this.allowedDelete = allowedDelete;
    }

    public List<SystemUser> getSystemUsers() {
        return systemUsers;
    }

    public void setSystemUsers(List<SystemUser> systemUsers) {
        this.systemUsers = systemUsers;
    }

    public boolean isAllowedRead() {
        return allowedRead;
    }

    public boolean isAllowedCreate() {
        return allowedCreate;
    }

    public boolean isAllowedUpdate() {
        return allowedUpdate;
    }

    public boolean isAllowedDelete() {
        return allowedDelete;
    }
}
