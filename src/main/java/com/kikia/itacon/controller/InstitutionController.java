package com.kikia.itacon.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kikia.itacon.domain.Activity;
import com.kikia.itacon.domain.Institution;
import com.kikia.itacon.exceptionshandlers.InstitutionNotFoundException;
import com.kikia.itacon.service.InstitutionService;

@RestController
@RequestMapping("/institutions")
public class InstitutionController {

	private InstitutionService institutionService;

	@Autowired
	public InstitutionController(InstitutionService institutionService) {
		this.institutionService = institutionService;
	}

	@GetMapping(value = "/{institutionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Institution retrieveInstitution(@PathVariable Long institutionId) {
		return institutionService.findInstitutionById(institutionId).orElseThrow(InstitutionNotFoundException::new);
	}

	@GetMapping(value = "/search/{institutionName}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Institution retrieveInstitutionByName(@PathVariable String institutionName) {
		return institutionService.findInstitutionByName(institutionName).orElseThrow(InstitutionNotFoundException::new);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void institutionNotFound(InstitutionNotFoundException institutionNotFoundException) {

	}

	@GetMapping(value = "/{institutionId}/activities")
	public Collection<Activity> retrieveAllInstitutionActivity(@PathVariable Long institutionId) {
		return institutionService.findAllInstitutionActivities(institutionId);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Institution> addInstitution(@RequestBody Institution institution) {
		if (institution == null)
			return ResponseEntity.noContent().build();
		else {
			institutionService.saveInstitution(institution);
			return new ResponseEntity<Institution>(institution, (HttpStatus.OK));
		}
	}

	@GetMapping
	public Iterable<Institution> retrieveInstitutions() {
		return institutionService.getInstitutions();
	}
}
