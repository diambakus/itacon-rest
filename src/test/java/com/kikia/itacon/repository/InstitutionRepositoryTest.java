package com.kikia.itacon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.kikia.itacon.domain.Activity;
import com.kikia.itacon.domain.Institution;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InstitutionRepositoryTest {

	@Autowired
	private InstitutionRepository institutionRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Before
	public void setUp() {

	}

	@Test
	public void getInstitution_shouldReturnInstitutionInfo() {
		Optional<Institution> savedInstitution = Optional
				.ofNullable(testEntityManager.persistFlushFind(new Institution("Pesca")));
		
		Optional<Institution> institution = institutionRepository.findInstitutionByName("Pesca");		
		assertThat(institution.get().getName()).isEqualTo(savedInstitution.get().getName());
	}

	@Test
	public void getInstitution_shouldReturnInstitutionActivities() {
        
		Institution institution = new Institution("I");
		
        Activity regCivil = new Activity("Activity A", new BigDecimal("123"));
		Activity identifCivil = new Activity("Activity B", new BigDecimal("500"));
		
		
		institution.addActivity(identifCivil);
		institution.addActivity(regCivil);
	
		
		Optional<Institution> savedInstitution = Optional.ofNullable(testEntityManager.persistFlushFind(institution));
		Optional<Institution> retrievedInstitution = institutionRepository.findInstitutionByName("I");
		assertThat(retrievedInstitution.get().getActivities().size()).isEqualTo(2);
		assertThat(retrievedInstitution.get().getActivities().size())
				.isEqualTo(savedInstitution.get().getActivities().size());
	}
}