package com.github.fangming.springboot.repository;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardingPassRepository extends BaseJdbcRepository<BoardingPass, Object[]> {

    public static final RowMapper<BoardingPass> ROW_MAPPER = new RowMapper<BoardingPass>() {

        public BoardingPass mapRow(ResultSet rs, int rowNum) throws SQLException {
            BoardingPass boardingPass = new BoardingPass(
                rs.getString("flight_no"),
                rs.getInt("seq_no"),
                rs.getString("passenger"),
                rs.getString("seat")
            );
            return boardingPass.withPersisted(true);
        }
    };

    public static final RowUnmapper<BoardingPass> ROW_UNMAPPER = new RowUnmapper<BoardingPass>() {

        public Map<String, Object> mapColumns(BoardingPass o) {
            HashMap<String, Object> row = new HashMap<>();
            row.put("flight_no", o.getFlightNo());
            row.put("seq_no", o.getSeqNo());
            row.put("passenger", o.getPassenger());
            row.put("seat", o.getSeat());
            return row;
        }
    };


    public BoardingPassRepository() {
        super(ROW_MAPPER, ROW_UNMAPPER,
            new TableDescription("BOARDING_PASS", null, "flight_no", "seq_no"));
    }


    @Override
    protected <S extends BoardingPass> S postInsert(S entity, Number generatedId) {
        entity.withPersisted(true);
        return entity;
    }

}
