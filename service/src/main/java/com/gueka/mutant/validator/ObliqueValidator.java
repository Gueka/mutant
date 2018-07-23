package com.gueka.mutant.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObliqueValidator extends AbstractValidator {

	@Value("${mutant.validator.oblique.size}")
	private Integer size;
	
	@Value("${mutant.validator.pattern}")
	private String regex;
	
	private Pattern pattern;
	
	@Override
	protected Boolean isMutant(String[] dna) {
		List<String> dnaList = Arrays.asList(dna);
		List<String> diagonals = getDiagonals(dnaList, size);
		if(diagonals.stream().filter(getPattern().asPredicate()).findAny().isPresent()) {
			return true;
		}
		List<String> invertDna = dnaList.stream().map(value -> new StringBuilder(value).reverse().toString() ).collect(Collectors.toList());
		List<String> invertDiagonals = getDiagonals(invertDna, size);
		return invertDiagonals.stream().filter(getPattern().asPredicate()).findAny().isPresent();
	}

	private List<String> getDiagonals(List<String> rows, Integer diagSize) {
		List<String> colDiag = new ArrayList<>();
		List<String> rowDiag = new ArrayList<>();
		Integer colMax = rows.get(0).length() - (diagSize - 1);
		for(int i = 0; i < colMax; i++) {
			colDiag.add(getDiagonal(i, 0, rows, rows.get(i).length()));
		}
		Integer rowMax = rows.size() - (diagSize - 1);
		for(int i = 0; i < rowMax; i++) {
			rowDiag.add(getDiagonal(0, i, rows, rows.size()));
		}
		colDiag.addAll(rowDiag);
		return colDiag;
	}

	private String getDiagonal(Integer colStart, Integer rowStart, List<String> rows, Integer rowSize) {
		StringBuilder diagonal = new StringBuilder();
		Integer columnSize = rows.size();
		Integer min = Math.min(columnSize - colStart, rowSize - rowStart);
		for(int i = 0; i < min;i++) {
			diagonal.append(rows.get(i + rowStart).charAt(i + colStart));
		}
		return diagonal.toString();
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
