package com.github.fangming.springboot.repository;

import com.github.fangming.springboot.jdbc.BaseJdbcRepository;
import com.github.fangming.springboot.jdbc.RowUnmapper;
import com.github.fangming.springboot.jdbc.TableDescription;
import com.github.fangming.springboot.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentJdbcRepository extends BaseJdbcRepository<Student, Long> {


    public StudentJdbcRepository(EntityInformation<Student, Long> entityInformation, RowMapper<Student> rowMapper, RowUnmapper<Student> rowUnmapper, TableDescription table) {
        super(entityInformation, rowMapper, rowUnmapper, table);
    }

    class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setPassportNumber(rs.getString("passport_number"));
            return student;
        }

    }

//    public Student findById(long id) {
//        return jdbcTemplate.queryForObject("select * from student where id=?", new Object[] { id },
//            new BeanPropertyRowMapper<Student>(Student.class));
//    }
//
//    public List<Student> findAll() {
//        return jdbcTemplate.query("select * from student", new StudentRowMapper());
//    }
//
//
//    public int deleteById(long id) {
//        return jdbcTemplate.update("delete from student where id=?", new Object[] { id });
//    }
//
//    public int insert(Student student) {
//        return jdbcTemplate.update("insert into student (id, name, passport_number) " + "values(?,  ?, ?)",
//            new Object[] { student.getId(), student.getName(), student.getPassportNumber() });
//    }
//
//    public int update(Student student) {
//        return jdbcTemplate.update("update student " + " set name = ?, passport_number = ? " + " where id = ?",
//            new Object[] { student.getName(), student.getPassportNumber(), student.getId() });
//    }
}
