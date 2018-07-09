package com.gueka.mutant.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.gueka.mutant.domain.DNA;
import com.gueka.mutant.repository.DNARepository;
import com.gueka.mutant.request.DNARequest;
import com.gueka.mutant.service.DNAService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

public class MutantHandler {
	
	@Autowired
	private DNARepository repository;
	
	@Autowired
	private DNAService service;
	
	private static final Logger log = Loggers.getLogger(MutantHandler.class);

	
	public Mono<ServerResponse> isMutant(ServerRequest req) { 
		
		Mono<Boolean> response = service.isMutant(req.bodyToMono(DNARequest.class)).log(log);
		Mono<ServerResponse> sr = response.flatMap( (isMutant) -> isMutant ? 
				ServerResponse.ok().build() : ServerResponse.status(HttpStatus.BAD_GATEWAY).build()).log(log);
		response.flatMap( (isMutant) ->  
				{
					log.debug("Saving dna");
					DNA document = new DNA();
					document.setDna(new String[]{"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"});
					document.setIsMutant(isMutant);
					return repository.save(document);
				}).log(log);
		log.debug("Finish processing dna");
		return sr;
    }
	
	public Flux<DNA> getAll(ServerRequest req) {
        return repository.findAll();
    }
}
