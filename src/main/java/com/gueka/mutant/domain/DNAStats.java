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

	public void updateRatio() {
		Double mutantDouble = getMutantCount().doubleValue();
		Double humanDouble = getHumanCount().doubleValue();
		Double result = (mutantDouble - humanDouble) / (mutantDouble + humanDouble);
		this.ratio = Double.parseDouble( new DecimalFormat("#.##").format(result) );
	}

	public void setMutantCount(Integer mutantCount) {
		this.mutantCount = mutantCount;
	}

	public void setHumanCount(Integer humanCount) {
		this.humanCount = humanCount;
	}
	
}
