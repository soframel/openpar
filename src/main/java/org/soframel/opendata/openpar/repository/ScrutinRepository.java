package org.soframel.opendata.openpar.repository;

import org.soframel.opendata.openpar.domain.frpar.Scrutin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrutinRepository extends MongoRepository<Scrutin, String> {

}
