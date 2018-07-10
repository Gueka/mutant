package com.gueka.mutant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gueka.mutant.request.DNARequest;
import com.gueka.mutant.service.DNAService;
import com.gueka.mutant.validator.DefaultValidator;
import com.gueka.mutant.validator.HorizontalValidator;
import com.gueka.mutant.validator.ObliqueValidator;
import com.gueka.mutant.validator.StructureValidator;
import com.gueka.mutant.validator.VerticalValidator;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidationTest {

	@Autowired
	private DNAService service;
	
	@Test
	public void validDNA() {
		String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
		DNARequest request = new DNARequest();
		request.setDna(dna);
		
		Mono<Boolean> isMutant = service.isMutant(Mono.just(request));
		
		StepVerifier.create(isMutant).expectNext(true);
	}
	
	@Test
	public void invalidDNA() {
		String[] dna = {"WWWWWW","WWWWWW","WWWWWW","WWWWWW","WWWWWW","WWWWWW"};
		DNARequest request = new DNARequest();
		request.setDna(dna);
		
		Mono<Boolean> isMutant = service.isMutant(Mono.just(request));
		
		StepVerifier.create(isMutant).expectNext(false);
	}
	
	@Autowired
	HorizontalValidator horizontal;
	@Test
	public void validHorizontal() throws Exception {
		horizontal.setNext(new DefaultValidator());
		assertTrue(horizontal.processDNA(new String[] {"WWAAAA","WWWWWW","WWWWWW","WWWWWW","WWWWWW","WWWWWW"}));
		assertFalse(horizontal.processDNA(new String[] {"ABCDEF","FABCDE","EFABCD","DEFABC","CDEFAB","BCDEFA"}));
	}
	
	@Autowired
	VerticalValidator vertical;
	@Test
	public void validVertical() throws Exception {
		vertical.setNext(new DefaultValidator());
		assertTrue(vertical.processDNA(new String[] {"ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF"}));
		assertFalse(vertical.processDNA(new String[] {"AAAAAA","BBBBBB","CCCCCC","DDDDDD","EEEEEE","FFFFFF"}));
	}
	
	@Autowired
	ObliqueValidator oblique;
	@Test
	public void validOblique() throws Exception {
		oblique.setNext(new DefaultValidator());
		assertTrue(oblique.processDNA(new String[] {"ABCDEF","FABCDE","EFABCD","DEFABC","CDEFAB","BCDEFA"}));
		assertFalse(oblique.processDNA(new String[] {"WWAAAA","WWWWWW","WWWWWW","WWWWWW","WWWWWW","WWWWWW"}));
	}
	
	@Test
	public void validInvertOblique() throws Exception {
		oblique.setNext(new DefaultValidator());
		assertTrue(oblique.processDNA(new String[] {"FEDCBA","EDCBAF","DCBAFE","CBAFED","BAFEDC","AFEDCB"}));
	}
	
	@Test
	public void validDefault() {
		String[] dna = {"AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"};
		DefaultValidator validator = new DefaultValidator();
		assertFalse(validator.processDNA(dna));
	}
	
	@Autowired
	StructureValidator structure;
	@Test
	public void validStructure() throws Exception {
		structure.setNext(new DefaultValidator(){
			@Override
			protected Boolean isMutant(String[] dna) {
				return true;
			}
		});
		assertTrue(structure.processDNA(new String[] {"CATCAG","CATCAG","CATCAG","CATCAG","CATCAG","CATCAG"}));
		
		assertFalse(structure.processDNA(new String[] {"ACTGAC","ACTGAC","ACTGAC"}));
		assertFalse(structure.processDNA(new String[] {"EREACS","EREAC"}));
		assertFalse(structure.processDNA(new String[] {"CARGAS","CARGAS","CARGAS","CARGAS","CARGAS","CARGAS"}));
		assertFalse(structure.processDNA(new String[] {"CATCA","CATCAGT","CATCAG","CATCAG","CATACAG","CATCA"}));
	}

}
