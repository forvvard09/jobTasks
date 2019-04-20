package ru.spoddubnyak.testTask.repo;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import ru.spoddubnyak.testTask.domain.Person;

import java.io.Serializable;

public class IdGenerator extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if (obj == null) throw new HibernateException(new NullPointerException());

        if ((((Person) obj).getId()) == null) {
            Serializable id = super.generate(session, obj);
            return id;
        } else {
            return ((Person) obj).getId();

        }
    }
}
