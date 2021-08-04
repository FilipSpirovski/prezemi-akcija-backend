package mk.ukim.finki.dick.prezemiakcijabackend.service;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Comment;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.CommentDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {

//    List<Comment> findAll();

    List<Comment> findAllByForumId(Long forumId);

//    List<Comment> findAllBySubmitterEmail(String submitterEmail);

    Comment findById(Long commentId);

    Comment createComment(CommentDto commentDto, Authentication authentication);

    Comment editComment(Long commentId, CommentDto commentDto);

//    Comment likeComment(Long commentId);
//
//    Comment dislikeComment(Long commentId);

    boolean deleteComment(Long commentId);
}
