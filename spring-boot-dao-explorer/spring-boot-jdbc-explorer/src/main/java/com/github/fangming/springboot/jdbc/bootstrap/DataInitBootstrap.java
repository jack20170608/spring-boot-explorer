package com.github.fangming.springboot.jdbc.bootstrap;

import com.github.fangming.springboot.jdbc.model.Registration;
import com.github.fangming.springboot.jdbc.service.RegistrationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitBootstrap implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitBootstrap.class);
    private final RegistrationServiceImpl registrationService;

    @Autowired
    public DataInitBootstrap(RegistrationServiceImpl registrationService) {
        this.registrationService = registrationService;
    }


    @Override
    public void run(String... args) throws Exception {
        registrationService.initTable();
        registrationService.create(new Registration(1L,"Jack","Ma", 30));
        registrationService.create(new Registration(2L,"Lucy","Li", 20));
    }
}
