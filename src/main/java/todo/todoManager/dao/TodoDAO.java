package todo.todoManager.dao;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import todo.todoManager.cryptography.BeanDecryptor;
import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

public class TodoDAO {
    final static Logger logger = Logger.getLogger(TodoDAO.class);
    private ApplicationContext context = null;
    private HibernateUtil hibernateUtil = null;

    public TodoDAO() {
        super();
        context = new ClassPathXmlApplicationContext("spring.xml");
        hibernateUtil = (HibernateUtil) context.getBean("hibernateUtil");
    }

    public void insertTodo(Todo todo) {
        Todo encryptedTodo = (Todo) BeanDecryptor.encryptBean(todo, Todo.class);
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(encryptedTodo);
        tx.commit();
        session.close();
    }

    public List<Todo> getTodos() throws SecurityException, IllegalArgumentException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        List<Todo> decryptedTodos = new ArrayList<Todo>();
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteriaForAllTodos = session.createCriteria(Todo.class);
        List<Todo> allTodos = criteriaForAllTodos.list();
        for ( Todo todo : allTodos ) {
            decryptedTodos.add((Todo) BeanDecryptor.decryptBean(todo, Todo.class));
        }
        session.close();
        return allTodos;
    }

    public Todo getTodoForTitle(int id) throws SecurityException, IllegalArgumentException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteriaForTodo = session.createCriteria(Todo.class);
        criteriaForTodo.add(Restrictions.eq("id", id));       
        Todo encryptedTodo = (Todo) criteriaForTodo.uniqueResult();
        return (Todo) BeanDecryptor.decryptBean(encryptedTodo, Todo.class);
    }
}
