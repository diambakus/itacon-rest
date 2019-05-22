package com.kikia.itacon.service;

import java.util.Optional;
import java.util.Set;

import com.kikia.itacon.domain.Activity;
import com.kikia.itacon.domain.Institution;

public interface InstitutionService {
	Optional<Institution> findInstitutionById(Long Id);

	Optional<Institution> findInstitutionByName(String anyString);

	Set<Activity> findAllInstitutionActivities(Long anyLong);

	Optional<Institution> saveInstitution(Institution institution);

	Iterable<Institution> getInstitutions();
}