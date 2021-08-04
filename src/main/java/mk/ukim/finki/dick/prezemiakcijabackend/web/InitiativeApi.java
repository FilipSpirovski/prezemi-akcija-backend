package mk.ukim.finki.dick.prezemiakcijabackend.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.Initiative;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.InitiativeDto;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.*;
import mk.ukim.finki.dick.prezemiakcijabackend.service.InitiativeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/initiatives")
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class InitiativeApi {

    private final InitiativeService initiativeService;

    @GetMapping
    public ResponseEntity<List<Initiative>> getAllInitiatives() {
        List<Initiative> initiatives = this.initiativeService.findAll();

        return ResponseEntity.ok(initiatives);
    }

    @GetMapping("/initiated-by/{initiatorEmail}")
    public ResponseEntity<List<Initiative>> getInitiativesInitiatedBy(@PathVariable String initiatorEmail) {
        List<Initiative> initiatives = this.initiativeService.findAllByInitiatorEmail(initiatorEmail);

        return ResponseEntity.ok(initiatives);
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity getInitiativesWithCategory(@PathVariable String category) {
        try {
            List<Initiative> initiatives = this.initiativeService.findAllByCategory(category);

            return ResponseEntity.ok(initiatives);
        } catch (InvalidCategoryName e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/by-type/{eventType}")
    public ResponseEntity getInitiativesWithEventType(@PathVariable String eventType) {
        try {
            List<Initiative> initiatives = this.initiativeService.findAllByEventType(eventType);

            return ResponseEntity.ok(initiatives);
        } catch (InvalidEventTypeName e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getInitiativeDetails(@PathVariable Long id) {
        try {
            Initiative initiative = this.initiativeService.findById(id);

            return ResponseEntity.ok(initiative);
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/new")
    public ResponseEntity addNewInitiative(@RequestBody InitiativeDto initiativeDto,
                                           @RequestHeader("Authorization") String authPayload,
                                           Authentication authentication) {
        try {
            Initiative initiative = this.initiativeService.createInitiative(initiativeDto, authPayload, authentication);

            return ResponseEntity.ok(initiative);
        } catch (ConstraintViolationException | InvalidCategoryName | InvalidEventTypeName | InvalidDateAndTime e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (ForumNotCreated e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity updateExistingInitiative(@PathVariable Long id,
                                                   @RequestBody InitiativeDto initiativeDto) {
        try {
            Initiative initiative = this.initiativeService.editInitiative(id, initiativeDto);

            return ResponseEntity.ok(initiative);
        } catch (ConstraintViolationException | InvalidCategoryName | InvalidEventTypeName | InvalidDateAndTime e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/add-participant")
    public ResponseEntity addParticipantToExistingInitiative(@PathVariable Long id, Authentication authentication) {
        try {
            Initiative initiative = this.initiativeService.addParticipantToInitiative(id, authentication);

            return ResponseEntity.ok(initiative);
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/remove-participant")
    public ResponseEntity removeParticipantFromExistingInitiative(@PathVariable Long id, Authentication authentication) {
        try {
            Initiative initiative = this.initiativeService.removeParticipantFromInitiative(id, authentication);

            return ResponseEntity.ok(initiative);
        } catch (InitiativeNotFound | ParticipantNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteExistingInitiative(@PathVariable Long id) {
        try {
            boolean result = this.initiativeService.deleteInitiative(id);

            if (result) {
                String message = String.format("The initiative with the provided id (%d) was successfully deleted.", id);

                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (InitiativeNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
