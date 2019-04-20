package ru.spoddubnyak.testTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spoddubnyak.testTask.exception.NotFoundException;
import ru.spoddubnyak.testTask.domain.Person;
import ru.spoddubnyak.testTask.repo.PersonRepo;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepo repository;

    @Autowired
    public PersonService(final PersonRepo repository) {
        this.repository = repository;
    }

    public Person get(final Integer personId) {
        return repository.findById(personId).orElseThrow(() -> new NotFoundException(personId));
    }

    public Person save(final Person person) {
        return repository.save(person);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public void delete(final Integer personId) {
        repository.deleteById(personId);
    }

    public boolean isExists(final Integer personId) {
        return repository.findById(personId).isPresent();
    }


    @Transactional
    public Person processData(final Integer id) {
        Person person = get(id);
        person.setComment(String.format("%s %s", "Обработано", LocalTime.now()));
        person.setUpdateDate(LocalDateTime.now());
        return person;
    }

}
