package todo.TodoManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import todo.todoManager.dao.TodoDAO;
import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

public class TodoTest {

    private ApplicationContext context;

    @Test
    public void testHibernateSession() {
        context = new ClassPathXmlApplicationContext("spring.xml");
        HibernateUtil hibernateUtil = (HibernateUtil) context.getBean("hibernateUtil");
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        
        Assert.assertNotNull(sessionFactory);
        Session session = sessionFactory.openSession();
        Assert.assertNotNull(session);
        session.close();
    }
    
    @Test
    public void insertedDataShouldSameWithFecthedData() {
        Todo todo1 = new Todo(1, "title1", "text1");
        TodoDAO todoDao = new TodoDAO();
        todoDao.insertTodo(todo1);

        Todo todo2 = null;
        try {
            todo2 = todoDao.getTodoForTitle(1);
        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Assert.assertEquals("text1", todo2.getText());
    }

    @Test
    public void todosShouldNotNull() {
        TodoDAO todoDao = new TodoDAO();
        List<Todo> todos = null;
        try {
            todos = todoDao.getTodos();
        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertNotNull(todos);
    }
}
