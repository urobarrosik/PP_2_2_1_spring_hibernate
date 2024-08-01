package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> listUsers();

    void cleanUsersTable();

    void dropUsersTable();

    User getUserByCar(String model, int series);
}

