package com.gueka.mutant.validator;

public abstract class AbstractValidator {
	
	protected AbstractValidator next;
	public AbstractValidator getNext() {
		return next;
	};
	public void setNext(AbstractValidator next) {
        this.next = next;
    }

	public abstract Boolean processDNA(String[] dna);
	protected abstract Boolean isMutant(String[] dna);
	
}
