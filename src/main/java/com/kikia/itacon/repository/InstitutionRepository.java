package com.kikia.itacon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.kikia.itacon.domain.Institution;

@RepositoryRestResource(collectionResourceRel="institutions", path="institutions")
public interface InstitutionRepository extends PagingAndSortingRepository<Institution, Long> {

	@Query("select institution from Institution institution where institution.name = ?1")
	Optional<Institution> findInstitutionByName(String name);
}