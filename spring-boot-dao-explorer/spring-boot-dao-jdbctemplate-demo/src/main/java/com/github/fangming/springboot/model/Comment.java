package com.github.fangming.springboot.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.domain.Persistable;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public class Comment implements Persistable<String>{

    public static final String TABLE_NAME = "COMMENTS";
    public static final String ID_COLUMN = "ID";

    public enum Columns {
        ID("id")
        ,USER_ID("user id")
        ,CONTENTS("contents")
        ,CREATED_TS("created timestamp")
        ,FAVOURITE_COUNT("favourite count");

        private String description;

        Columns(String description) {
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

    private transient boolean persisted;
    private String id;
    private Long userId;
    private String contents;
    private LocalDateTime createdTimestamp;
    private int favouriteCount;

    public Comment(String id, Long userId, String contents, LocalDateTime createdTimestamp, int favouriteCount) {
        this.id = id;
        this.userId = userId;
        this.contents = contents;
        this.createdTimestamp = createdTimestamp;
        this.favouriteCount = favouriteCount;
    }

    @Nullable
    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }

    public Comment withPersisted(boolean persisted) {
        this.persisted = persisted;
        return this;
    }

    public boolean isPersisted() {
        return persisted;
    }


    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public void setFavouriteCount(int favouriteCount) {
        this.favouriteCount = favouriteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equal(id, comment.id) &&
            Objects.equal(userId, comment.userId) &&
            Objects.equal(contents, comment.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, userId, contents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("userId", userId)
            .add("contents", contents)
            .add("createdTimestamp", createdTimestamp)
            .add("favouriteCount", favouriteCount)
            .toString();
    }
}
