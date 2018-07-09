package com.gueka.mutant.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HorizontalValidator extends AbstractValidator {

	@Value("${mutant.validator.pattern}")
	private String regex;
	
	private Pattern pattern;
	
	@Override
	protected Boolean isMutant(String[] dna) {
		Matcher matcher = getPattern().matcher(String.join(" ", dna));
		return matcher.find();
	}

	@Override
	public Boolean processDNA(String[] dna) {
		Boolean result = isMutant(dna);
		if(!result) {
			return getNext().processDNA(dna);
		}
		return true;
	}

	public Pattern getPattern() {
		if(pattern == null) {
			pattern = Pattern.compile(regex);
		}
		return pattern;
	}

}
