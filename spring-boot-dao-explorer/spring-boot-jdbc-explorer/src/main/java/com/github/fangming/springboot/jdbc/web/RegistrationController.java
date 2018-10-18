package com.github.fangming.springboot.jdbc.web;

import com.github.fangming.springboot.jdbc.dao.RegistrationDaoImpl;
import com.github.fangming.springboot.jdbc.model.Registration;
import com.github.fangming.springboot.jdbc.service.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("registraton")
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;

    @Autowired
    public RegistrationController(RegistrationServiceImpl registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("list")
    public List<Registration> list(){
        return registrationService.getAll();
    }

    @GetMapping(value = "show/{id}", produces = "application/json")
    public Registration getById(@PathVariable Long id){
        return registrationService.getById(id);
    }

    @GetMapping("dirtyRead/{isolationLevel}")
    public String dirtyRead(@PathVariable("isolationLevel") int isolationLevel){
        try {
            return registrationService.dirtyRead(isolationLevel);
        }catch (Exception e){
            return "Interner error happen";
        }
    }

    @GetMapping("repeatRead/{isolationLevel}")
    public String repeatRead(@PathVariable("isolationLevel") int isolationLevel){
        try {
            return registrationService.repeatableRead(isolationLevel);
        }catch (Exception e){
            return "Interner error happen";
        }
    }

}
