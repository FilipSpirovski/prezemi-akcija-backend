package mk.ukim.finki.dick.prezemiakcijabackend.junit;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Forum;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.Initiative;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.InitiativeDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Category;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.EventType;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.enums.Role;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.InvalidDateAndTime;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.InvalidEventTypeName;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.ParticipantNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.ForumRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.InitiativeRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.service.ForumService;
import mk.ukim.finki.dick.prezemiakcijabackend.service.InitiativeService;
import mk.ukim.finki.dick.prezemiakcijabackend.service.impl.ForumServiceImpl;
import mk.ukim.finki.dick.prezemiakcijabackend.service.impl.InitiativeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InitiativeServiceTests {

    @Autowired
    private Validator validator;

    @Mock
    private ForumRepository forumRepository;

    @Mock
    private InitiativeRepository initiativeRepository;

    private InitiativeService initiativeService;

    private List<Initiative> initiatives;

    @Before
    public void init() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        ForumService forumService = Mockito.spy(
                new ForumServiceImpl(this.forumRepository, this.initiativeRepository));
        this.initiativeService = Mockito.spy(
                new InitiativeServiceImpl(this.initiativeRepository, forumService, this.validator));

        this.initiatives = Stream.of(
                new Initiative(1L, "filip.spirovski@gmail.com", Category.HEALTH,
                        "Processed foods are killing you one bite at a time!",
                        "The term “processed food” can cause some confusion because most foods are processed " +
                                "in some way. Mechanical processing — such as grinding beef, heating vegetables, or " +
                                "pasteurizing foods — does not necessarily make foods unhealthful. If the processing " +
                                "does not add chemicals or ingredients, it does not tend to lessen the healthfulness " +
                                "of the food. However, there is a difference between mechanical processing and chemical " +
                                "processing. Chemically processed foods often only contain refined ingredients and " +
                                "artificial substances, with little nutritional value. They tend to have added chemical " +
                                "flavoring agents, colors, and sweeteners. These ultra-processed foods are sometimes " +
                                "called “cosmetic” foods, as compared with whole foods.",
                        LocalDateTime.of(2021, 10, 10, 21, 45),
                        EventType.IN_GROUP, "Public Room", new ArrayList<>()),
                new Initiative(2L, "viktor.sadikovic@gmail.com", Category.ENVIRONMENT,
                        "RIP to breathable air in Skopje!",
                        "Many things that are useful to people produce pollution. Cars spew pollutants from " +
                                "their exhaust pipes. Burning coal to create electricity pollutes the air. Industries " +
                                "and homes generate garbage and sewage that can pollute the land and water. " +
                                "Pesticides—chemical poisons used to kill weeds and insects—seep into waterways and " +
                                "harm wildlife. All living things—from one-celled microbes to blue whales—depend on " +
                                "Earth’s supply of air and water. When these resources are polluted, all forms of " +
                                "life are threatened.",
                        LocalDateTime.of(2021, 10, 10, 21, 45),
                        EventType.IN_GROUP, "Central Park Skopje", new ArrayList<>())
        ).collect(Collectors.toList());
    }

    @Test
    public void findAllTest() {
        when(this.initiativeRepository.findAll()).thenReturn(this.initiatives);

        assertEquals(this.initiatives, this.initiativeService.findAll());
    }

    @Test
    public void findAllByInitiatorEmailTest() {
        String initiatorEmail = "filip.spirovski@gmail.com";
        Predicate<Initiative> byInitiatorEmail = initiative -> initiative.getInitiatorEmail().equals(initiatorEmail);
        List<Initiative> filteredList = this.initiatives
                .stream()
                .filter(byInitiatorEmail)
                .collect(Collectors.toList());

        when(this.initiativeRepository.findAllByInitiatorEmail(initiatorEmail)).thenReturn(filteredList);

        assertEquals(filteredList, this.initiativeService.findAllByInitiatorEmail(initiatorEmail));
    }

    @Test
    public void findAllByCategoryTest() {
        Category category = Category.HEALTH;
        Predicate<Initiative> byCategory = initiative -> initiative.getCategory().equals(category);
        List<Initiative> filteredList = this.initiatives
                .stream()
                .filter(byCategory)
                .collect(Collectors.toList());

        when(this.initiativeRepository.findAllByCategory(category)).thenReturn(filteredList);

        assertEquals(filteredList, this.initiativeService.findAllByCategory(category.toString()));
    }

    @Test
    public void findAllByEventTypeTest() {
        EventType eventType = EventType.ONLINE;
        Predicate<Initiative> byEventType = initiative -> initiative.getEventType().equals(eventType);
        List<Initiative> filteredList = this.initiatives
                .stream()
                .filter(byEventType)
                .collect(Collectors.toList());

        when(this.initiativeRepository.findAllByEventType(eventType)).thenReturn(filteredList);

        assertEquals(filteredList, this.initiativeService.findAllByEventType(eventType.toString()));
    }

    @Test
    public void findByIdTest() {
        Long id = 2L;
        Predicate<Initiative> byId = initiative -> initiative.getId().equals(id);
        Optional<Initiative> initiative = this.initiatives
                .stream()
                .filter(byId)
                .findFirst();

        when(this.initiativeRepository.findById(id)).thenReturn(initiative);

        assertEquals(initiative.get(), this.initiativeService.findById(id));
    }

    @Test
    public void createInitiativeTest() {
        String user = "filip.spirovski@gmail.com";
        Role role = Role.ROLE_USER;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(role));

        InitiativeDto initiativeDto = new InitiativeDto(
                Category.ANIMALS.toString(),
                "Stop animal cruelty!",
                "Animals don’t have the capability to talk, but it doesn’t mean that they don’t feel " +
                        "anything. Like, people, they can also feel the pain of every violence people do. Animals " +
                        "are not machines because deep inside they can feel the helplessness of their situation.",
                "2021-10-10T19:15:00", EventType.IN_GROUP.toString(), "Kantina");

        Initiative newInitiative = new Initiative(
                3L, user, Category.ANIMALS,
                "Stop animal cruelty!",
                "Animals don’t have the capability to talk, but it doesn’t mean that they don’t feel " +
                        "anything. Like, people, they can also feel the pain of every violence people do. Animals " +
                        "are not machines because deep inside they can feel the helplessness of their situation.",
                LocalDateTime.of(2021, 11, 10, 19, 15),
                EventType.IN_GROUP, "Kantina", new ArrayList<>());
        when(this.initiativeRepository.save(any(Initiative.class))).thenReturn(newInitiative);

        Forum newForum = new Forum(3L, 3L, new ArrayList<>());
        when(this.forumRepository.save(any(Forum.class))).thenReturn(newForum);

        assertEquals(newInitiative, this.initiativeService.createInitiative(initiativeDto, token));
    }

    @Test
    public void editInitiativeTest() {
        Initiative existingInitiative = this.initiatives.get(0);
        when(this.initiativeRepository.findById(1L)).thenReturn(Optional.of(existingInitiative));
        when(this.initiativeRepository.save(existingInitiative)).thenReturn(existingInitiative);

        InitiativeDto initiativeDto = new InitiativeDto(
                Category.HEALTH.toString(),
                "Processed foods are killing you one bite at a time!",
                "The term “processed food” can cause some confusion because most foods are processed " +
                        "in some way. Mechanical processing — such as grinding beef, heating vegetables, or " +
                        "pasteurizing foods — does not necessarily make foods unhealthful. If the processing " +
                        "does not add chemicals or ingredients, it does not tend to lessen the healthfulness " +
                        "of the food. However, there is a difference between mechanical processing and chemical " +
                        "processing. Chemically processed foods often only contain refined ingredients and " +
                        "artificial substances, with little nutritional value. They tend to have added chemical " +
                        "flavoring agents, colors, and sweeteners. These ultra-processed foods are sometimes " +
                        "called “cosmetic” foods, as compared with whole foods.",
                "2021-10-10T21:30:00", EventType.IN_GROUP.toString(), "Public Room");

        assertEquals(LocalDateTime.of(2021, 10, 10, 21, 30),
                this.initiativeService.editInitiative(1L, initiativeDto).getScheduledFor());
    }

    @Test
    public void addParticipantToInitiativeTest() {
        String user = "filip.spirovski@gmail.com";
        Role role = Role.ROLE_USER;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(role));

        Initiative existingInitiative = this.initiatives.get(0);
        when(this.initiativeRepository.findById(1L)).thenReturn(Optional.of(existingInitiative));
        when(this.initiativeRepository.save(existingInitiative)).thenReturn(existingInitiative);

        assertEquals(1,
                this.initiativeService.addParticipantToInitiative(1L, token)
                        .getParticipantEmails().size());
        assertEquals(user,
                this.initiativeService.addParticipantToInitiative(1L, token)
                        .getParticipantEmails().get(0));
    }

    @Test
    public void removeParticipantFromInitiativeWithoutExceptionTest() {
        String user = "filip.spirovski@gmail.com";
        Role role = Role.ROLE_USER;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(role));

        Initiative existingInitiative = this.initiatives.get(0);
        existingInitiative.getParticipantEmails().add(user);
        when(this.initiativeRepository.findById(1L)).thenReturn(Optional.of(existingInitiative));
        when(this.initiativeRepository.save(existingInitiative)).thenReturn(existingInitiative);

        assertEquals(0,
                this.initiativeService.removeParticipantFromInitiative(1L, token)
                        .getParticipantEmails().size());
    }

    @Test
    public void removeParticipantFromInitiativeWithExceptionTest() {
        String user = "filip.spirovski@gmail.com";
        Role role = Role.ROLE_USER;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(role));

        Initiative existingInitiative = this.initiatives.get(0);
        when(this.initiativeRepository.findById(1L)).thenReturn(Optional.of(existingInitiative));
        when(this.initiativeRepository.save(existingInitiative)).thenReturn(existingInitiative);

        assertThrows(ParticipantNotFound.class,
                () -> this.initiativeService.removeParticipantFromInitiative(1L, token));
    }

    @Test
    public void deleteInitiativeTest() {
        Initiative existingInitiative = this.initiatives.get(0);
        when(this.initiativeRepository.findById(1L)).thenReturn(Optional.of(existingInitiative));

        this.initiativeService.deleteInitiative(1L);

        verify(this.initiativeRepository, times(2)).findById(1L);
        verify(this.initiativeRepository, times(1)).delete(existingInitiative);
    }

    @Test
    public void validateCategoryNameTest1() {
        Category category = Category.HEALTH;

        assertEquals(category, this.initiativeService.validateCategoryName(category.toString()));
    }

    @Test
    public void validateCategoryNameTest2() {
        Category category = Category.ANIMALS;

        assertEquals(category, this.initiativeService.validateCategoryName(category.toString()));
    }

    @Test
    public void validateEventTypeNameTest1() {
        String invalidEventTypeName = "in group";

        assertThrows(InvalidEventTypeName.class,
                () -> this.initiativeService.validateEventTypeName(invalidEventTypeName));
    }

    @Test
    public void validateEventTypeNameTest2() {
        EventType eventType = EventType.IN_GROUP;

        assertEquals(eventType, this.initiativeService.validateEventTypeName(eventType.toString()));
    }

    @Test
    public void checkDtoForViolationsTest2() {
        InitiativeDto initiativeDto = new InitiativeDto(
                null,
                "Processed foods are killing you one bite at a time!",
                "The term “processed food” can cause some confusion because most foods are processed " +
                        "in some way. Mechanical processing — such as grinding beef, heating vegetables, or " +
                        "pasteurizing foods — does not necessarily make foods unhealthful. If the processing " +
                        "does not add chemicals or ingredients, it does not tend to lessen the healthfulness " +
                        "of the food. However, there is a difference between mechanical processing and chemical " +
                        "processing. Chemically processed foods often only contain refined ingredients and " +
                        "artificial substances, with little nutritional value. They tend to have added chemical " +
                        "flavoring agents, colors, and sweeteners. These ultra-processed foods are sometimes " +
                        "called “cosmetic” foods, as compared with whole foods.",
                "2021-10-10T21:30:00", EventType.IN_GROUP.toString(), "Public Room");

        assertThrows(ConstraintViolationException.class,
                () -> this.initiativeService.checkDtoForViolations(initiativeDto));
    }

    @Test
    public void checkDtoForViolationsTest3() {
        InitiativeDto initiativeDto = new InitiativeDto(
                Category.HEALTH.toString(),
                "Processed foods are killing you one bite at a time!",
                "The term “processed food” can cause some confusion because most foods are processed " +
                        "in some way. Mechanical processing — such as grinding beef, heating vegetables, or " +
                        "pasteurizing foods — does not necessarily make foods unhealthful. If the processing " +
                        "does not add chemicals or ingredients, it does not tend to lessen the healthfulness " +
                        "of the food. However, there is a difference between mechanical processing and chemical " +
                        "processing. Chemically processed foods often only contain refined ingredients and " +
                        "artificial substances, with little nutritional value. They tend to have added chemical " +
                        "flavoring agents, colors, and sweeteners. These ultra-processed foods are sometimes " +
                        "called “cosmetic” foods, as compared with whole foods.",
                "2021-08-10T21:30:00", EventType.IN_GROUP.toString(), "Public Room");

        assertThrows(InvalidDateAndTime.class,
                () -> this.initiativeService.checkDtoForViolations(initiativeDto));
    }
}
