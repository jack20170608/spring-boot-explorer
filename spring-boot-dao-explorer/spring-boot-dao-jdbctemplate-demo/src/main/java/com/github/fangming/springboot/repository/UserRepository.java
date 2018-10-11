package com.github.fangming.springboot.repository;

import com.github.fangming.springboot.jdbc.BaseJdbcRepository;
import com.github.fangming.springboot.jdbc.RowUnmapper;
import com.github.fangming.springboot.model.User;
import static com.github.fangming.springboot.model.User.Sex;
import static com.github.fangming.springboot.model.User.Columns.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Optional;

@Repository
public class UserRepository extends BaseJdbcRepository<User, Long> {

    //Row of table to Object mapper
    public static final RowMapper<User> ROW_MAPPER = (rs, rowNum) -> new User(
        rs.getLong(ID.name())
        ,rs.getString(USER_NAME.name())
        ,Enum.valueOf(Sex.class, rs.getString(SEX.name()))
        ,rs.getDate(DATE_OF_BIRTH.name()).toLocalDate()
        ,rs.getInt(REPUTATION.name())
        ,rs.getBoolean(ENABLED.name())
        ,rs.getTimestamp(LAST_LOGIN_TS.name()).toLocalDateTime()
    );

    //Object to row mapper
    public static final RowUnmapper<User> ROW_UNMAPPER = o -> {
        LinkedHashMap<String, Object> row = new LinkedHashMap<>();
        row.put(ID.name(), o.getId());
        row.put(USER_NAME.name(), o.getUserName());
        row.put(SEX.name(), o.getSex().name());
        row.put(DATE_OF_BIRTH.name(), Optional.ofNullable(o.getDateOfBirth()).map(Date::valueOf).orElse(null));
        row.put(REPUTATION.name(), o.getReputation());
        row.put(ENABLED.name(), o.isEnabled());
        row.put(LAST_LOGIN_TS.name(), Optional.ofNullable(o.getLastLoginTimestamp()).map(Timestamp::valueOf).orElse(null));
        return row;
    };


    public UserRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER, User.TABLE_NAME, User.ID_COLUMN);
    }

    @Override
    protected <S extends User> S postInsert(S entity, Number generatedId) {
        entity.setId(generatedId.longValue());
        return entity;
    }
}
