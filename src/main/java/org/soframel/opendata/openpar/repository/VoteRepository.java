package org.soframel.opendata.openpar.repository;

import org.soframel.opendata.openpar.domain.frpar.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository<Vote, String> {

}
