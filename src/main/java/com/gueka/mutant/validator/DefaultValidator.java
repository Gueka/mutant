package com.gueka.mutant.validator;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultValidator extends AbstractValidator {
	
	@Override
	protected Boolean isMutant(String[] dna) {
		return false;
	}
	
	@Override
	public Boolean processDNA(String[] dna) {
		return isMutant(dna);
	};

}
