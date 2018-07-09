package com.gueka.mutant.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerticalValidator extends AbstractValidator {

	@Value("${mutant.validator.pattern}")
	private String regex;
	
	private Pattern pattern;
	
	@Override
	protected Boolean isMutant(String[] dna) {
		int length = dna[0].length();		
		List<String> columns = transposeToColumns(String.join("", dna), length);
		return columns.stream().filter(getPattern().asPredicate()).findAny().isPresent();
	}

	private List<String> transposeToColumns(String join, int rowLength) {
		int length = join.length() / rowLength;
		List<String> columns = new ArrayList<>();
		//row
		for(int r = 0; r < rowLength; r++) {
			String column = "";
			//col
			for(int c = 0; c < length; c++) {
				int charIndex = (c * rowLength) + r;
				column += String.valueOf( join.charAt(charIndex) );
			}
			columns.add(column);
		}
		return columns;
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
