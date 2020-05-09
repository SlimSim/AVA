package com.slimsimapps.ava.participant;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParticipantRepository extends CrudRepository<Participant, Integer> {

    public List<Participant> findAll();

    // Here spring will look at the Topic-attribute on Course, and then the Id-attribute on that object
    public List<Participant> findByMeetingId( int topicId );

}
