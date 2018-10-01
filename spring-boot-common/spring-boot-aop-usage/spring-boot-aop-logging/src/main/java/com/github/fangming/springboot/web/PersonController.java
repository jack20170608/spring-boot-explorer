package com.github.fangming.springboot.web;

import com.github.fangming.springboot.aop.LoggerManager;
import com.github.fangming.springboot.model.Person;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);
    private static Map<Integer, Person> dataStore = Maps.newConcurrentMap();

    static {
        dataStore.put(1, new Person(1, "jack"));
        dataStore.put(2, new Person(2, "lily"));
    }

    @GetMapping("/")
    @LoggerManager(description = "Index Page")
    public String index(){
        return "Hello, welcome!, click <a href='list'>list </a> to list all data";
    }

    @GetMapping(value = "/list",produces = "application/json")
    @LoggerManager(description = "List all data ")
    public List<Person> list() throws InterruptedException {
        logger.info("sleep 2s to mock the actual list operation.");
        Thread.sleep(2000L);
        return Lists.newArrayList(dataStore.values());
    }

    @PostMapping(value = "/add", produces = "application/json")
    @LoggerManager(description = "add new person")
    public ResponseEntity addPerson(@RequestBody Person person){
        dataStore.put(person.getId(), person);
        return new ResponseEntity("Person saved successfully", HttpStatus.OK);
    }

    @GetMapping(value = "getById/{id}", produces = "application/json")
    @LoggerManager(description = "get person by id")
    public Person getById(@PathVariable Integer id){
        return dataStore.get(id);
    }
}
