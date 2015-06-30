package todo.todoManager.dao;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import todo.todoManager.cryptography.BeanDecryptor;
import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

import com.mysql.jdbc.Connection;

public class TodoDAO {
    final static Logger logger = Logger.getLogger(TodoDAO.class);
    private ApplicationContext context = null;
    private HibernateUtil hibernateUtil = null;
    private DataSource ds = null;
    private JdbcTemplate jdbcTemplate = null;

    public TodoDAO() {
        super();
        context = new ClassPathXmlApplicationContext("spring.xml");
        hibernateUtil = (HibernateUtil) context.getBean("hibernateUtil");
        ds = (DataSource) context.getBean("dataSourceForJDBC");
        jdbcTemplate = new JdbcTemplate(ds);
    }

    public void insertTodo(Todo todo) {
        Todo encryptedTodo = (Todo) BeanDecryptor.encryptBean(todo, Todo.class);
        String SQL = "insert into Todo (title, text) values (?, ?)";
        jdbcTemplate.update(SQL, encryptedTodo.getTitle(), encryptedTodo.getText());
    }

    public List<Todo> getTodos() {
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteriaForAllTodos = session.createCriteria(Todo.class);
        List<Todo> allTodos = criteriaForAllTodos.list();
        session.close();
        return allTodos;
    }

    public Todo getTodoForTitle(String title) throws SecurityException, IllegalArgumentException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Todo encryptedTodo = new Todo();
        Todo decriptedTodo = new Todo();

        try {
            Connection connection = (Connection) DataSourceUtils.getConnection(ds);
            String query = "select * from Todo as t where t.title = ?";
            java.sql.PreparedStatement prepStmt = connection.prepareStatement(query);
            prepStmt.setString(1, title);
            ResultSet resultSet = prepStmt.executeQuery();

            while ( resultSet.next() ) {
                encryptedTodo.setId(resultSet.getInt("id"));
                encryptedTodo.setTitle(resultSet.getString("title"));
                encryptedTodo.setText(resultSet.getString("text"));

                decriptedTodo = (Todo) BeanDecryptor.decryptBean(encryptedTodo, Todo.class);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return decriptedTodo;
    }
}
