package com.github.fangming.springboot.h2.web;

import com.github.fangming.springboot.h2.model.Student;
import com.github.fangming.springboot.h2.repository.StudentJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentJdbcRepository studentJdbcRepository;

    @Autowired
    public StudentController(StudentJdbcRepository studentJdbcRepository) {
        this.studentJdbcRepository = studentJdbcRepository;
    }

    @GetMapping("{id}")
    public Student getById(@PathVariable Long id){
        return studentJdbcRepository.findById(id);
    }

    @GetMapping("list")
    public List<Student> listAll(){
        return studentJdbcRepository.findAll();
    }
}
