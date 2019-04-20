package ru.spoddubnyak.testTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spoddubnyak.testTask.domain.Person;


@Repository
@Transactional
public interface PersonRepo extends JpaRepository<Person, Integer> {

}
