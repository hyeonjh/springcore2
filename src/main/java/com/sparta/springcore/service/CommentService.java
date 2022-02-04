package com.sparta.springcore.service;

import com.sparta.springcore.dto.CommentRequestDto;
import com.sparta.springcore.model.Comment;
import com.sparta.springcore.model.Memo;
import com.sparta.springcore.model.User;
import com.sparta.springcore.repository.CommentRepository;
import com.sparta.springcore.repository.MemoRepository;
import com.sparta.springcore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(CommentRepository commentRepository ){
        this.commentRepository = commentRepository;

    }

//코멘트 저장 userid연결.
    public Comment createComment(CommentRequestDto requestDto,Long userId) {
        Comment comment = new Comment(requestDto,userId);
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getComment() {
        return commentRepository.findAllByOrderByModifiedAtDesc();
    }

    public void deleteComments(Long commentId) {commentRepository.deleteById(commentId);
    }
}
