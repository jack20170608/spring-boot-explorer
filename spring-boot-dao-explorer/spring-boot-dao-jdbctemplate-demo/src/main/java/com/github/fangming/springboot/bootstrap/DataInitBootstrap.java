package com.github.fangming.springboot.bootstrap;

import com.github.fangming.springboot.model.Comment;
import com.github.fangming.springboot.model.User;
import com.github.fangming.springboot.repository.CommentRepository;
import com.github.fangming.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitBootstrap implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(DataInitBootstrap.class);

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public DataInitBootstrap(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User jack = new User(-1000L, "jack", User.Sex.MAN, LocalDate.of(2018,8,8)
                        ,100, true, LocalDateTime.of(2017,12,11,5,23,34));
        User lucy = new User(-1001L, "lucy", User.Sex.WOMAN, LocalDate.of(2018,8,8)
            ,100, true, LocalDateTime.of(2010,12,11,2,23,34));
        userRepository.save(jack);
        userRepository.save(lucy);

        Comment c1 = new Comment("100", -1000L, "Good good study", LocalDateTime.of(1999,11,11,12,12,00),
            1000);
        commentRepository.save(c1);

    }
}
