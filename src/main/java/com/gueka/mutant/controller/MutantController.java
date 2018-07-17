package com.gueka.mutant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gueka.mutant.domain.DNA;
import com.gueka.mutant.domain.DNAStats;
import com.gueka.mutant.repository.DNARepository;
import com.gueka.mutant.request.DNARequest;
import com.gueka.mutant.service.DNAService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MutantController {

	@Autowired
	private DNARepository repository;
	
	@Autowired
	private DNAService service;
    
    @GetMapping("/stats")
    public Flux<DNAStats> getStats() {
    	DNAStats stats = new DNAStats();
    	// TODO: instead of retrieving data test for count if we can perform better.
        return repository.findAll().map(dna -> {
        	if(dna.getIsMutant()) {
        		stats.increaseMutantCount();
        	}else {
        		stats.increaseHumanCount();
        	}
        	return new DNAStats(stats.getMutantCount(),stats.getHumanCount(),stats.getRatio());
        }).cache();
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/mutant")
    public Mono<ResponseEntity<Void>> isMutant(@RequestBody Mono<DNARequest> request) {
    	return request.map(DNARequest -> service.isMutant(DNARequest.getDna()) )
    		.cast(DNA.class)
    		.flatMap(dna -> this.repository.save(dna) )
    		.flatMap(dna -> {
    			if(dna.getIsMutant()) {
    				return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
    			}else {
    				return Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build());
    			}
    		});
    }
    
}
