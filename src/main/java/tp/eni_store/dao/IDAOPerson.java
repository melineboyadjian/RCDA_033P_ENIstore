package tp.eni_store.dao;

import tp.eni_store.bo.Person;

public interface IDAOPerson {
    Person selectPersonByLogin(String email, String password);
}
