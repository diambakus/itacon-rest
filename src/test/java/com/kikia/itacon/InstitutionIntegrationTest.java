package com.kikia.itacon;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.kikia.itacon.domain.Activity;
import com.kikia.itacon.domain.Institution;
import com.kikia.itacon.repository.InstitutionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = ItaconApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
public class InstitutionIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private InstitutionRepository institutionRepository;

	@Before
	public void setUp() {

	}

	@After
	public void deleteDataAfterTests() {
		institutionRepository.deleteAll();
	}

	@Test
	public void getInstitution_returnsInstitutionDetails() throws Exception {

		createInstitutionForTesting("Pesca");

		mockMvc.perform(get("/institutions/1").accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().json("{'name' : 'Pesca'}"));
	}

	@Test
	public void givenInstitution_shouldReturnInstitutionActivities() throws Exception {
		Institution institution = new Institution("Justiça");
		Activity activity = new Activity("Registo civil", new BigDecimal("123"));
		institution.addActivity(activity);

		Institution savedInstitution = institutionRepository.save(institution);

		mockMvc.perform(get("/institutions/" + savedInstitution.getId() + "/activities")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print()).andExpect(status().isOk());
	}

	private void createInstitutionForTesting(String institutionName) {
		institutionRepository.save(new Institution(institutionName));
	}
}