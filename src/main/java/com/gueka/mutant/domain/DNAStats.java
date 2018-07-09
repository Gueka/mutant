package com.gueka.mutant.domain;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DNAStats {

	@JsonProperty("count_mutant_dna")
	private Integer mutantCount = 0;
	
	@JsonProperty("count_human_dna")
	private Integer humanCount = 0;
	
	private Double ratio;
	
	public void increaseMutantCount() {
		mutantCount++;
		updateRatio();
	}
	
	public void increaseHumanCount() {
		humanCount++;
		updateRatio();
	}

	private void updateRatio() {
		Double mutantDouble = mutantCount.doubleValue();
		Double humanDouble = humanCount.doubleValue();
		Double result = (mutantDouble - humanDouble) / (mutantDouble + humanDouble);
		ratio = Double.parseDouble( new DecimalFormat("#.##").format(result) );
	}
	
}
