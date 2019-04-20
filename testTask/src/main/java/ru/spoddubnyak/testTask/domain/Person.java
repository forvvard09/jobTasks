package ru.spoddubnyak.testTask.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "person")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdOrGenerated")
    @GenericGenerator(name = "IdOrGenerated", strategy = "ru.spoddubnyak.testTask.repo.IdGenerator")
    private Integer id;

    @Column(name = "last_name", columnDefinition = "text")

    private String lastName;

    @Column(name = "first_name", columnDefinition = "text")

    private String firstName;

    @Column(name = "middle_name", columnDefinition = "text")

    private String middleName;

    @Column(name = "birth_date")

    private LocalDate birthDate;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
