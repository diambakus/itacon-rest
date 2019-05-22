package com.kikia.itacon.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kikia.itacon.domain.Activity;
import com.kikia.itacon.domain.Institution;
import com.kikia.itacon.service.InstitutionService;

@RunWith(SpringRunner.class)
@WebMvcTest(InstitutionController.class)
public class InstitutionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private InstitutionService institutionService;

	@Test
	public void getInstitution_shouldReturnInstitution() throws Exception {

		Optional<Institution> institutionInstance = Optional.ofNullable(new Institution("Pesca"));
		given(institutionService.findInstitutionById(Mockito.anyLong())).willReturn(institutionInstance);

		mockMvc.perform(MockMvcRequestBuilders.get("/institutions/1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("name").value("Pesca"));
	}

	@Test
	public void getInstitutionByName_shouldReturnInstitution() throws Exception {
		Optional<Institution> institutionInstance = Optional.ofNullable(new Institution("Pesca"));
		when(institutionService.findInstitutionByName(Mockito.anyString())).thenReturn(institutionInstance);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/institutions/search/Pesca").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(content().json("{'name':'Pesca'}"));
	}

	@Test
	public void getInstitution_shouldReturnInstitutionNotFound() throws Exception {
		when(institutionService.findInstitutionById(Mockito.anyLong())).thenReturn(Optional.empty());
		// then
		mockMvc.perform(MockMvcRequestBuilders.get("/institutions/-1").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenInstitution_shouldReturnInstitutionActivities() throws Exception {
		Institution institution = new Institution("Justiça");
		Activity activity = new Activity("Registo civil", new BigDecimal("123"));
		institution.addActivity(activity);
		when(institutionService.findAllInstitutionActivities(Mockito.anyLong()))
				.thenReturn(institution.getActivities());

		mockMvc.perform(get("/institutions/1/activities").accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void postInstitution_shouldBeOK() throws Exception {
		Institution institution = new Institution("Justiça");

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		String requestJson = objectWriter.writeValueAsString(institution);

		mockMvc.perform(post("/institutions").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void getInstitutions() throws Exception {
		mockMvc.perform(get("/institutions").accept(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk());
	}
}