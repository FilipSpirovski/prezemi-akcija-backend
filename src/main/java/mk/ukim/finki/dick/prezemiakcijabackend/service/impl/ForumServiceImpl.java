package mk.ukim.finki.dick.prezemiakcijabackend.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.Forum;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.ForumForInitiativeAlreadyExists;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.ForumForInitiativeNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.domain.exc.ForumNotFound;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.ForumRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.repository.InitiativeRepository;
import mk.ukim.finki.dick.prezemiakcijabackend.service.ForumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final InitiativeRepository initiativeRepository;

//    @Override
//    public List<Forum> findAll() {
//        return this.forumRepository.findAll();
//    }
//
//    @Override
//    public Forum findById(Long forumId) {
//        return this.forumRepository.findById(forumId)
//                .orElseThrow(() -> new ForumNotFound(forumId));
//    }

    @Override
    public Forum findByInitiative(Long initiativeId) {
        return this.forumRepository.findByInitiativeId(initiativeId)
                .orElseThrow(() -> new ForumForInitiativeNotFound(initiativeId));
    }

    @Override
    public Forum createForum(Long initiativeId) {
        Forum forum;

        try {
            forum = this.findByInitiative(initiativeId);
            throw new ForumForInitiativeAlreadyExists(initiativeId);
        } catch (ForumForInitiativeNotFound e) {
            forum = new Forum(initiativeId);

            return this.forumRepository.save(forum);
        }
    }

//    @Override
//    public boolean deleteForum(Long forumId) throws ForumNotFound {
//        Forum existingForum = this.findById(forumId);
//
//        this.forumRepository.delete(existingForum);
//        try {
//            existingForum = this.findById(forumId);
//
//            return false;
//        } catch (ForumNotFound e) {
//            return true;
//        }
//    }
//
//    private boolean checkIfInitiativeExists(Long initiativeId) {
//        return this.initiativeRepository.existsById(initiativeId);
//    }
}
