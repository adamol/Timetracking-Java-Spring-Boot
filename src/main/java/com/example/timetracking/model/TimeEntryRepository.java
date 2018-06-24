package com.example.timetracking.model;

import org.springframework.data.repository.CrudRepository;

public interface TimeEntryRepository extends CrudRepository<TimeEntry, Long> {

}