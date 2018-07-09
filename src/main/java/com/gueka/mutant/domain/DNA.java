package com.gueka.mutant.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Document(collection = "dna")
@NoArgsConstructor
public class DNA {
	
	@Id
	private String id;
	
	@NotBlank
    private String[] dna;
	
    @CreatedDate
    private Date createdDate;

    @NotNull
    @Indexed
    private Boolean isMutant;

	public Boolean getIsMutant() {
		return isMutant;
	}
}
