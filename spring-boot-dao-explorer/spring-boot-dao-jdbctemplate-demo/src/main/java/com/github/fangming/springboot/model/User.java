package com.github.fangming.springboot.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.domain.Persistable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User implements Persistable<Long> {

    public enum Sex {
        MAN,WOMAN, UNKNOWN;
    }

    public static final String TABLE_NAME = "USERS";
    public static final String ID_COLUMN = "id";

    //The column description of user entity
    public enum Columns {
        ID("id")
        ,USER_NAME("user name")
        ,SEX("sex")
        ,DATE_OF_BIRTH("birthday")
        ,REPUTATION("reputation")
        ,ENABLED("enabled")
        ,LAST_LOGIN_TS("last login timestamp");

        private String description;

        Columns(String description){
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                .add("description", description)
                .toString();
        }
    }


    private Long id;
    private String userName;
    private Sex sex;
    private LocalDate dateOfBirth;
    private int reputation;
    private boolean enabled;
    private LocalDateTime lastLoginTimestamp;

    @Override
    public Long getId() {
        return id;
    }

    //Always set to true if use key generation strategy inside DB
    @Override
    public boolean isNew() {
        return true;
    }

    public User() {
    }

    public User(Long id, String userName, Sex sex, LocalDate dateOfBirth, int reputation, boolean enabled, LocalDateTime lastLoginTimestamp) {
        this.id = id;
        this.userName = userName;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.reputation = reputation;
        this.enabled = enabled;
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    public LocalDateTime getLastLoginTimestamp() {
        return lastLoginTimestamp;
    }

    public void setLastLoginTimestamp(LocalDateTime lastLoginTimestamp) {
        this.lastLoginTimestamp = lastLoginTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equal(id, user.id) &&
            Objects.equal(userName, user.userName) &&
            sex == user.sex &&
            Objects.equal(dateOfBirth, user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, userName, sex, dateOfBirth);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("userName", userName)
            .add("sex", sex)
            .add("dateOfBirth", dateOfBirth)
            .add("reputation", reputation)
            .add("enabled", enabled)
            .add("lastLoginTimestamp", lastLoginTimestamp)
            .toString();
    }
}