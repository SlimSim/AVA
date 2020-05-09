package com.slimsimapps.ava.meeting;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetingRepository extends CrudRepository<Meeting, Integer> {

    public List<Meeting> findAll();
}
