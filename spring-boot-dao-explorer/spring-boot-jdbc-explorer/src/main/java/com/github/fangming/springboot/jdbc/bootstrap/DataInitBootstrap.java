package com.github.fangming.springboot.jdbc.bootstrap;

import com.github.fangming.springboot.jdbc.dao.RegistrationDaoImpl;
import com.github.fangming.springboot.jdbc.model.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitBootstrap implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitBootstrap.class);
    private final RegistrationDaoImpl registrationDao;

    @Autowired
    public DataInitBootstrap(RegistrationDaoImpl registrationDao) {
        this.registrationDao = registrationDao;
    }


    @Override
    public void run(String... args) throws Exception {
        registrationDao.initTable();
        registrationDao.create(new Registration(1L,"Jack","Ma", 30));
        registrationDao.create(new Registration(2L,"Lucy","Li", 20));
    }
}
