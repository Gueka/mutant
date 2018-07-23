package com.gueka.mutant.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gueka.mutant.validator.AbstractValidator;
import com.gueka.mutant.validator.DefaultValidator;
import com.gueka.mutant.validator.HorizontalValidator;
import com.gueka.mutant.validator.ObliqueValidator;
import com.gueka.mutant.validator.StructureValidator;
import com.gueka.mutant.validator.VerticalValidator;

@Configuration
public class Config {
	
	@Bean("obliqueValidator")
	public AbstractValidator getObliqueValidation() {
		AbstractValidator validator = new ObliqueValidator();
		validator.setNext(new DefaultValidator());
		return validator;
	}
	@Bean("verticalValidator")
	public AbstractValidator getVerticalValidation(@Qualifier("obliqueValidator") AbstractValidator next) {
		AbstractValidator validator = new VerticalValidator();
		validator.setNext(next);
		return validator;
	}
	@Bean("horizontalValidator")
	public AbstractValidator getHorizontalValidation(@Qualifier("verticalValidator") AbstractValidator next) {
		AbstractValidator validator = new HorizontalValidator();
		validator.setNext(next);
		return validator;
	}
	@Bean("structureValidator")
	public AbstractValidator getStructureValidation(@Qualifier("horizontalValidator") AbstractValidator next) {
		StructureValidator validator = new StructureValidator();
		validator.setNext(next);
		return validator;
	}
	
}
