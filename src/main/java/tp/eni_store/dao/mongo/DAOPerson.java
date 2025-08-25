package tp.eni_store.dao.mongo;

import org.springframework.stereotype.Component;
import tp.eni_store.bo.Person;
import tp.eni_store.dao.IDAOPerson;

@Component
public class DAOPerson implements IDAOPerson {
    private final PersonMongoRepository personMongoRepository;

    public DAOPerson(PersonMongoRepository personMongoRepository) {
        this.personMongoRepository = personMongoRepository;
    }

    @Override
    public Person selectPersonByLogin(String email, String password) {
        return personMongoRepository.findByEmailAndPassword(email, password);
    }
}
