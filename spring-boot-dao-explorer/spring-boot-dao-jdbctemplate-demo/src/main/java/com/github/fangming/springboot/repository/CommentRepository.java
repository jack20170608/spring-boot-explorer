package com.github.fangming.springboot.repository;

import com.github.fangming.springboot.jdbc.BaseJdbcRepository;
import com.github.fangming.springboot.jdbc.RowUnmapper;
import com.github.fangming.springboot.jdbc.TableDescription;
import com.github.fangming.springboot.model.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.fangming.springboot.model.Comment.Columns;

@Repository
public class CommentRepository extends BaseJdbcRepository<Comment, String> {

    public static final RowMapper<Comment> ROW_MAPPER = (rs, rowNum) -> new Comment(
        rs.getString(Columns.ID.name())
        ,rs.getLong(Columns.USER_ID.name())
        ,rs.getString(Columns.CONTENTS.name())
        ,rs.getTimestamp(Columns.CREATED_TS.name()).toLocalDateTime()
        ,rs.getInt(Columns.FAVOURITE_COUNT.name())
    );

    public static final RowUnmapper<Comment> ROW_UNMAPPER = o -> {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put(Columns.ID.name(), o.getId());
        row.put(Columns.USER_ID.name(), o.getUserId());
        row.put(Columns.CONTENTS.name(), o.getContents());
        row.put(Columns.CREATED_TS.name(), Timestamp.valueOf(o.getCreatedTimestamp()));
        row.put(Columns.FAVOURITE_COUNT.name(), o.getFavouriteCount());
        return row;
    };


    public CommentRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, Comment.TABLE_NAME, Comment.ID_COLUMN);
    }

    public CommentRepository(TableDescription table) {
        super(ROW_MAPPER, ROW_UNMAPPER, table);
    }

    @Override
    protected <S extends Comment> S postUpdate(S entity) {
        entity.withPersisted(true);
        return super.postUpdate(entity);
    }

    @Override
    protected <S extends Comment> S postInsert(S entity, Number generatedId) {
        entity.withPersisted(true);
        return entity;
    }
}
