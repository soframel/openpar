package org.soframel.opendata.openpar.repository;

import org.soframel.opendata.openpar.parsers.scrutins.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository<Vote, String> {

}
