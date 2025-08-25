package tp.eni_store.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import tp.eni_store.bo.Person;

public interface PersonMongoRepository extends MongoRepository<Person, String> {
    Person findByEmailAndPassword(String email, String password);
}
