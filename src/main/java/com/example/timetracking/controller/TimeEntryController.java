package com.example.timetracking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.timetracking.model.TimeEntryRepository;
import com.example.timetracking.model.TimeEntry;

@RestController
@RequestMapping("/api/time-entries")
public class TimeEntryController {
	
	@Autowired
	private TimeEntryRepository repository;
	
	@GetMapping("/")
	@ResponseBody
	public Iterable<TimeEntry> index() {
		return repository.findAll();
	}
	
	@PostMapping("/")
	@ResponseBody
	public TimeEntry store(@RequestBody TimeEntry timeEntry) {
		repository.save(timeEntry);
		
		return timeEntry;
	}
	
	@GetMapping("/{entryId}")
	@ResponseBody
	public Optional<TimeEntry> show(@PathVariable("entryId") Long entryId) {
		return repository.findById(entryId);
	}
	
	@PutMapping("/{entryId}")
	@ResponseBody
	public TimeEntry update(@RequestBody TimeEntry timeEntry, @PathVariable("entryId") Long entryId) {
		timeEntry.setId(entryId);
		
		return repository.save(timeEntry);
	}
	
	@DeleteMapping("/{entryId}")
	public ResponseEntity<?> delete(@PathVariable("entryId") Long entryId) {
		repository.deleteById(entryId);
		
		return ResponseEntity.ok().build();
	}
}