package mk.ukim.finki.dick.prezemiakcijabackend.service;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Initiative;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.dto.InitiativeDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface InitiativeService {

    List<Initiative> findAll();

    List<Initiative> findAllByInitiatorEmail(String initiatorEmail);

    List<Initiative> findAllByCategory(String categoryName);

    List<Initiative> findAllByEventType(String eventTypeName);

    Initiative findById(Long initiativeId);

    Initiative createInitiative(InitiativeDto initiativeDto, String authPayload, Authentication authentication);

    Initiative editInitiative(Long initiativeId, InitiativeDto initiativeDto);

    Initiative addParticipantToInitiative(Long initiativeId, Authentication authentication);

    Initiative removeParticipantFromInitiative(Long initiativeId, Authentication authentication);

    boolean deleteInitiative(Long initiativeId);
}
