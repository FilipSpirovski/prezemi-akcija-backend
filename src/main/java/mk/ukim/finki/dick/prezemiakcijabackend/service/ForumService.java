package mk.ukim.finki.dick.prezemiakcijabackend.service;

import mk.ukim.finki.dick.prezemiakcijabackend.domain.Forum;

import java.util.List;

public interface ForumService {

//    List<Forum> findAll();
//
//    Forum findById(Long forumId);

    Forum findByInitiative(Long initiativeId);

    Forum createForum(Long initiativeId);

//    boolean deleteForum(Long forumId);
}
