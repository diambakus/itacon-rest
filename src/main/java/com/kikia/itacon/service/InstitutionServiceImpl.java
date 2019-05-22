package com.kikia.itacon.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kikia.itacon.domain.Activity;
import com.kikia.itacon.domain.Institution;
import com.kikia.itacon.exceptionshandlers.InstitutionNotFoundException;
import com.kikia.itacon.repository.InstitutionRepository;

@Service
public class InstitutionServiceImpl implements InstitutionService {

	private InstitutionRepository institutionRepository;

	@Autowired
	public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
		this.institutionRepository = institutionRepository;
	}

	@Override
	@Cacheable("institutions")
	public Optional<Institution> findInstitutionById(Long id) {

		Optional<Institution> institution = institutionRepository.findById(id);
		if (!institution.isPresent())
			throw new InstitutionNotFoundException();
		return institution.map(Institution::getName).map(Institution::new);
	}

	@Override
	@Cacheable("institutions")
	public Optional<Institution> findInstitutionByName(String institutionName) {
		Optional<Institution> institution = institutionRepository.findInstitutionByName(institutionName);
		if (!institution.isPresent())
			throw new InstitutionNotFoundException();
		return institution.map(Institution::getName).map(Institution::new);
	}

	@Override
	public Set<Activity> findAllInstitutionActivities(Long anyLong) {
		Optional<Institution> institution = institutionRepository.findById(anyLong);
		return institution.map(institutionParam-> {
			return institutionParam.getActivities();
		}).orElseThrow(InstitutionNotFoundException::new);
	}

	@Override
	public Optional<Institution> saveInstitution(Institution institution) {
		return Optional.ofNullable(institutionRepository.save(institution));
	}

	@Override
	public Iterable<Institution> getInstitutions() {
		return institutionRepository.findAll();
	}
}