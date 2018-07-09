package com.gueka.mutant.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gueka.mutant.domain.DNA;
import com.gueka.mutant.request.DNARequest;
import com.gueka.mutant.validator.AbstractValidator;

import reactor.core.publisher.Mono;

@Service
public class DNAService {
	
	@Autowired
	@Qualifier("structureValidator")
	private AbstractValidator validator;

	public Mono<Boolean> isMutant(Mono<DNARequest> request) {
		return request.map(DNARequest -> validator.processDNA(DNARequest.getDna()) );
	}
	
	public DNA isMutant(String[] dna) {
		
		DNA document = new DNA();
		document.setDna(dna);
		document.setCreatedDate(new Date());
		document.setIsMutant(validator.processDNA(dna));
		
		return document;
	}
}
