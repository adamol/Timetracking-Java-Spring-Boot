package com.example.timetracking;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.example.timetracking.model.TimeEntryRepository;
import com.example.timetracking.model.TimeEntry;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TimeEntryControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private TimeEntryRepository repository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void it_works() throws Exception {
		TimeEntry timeEntry = new TimeEntry();
		
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		
		timeEntry.setDay(date);
		
		java.sql.Time time = new java.sql.Time(Calendar.getInstance().getTime().getTime() );
		
		timeEntry.setStart(time);
		timeEntry.setEnd(time);
		timeEntry.setDescription("here is some text");
		
		repository.save(timeEntry);
		
		mockMvc.perform(get("/api/time-entries/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("description", is("here is some text")));
	}
}
