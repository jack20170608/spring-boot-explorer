package com.github.fangming.springboot.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class CommentRepository extends BaseJdbcRepository<Comment, Integer> {

    public static final RowMapper<Comment> ROW_MAPPER = new RowMapper<Comment>() {

        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Comment(
                rs.getInt("id"),
                rs.getString("user_name"),
                rs.getString("contents"),
                rs.getTimestamp("created_time"),
                rs.getInt("favourite_count")
            );
        }
    };

    public static final RowUnmapper<Comment> ROW_UNMAPPER = new RowUnmapper<Comment>() {

        public Map<String, Object> mapColumns(Comment o) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", o.getId());
            row.put("user_name", o.getUserName());
            row.put("contents", o.getContents());
            row.put("created_time", new java.sql.Timestamp(o.getCreatedTime().getTime()));
            row.put("favourite_count", o.getFavouriteCount());
            return row;
        }
    };


    public CommentRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "COMMENTS");
    }

    public CommentRepository(TableDescription table) {
        super(ROW_MAPPER, ROW_UNMAPPER, table);
    }


    @Override
    protected <S extends Comment> S postInsert(S entity, Number generatedId) {
        entity.setId(generatedId.intValue());
        return entity;
    }
}
