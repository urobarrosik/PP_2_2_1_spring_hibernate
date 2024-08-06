package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private final SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().
                createQuery("SELECT u FROM User u LEFT JOIN FETCH u.car");
        return query.getResultList();
    }

    //Метод для очищения нужен был для тестов(не нравилось что ID постоянно менялся
    //(из-за авто-инкремента он увеличивался)). Но очищение таблицы не решало проблему, так что создал метод для
    //удаления таблицы, а удалить метод для очищения забыл...

    //Методы для удаления таблиц нужны, что бы во время тестов новые добавления юзеров ложились в 1вый, 2ой и тд ID.
    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
    }

    @Override
    public User getUserByCar(String model, int series) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series";

        List<User> users = session.createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getResultList();

        return users.isEmpty() ? null : users.get(0);
    }
}

