package pl.camp.it;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class Main {
    static SessionFactory sessionFactory;
    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        saveUSer(new User(50, "login", "password"));
        saveUSer(new User(50, "janusz", "janusz"));
        saveUSer(new User(50, "zbyszek", "zbyszek"));
        //updateUser(new User(5, "janusz", "janusz"));
        //deleteUser(new User(3, "karol", "karol"));
        //persistUser(new User(0, "jan", "jan"));

        //List<User> users = getAllUsers();
        //System.out.println(users);
        //User user = getUserById(5);
        //System.out.println(user);

        Order order = new Order(0, new User(0, "wojtek", "wojtek"), 50.00);

        persistOrder(order);
        System.out.println(order);

        //Order orderFromDb = getOrderById(1);
        //System.out.println(orderFromDb);

    }

    public static void saveUSer(User user) {
        Session session = Main.sessionFactory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    public static void updateUser(User user) {
        Session session = Main.sessionFactory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    public static void deleteUser(User user) {
        Session session = Main.sessionFactory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    public static void persistUser(User user) {
        Session session = Main.sessionFactory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public static List<User> getAllUsers() {
        Session session = Main.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.User");
        List<User> users = query.getResultList();
        session.close();
        return users;
    }

    public static User getUserById(int id) {
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.User WHERE id = :id");
        query.setParameter("id", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {

        } finally {
            session.close();
        }
        return user;
    }

    public static void persistOrder(Order order) {
        Session session = sessionFactory.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(order);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    public static Order getOrderById(int id) {
        Session session = sessionFactory.openSession();
        Query<Order> query = session.createQuery("FROM pl.camp.it.Order WHERE id = :id");
        query.setParameter("id", id);
        Order order = query.getSingleResult();
        session.close();
        return order;
    }
}
