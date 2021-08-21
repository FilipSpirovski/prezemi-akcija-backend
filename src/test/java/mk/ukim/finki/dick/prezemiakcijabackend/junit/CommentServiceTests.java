package mk.ukim.finki.dick.prezemiakcijabackend.junit;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Comment;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.Forum;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.CommentDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.CommentRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.ForumRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.service.CommentService;
import mk.ukim.finki.dick.prezemiakcijabackend.service.impl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTests {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private Validator validator;

    private CommentService commentService;

    @Before
    public void init() {
        this.commentService = Mockito.spy(new CommentServiceImpl(this.commentRepository, this.forumRepository, this.validator));
    }

    @Test
    public void findAllByForumIdWithValidId() {
        Long forumId = 55L;
        String text = "commentPlaceholder";
        String submitterEmail = "user@students.finki.ukim.mk";
        Forum forum = new Forum(forumId);
        Comment comment = new Comment(forum, submitterEmail, text);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        Mockito.when(this.commentRepository.findAllByForumId(forumId)).thenReturn(comments);

        Assert.assertEquals(comments, this.commentService.findAllByForumId(forumId));
        Mockito.verify(this.commentService).findAllByForumId(forumId);
    }

    @Test
    public void findByIdWithValidId() {
        Long forumId = 55L;
        Long commentId = 33L;
        String text = "commentPlaceholder";
        String submitterEmail = "user@students.finki.ukim.mk";
        Forum forum = new Forum(forumId);
        Comment comment = new Comment(forum, submitterEmail, text);

        Mockito.when(this.commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Assert.assertEquals(comment, this.commentService.findById(commentId));
        Mockito.verify(this.commentService).findById(commentId);
    }

    @Test
    public void createCommentWithValidData() {
        Long id = 55L;
        String text = "commentPlaceholder";
        String submitterEmail = "user@students.finki.ukim.mk";
        Forum forum = new Forum(id);
        CommentDto commentDto = new CommentDto(id, text);
        Comment comment = new Comment(forum, submitterEmail, commentDto.getText());
        Authentication authentication = createAuthenticationObject(submitterEmail);
        Set<ConstraintViolation<CommentDto>> constraintViolations = this.validator.validate(commentDto);

        Mockito.when(this.validator.validate(commentDto)).thenReturn(constraintViolations);
        Mockito.when(this.forumRepository.findById(id)).thenReturn(Optional.of(forum));
        Mockito.when(this.commentRepository.save(comment)).thenReturn(comment);

        Assert.assertEquals(comment, this.commentService.createComment(commentDto, authentication));
        Mockito.verify(this.commentService).createComment(commentDto, authentication);
    }

    @Test
    public void editCommentWithValidData() {
        Long forumId = 55L;
        Long commentId = 33L;
        String text = "commentPlaceholder";
        String submitterEmail = "user@students.finki.ukim.mk";
        Forum forum = new Forum(forumId);
        CommentDto commentDto = new CommentDto(forumId, text);
        Comment comment = new Comment(forum, submitterEmail, commentDto.getText());

        Mockito.when(this.commentRepository.save(comment)).thenReturn(comment);
        Mockito.when(this.commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Assert.assertEquals(comment, this.commentService.editComment(commentId, commentDto));
        Mockito.verify(this.commentService).editComment(commentId, commentDto);
    }

    @Test
    public void deleteCommentNotSuccessful() {
        Long forumId = 55L;
        Long commentId = 33L;
        String text = "commentPlaceholder";
        String submitterEmail = "user@students.finki.ukim.mk";
        Forum forum = new Forum(forumId);
        Comment comment = new Comment(forum, submitterEmail, text);

        Mockito.when(this.commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        this.commentService.deleteComment(commentId);
        Mockito.verify(this.commentRepository, Mockito.times(1)).delete(comment);

        Assert.assertFalse(this.commentService.deleteComment(commentId));
    }

    private Authentication createAuthenticationObject(String email) {
        return new UsernamePasswordAuthenticationToken(email, null,
                Collections.singleton(Role.ROLE_USER));
    }
}
