package com.kikia.itacon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.kikia.itacon.domain.Institution;
import com.kikia.itacon.exceptionshandlers.InstitutionNotFoundException;
import com.kikia.itacon.repository.InstitutionRepository;

@RunWith(MockitoJUnitRunner.class)
public class InstitutionServiceTest {

	@Mock
	private InstitutionRepository institutionRepository;
	private InstitutionService institutionService;
	
	@Before
	public void setUp() throws Exception {
		institutionService = new InstitutionServiceImpl(institutionRepository);
	}
	
	@Test
	public void getInstitution_shouldReturnsInstitutionInformation() {
		long anyId = Mockito.anyLong();
		Optional<Institution> institutionInstance = Optional.ofNullable(new Institution("Pesca"));
		when(institutionRepository.findById(anyId)).thenReturn(institutionInstance);
		
		Optional<Institution> institution = institutionService.findInstitutionById(anyId);
		
		assertThat(institution.get().getName()).isEqualTo("Pesca");
	}
	
	@Test(expected = InstitutionNotFoundException.class)
	public void tryToGetInstitutionNonexistent() throws Exception {
		when(institutionRepository.findById(1L)).thenReturn(Optional.empty());
		
		institutionService.findInstitutionById(1L);
	}
	
	@Test
	public void saveInstitution_shouldReturnSuccess() throws Exception {
		
		Institution institution = new Institution("Agricultura");
				
		assertThat(institutionService.saveInstitution(institution).isPresent() == true);
	}
	
	@Test
	public void registerInstitution_shouldReturnNumberOfSavedInstitutions() throws Exception {
		
		Institution institution1 = new Institution("Agricultura");
		Institution institution2 = new Institution("Pesca");
		Institution institution3 = new Institution("Justiça");
		
		int numberOfSavedInstitutions = 0;
		
		if (institutionService.saveInstitution(institution1).isPresent()) numberOfSavedInstitutions++;
		if (institutionService.saveInstitution(institution2).isPresent()) numberOfSavedInstitutions++;
		if (institutionService.saveInstitution(institution3).isPresent()) numberOfSavedInstitutions++;
		
		List<Institution> lista = new ArrayList<>();
		institutionService.getInstitutions().forEach(lista::add);
		
		assertThat(lista.size() == numberOfSavedInstitutions);
	}
}