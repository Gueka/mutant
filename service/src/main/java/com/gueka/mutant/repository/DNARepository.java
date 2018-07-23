package com.gueka.mutant.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.gueka.mutant.domain.DNA;

@Repository
public interface DNARepository extends ReactiveMongoRepository<DNA, String> {

}
