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
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from User").executeUpdate();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.getCurrentSession();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
    }

    @Override
    public User getUserByCar(String model, int series) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u FROM User u JOIN u.car c WHERE c.model = :model AND c.series = :series";

        List<User> users = session.createQuery(hql, User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .getResultList();

        return users.isEmpty() ? null : users.get(0);
    }
}

