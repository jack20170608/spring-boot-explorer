package com.github.fangming.springboot.web;

import com.github.fangming.springboot.model.Comment;
import com.github.fangming.springboot.repository.CommentRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping(value = "list", produces = "application/json")
    public List<Comment> list(Model model){
        return commentRepository.findAll();
    }

    @GetMapping(value = "/show/{id}", produces = "application/json")
    public ResponseEntity<Comment> showComment(@PathVariable String id, Model model){
        Optional<Comment> commentOptional = commentRepository.findById(id);
        return commentOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Add a comment")
    @PostMapping(value = "/{userId}/add",  produces = "application/json")
    public ResponseEntity<Comment> addComment(@PathVariable Long userId, @RequestBody Comment comment){
        comment.setUserId(userId);
        Comment storedComment = commentRepository.save(comment);
        return ResponseEntity.ok(storedComment);
    }

    @ApiOperation(value = "Update a exists comment")
    @PutMapping(value = "/update/{id}", produces = "application/json")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody Comment newComment){
        Optional<Comment> oldComment = commentRepository.findById(id);
        return oldComment.map((c) -> {
            c.setUserId(newComment.getUserId());
            c.setContents(newComment.getContents());
            c.setFavouriteCount(newComment.getFavouriteCount());
            return ResponseEntity.ok(commentRepository.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }
}
