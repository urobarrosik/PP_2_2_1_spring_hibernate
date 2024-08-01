package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        CarService carService = context.getBean(CarService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        userService.add(new User("UserWithCar1", "LastnameWithCar1", "userWithCar1@mail.ru",
                context.getBean(Car.class)));
        userService.add(new User("UserWithCar2", "LastnameWithCar2", "userWithCar2@mail.ru",
                context.getBean(Car.class)));

        String model = "lada";
        int series = 123;
        userService.add(new User("UserWithCar3", "LastnameWithCar3", "userWithCar3@mail.ru",
                new Car(model, series)));

        userService.add(new User("UserWithCar4", "LastnameWithCar4", "userWithCar4@mail.ru",
                new Car(model, series)));


        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println(user.toString());
            System.out.println();
        }

        System.out.println(userService.getUserByCar(model, series) != null ?
                userService.getUserByCar(model, series).toString() : "Машиной (модель "
                + model + " и серии " + series + ") никто не владеет");

        userService.dropUsersTable();

        carService.dropCarsTable();

        context.close();
    }
}
