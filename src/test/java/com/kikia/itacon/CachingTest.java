package com.kikia.itacon;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kikia.itacon.domain.Institution;
import com.kikia.itacon.repository.InstitutionRepository;
import com.kikia.itacon.service.InstitutionService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class CachingTest {

	@Autowired
	private InstitutionService institutionService;
	@MockBean
	private InstitutionRepository institutionRepository;

	@Test
	public void getInstitutionWithCaching_shouldReturnInstitutionDetails() throws Exception {
		when(institutionRepository.findInstitutionByName(Mockito.anyString()))
				.thenReturn(Optional.ofNullable(new Institution("Pesca")));
		
		institutionService.findInstitutionByName("Pesca");
		institutionService.findInstitutionByName("Pesca");
		
		//Verify if it called repository once
		verify(institutionRepository, times(1)).findInstitutionByName("Pesca");
		
	}
}
