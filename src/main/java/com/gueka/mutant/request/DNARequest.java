package com.gueka.mutant.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DNARequest {
	
	// TODO: add validation
	private String[] dna;

	public DNARequest() { }
}
