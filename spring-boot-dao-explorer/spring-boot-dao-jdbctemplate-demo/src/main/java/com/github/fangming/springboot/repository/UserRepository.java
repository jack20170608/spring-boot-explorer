package com.github.fangming.springboot.repository;

import com.github.fangming.springboot.jdbc.BaseJdbcRepository;
import com.github.fangming.springboot.jdbc.RowUnmapper;
import com.github.fangming.springboot.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.LinkedHashMap;

@Repository
public class UserRepository extends BaseJdbcRepository<User, String> {

    public static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
        rs.getString("user_name"),
        rs.getDate("date_of_birth"),
        rs.getInt("reputation"),
        rs.getBoolean("enabled")
    ).withPersisted(true);

    public static final RowUnmapper<User> ROW_UNMAPPER = o -> {
        LinkedHashMap<String, Object> row = new LinkedHashMap<>();
        row.put("user_name", o.getUserName());
        row.put("date_of_birth", new Date(o.getDateOfBirth().getTime()));
        row.put("reputation", o.getReputation());
        row.put("enabled", o.isEnabled());
        return row;
    };


    public UserRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, "USERS", "user_name");
    }


    @Override
    protected <S extends User> S postUpdate(S entity) {
        entity.withPersisted(true);
        return entity;
    }

    @Override
    protected <S extends User> S postInsert(S entity, Number generatedId) {
        entity.withPersisted(true);
        return entity;
    }
}
