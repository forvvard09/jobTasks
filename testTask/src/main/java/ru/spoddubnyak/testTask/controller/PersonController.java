package ru.spoddubnyak.testTask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spoddubnyak.testTask.domain.Person;
import ru.spoddubnyak.testTask.exception.AlreadyExistException;
import ru.spoddubnyak.testTask.service.PersonService;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/persons", produces = "application/json")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(final PersonService service) {
        this.service = service;
    }

    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;


    @PostMapping(consumes = "application/json")
    public ResponseEntity<Person> save(@RequestBody final Person person) {
        final Integer id = person.getId();
        if (id != null) {
            if (service.isExists(id)) {
                throw new AlreadyExistException(id);
            }
        }
        return new ResponseEntity<>(service.save(person), HttpStatus.CREATED);
    }


    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Person> update(@PathVariable("id") final Integer personId, @RequestBody final Person person) {
        if (personId == null) {
            throw new IllegalArgumentException("ID must not be null");
        } else if (service.isExists(personId)) {
            return new ResponseEntity<>(service.save(person), HttpStatus.OK);
        } else {
            throw new AlreadyExistException(personId);
        }
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable("id") final Integer personId) {
        return service.get(personId);
    }

    @GetMapping
    public List<Person> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") final Integer personId) {
        service.delete(personId);
    }

    // обработать
    @PutMapping(path = "/working", consumes = "application/json")
    public ResponseEntity<List<Person>> process(@RequestBody final Integer[] mass) {
        List<Person> list = new ArrayList<>();
        synchronized (mass) {
            for (int index : mass) {
                taskExecutor.execute(runTask(list, index));
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private Runnable runTask(List<Person> list, int index) {
        return () -> {
            list.add(service.processData(index));
        };
    }
}