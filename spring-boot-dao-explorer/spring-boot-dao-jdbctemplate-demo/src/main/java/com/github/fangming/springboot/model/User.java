package com.github.fangming.springboot.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.domain.Persistable;

import java.util.Date;

public class User implements Persistable<String> {

    private transient boolean persisted;

    private String userName;
    private Date dateOfBirth;
    private int reputation;
    private boolean enabled;

    @Override
    public String getId() {
        return userName;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }

    public User withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }

    // constructors / getters / setters / ...
    public User(String userName, Date dateOfBirth,int reputation, boolean enabled) {
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.reputation = reputation;
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return reputation == user.reputation &&
            enabled == user.enabled &&
            Objects.equal(userName, user.userName) &&
            Objects.equal(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName, dateOfBirth, reputation, enabled);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("userName", userName)
            .add("dateOfBirth", dateOfBirth)
            .add("reputation", reputation)
            .add("enabled", enabled)
            .toString();
    }
}