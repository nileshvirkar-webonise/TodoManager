package todo.TodoManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.mysql.jdbc.Connection;

import todo.todoManager.dao.TodoDAO;
import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

public class TodoTest {

    private ApplicationContext context;

    @Test
    public void insertedDataShouldSameWithFecthedData() {
        // Assert.assertEquals("text", "text");

        Todo todo1 = new Todo(1, "title1", "text1");
        TodoDAO todoDao = new TodoDAO();
        todoDao.insertTodo(todo1);

        Todo todo2 = null;
        try {
            todo2 = todoDao.getTodoForTitle("title1");
        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertEquals("text1", todo2.getText());
    }

    @Test
    public void todosShouldNotNull() {
        TodoDAO todoDao = new TodoDAO();
        List<Todo> todos = todoDao.getTodos();
        Assert.assertNotNull(todos);
    }

    @Test
    public void testJdbcConnection() {
        context = new ClassPathXmlApplicationContext("spring.xml");
        DataSource ds = (DataSource) context.getBean("dataSourceForJDBC");
        Connection connection = (Connection) DataSourceUtils.getConnection(ds);
        Assert.assertNotNull(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
}
