package org.soframel.opendata.openpar.repository;

import org.soframel.opendata.openpar.parsers.acteurs.Acteur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActeurRepository extends MongoRepository<Acteur, String> {

}
