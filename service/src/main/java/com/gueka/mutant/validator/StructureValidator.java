package com.gueka.mutant.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;

public class StructureValidator extends AbstractValidator {

	@Value("${mutant.validator.structure.dna.valid.letters.regex}")
	private String regex;
	
	private Pattern pattern;

	@Override
	public Boolean processDNA(String[] dna) {
		// WARNING: This is the only validation that response that can be
		// a good sample test for a dna
		Boolean result = isValidDNA(dna);
		if(result) {
			return getNext().processDNA(dna);
		}
		return false;
	}

	private Boolean isValidDNA(String[] dna) {
		Matcher matcher = getPattern().matcher(String.join(" ", dna));
		return matcher.matches();
	}

	@Override
	protected Boolean isMutant(String[] dna) {
		return false;
	}
	
	public Pattern getPattern() {
		if(pattern == null) {
			pattern = Pattern.compile(regex);
		}
		return pattern;
	}

}
