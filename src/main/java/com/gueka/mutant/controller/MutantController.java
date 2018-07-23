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
    public Mono<DNAStats> getStats() {
    	DNAStats result = new DNAStats();    	
    	return repository.findAll()
        		.map(dna -> {
		        	DNAStats stats = new DNAStats();
		        	if(dna.getIsMutant()) {
		        		stats.setMutantCount(1);
		        	}else {
		        		stats.setHumanCount(1);
		        	}
		        	return stats;
		        	})
	        	.reduce(result, (s1, s2) -> {
	        		result.setMutantCount(s1.getMutantCount() + s2.getMutantCount());
	        		result.setHumanCount(s1.getHumanCount() + s2.getHumanCount());
	        		result.updateRatio();
	        		return result;
	        	});
    	
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
