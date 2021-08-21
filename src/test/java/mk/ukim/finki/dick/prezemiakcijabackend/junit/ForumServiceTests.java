package mk.ukim.finki.dick.prezemiakcijabackend.junit;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Forum;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.ForumForInitiativeNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.ForumRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.InitiativeRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.service.ForumService;
import mk.ukim.finki.dick.prezemiakcijabackend.service.impl.ForumServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ForumServiceTests {

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private InitiativeRepository initiativeRepository;

    private ForumService forumService;

    @Before
    public void init() {
        this.forumService = Mockito.spy(new ForumServiceImpl(this.forumRepository, this.initiativeRepository));
    }

    @Test
    public void testFindByInitiativeWithValidId() {
        Long initiativeId = 33L;
        Forum forum = new Forum(initiativeId);

        Mockito.when(this.forumRepository.findByInitiativeId(initiativeId)).thenReturn(Optional.of(forum));

        Assert.assertEquals(forum, this.forumService.findByInitiative(initiativeId));
        Mockito.verify(this.forumService).findByInitiative(initiativeId);
    }

    @Test
    public void testCreateForumWithValidInput() {
        Long initiativeId = 55L;
        Forum forum = new Forum(initiativeId);

        Mockito.when(this.forumRepository.findByInitiativeId(initiativeId)).thenThrow(new ForumForInitiativeNotFound(initiativeId));
        Mockito.when(this.forumRepository.save(forum)).thenReturn(forum);

        Assert.assertEquals(forum, this.forumService.createForum(initiativeId));
        Mockito.verify(this.forumService).createForum(initiativeId);
    }
}
