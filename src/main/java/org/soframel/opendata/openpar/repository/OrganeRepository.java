package org.soframel.opendata.openpar.repository;

import org.soframel.opendata.openpar.parsers.acteurs.Organe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganeRepository extends MongoRepository<Organe, String> {

}
