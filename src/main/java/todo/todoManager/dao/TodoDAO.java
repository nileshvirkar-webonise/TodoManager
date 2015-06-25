package todo.todoManager.dao;

import java.io.UnsupportedEncodingException;
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
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import todo.todoManager.cryptography.StringEncryptor;
import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

import com.mysql.jdbc.Connection;

public class TodoDAO {
	final static Logger logger = Logger.getLogger(TodoDAO.class);
    ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    HibernateUtil hu = (HibernateUtil) context.getBean("hibernateUtil");
    DataSource ds = (DataSource) context.getBean("dataSourceForJDBC");
    
	public void insertTodo(Todo todo)
	{   
        SessionFactory sessionFactory = hu.getSessionFactory();
        Todo todo1 = new Todo(1, "title1", "text1");
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(todo1);
        tx.commit();
        session.close();
	}
	
	public List<Todo> getTodos()
	{
        SessionFactory sessionFactory = hu.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteriaForAllTodos = session.createCriteria(Todo.class);
        List<Todo> allTodos = criteriaForAllTodos.list();
        session.close();
        return allTodos;
	}
	
	public Todo getTodoForTitle(String title)
	{
	    Todo decryptedTodo = new Todo();
	    try {
	        Connection connection = (Connection) DataSourceUtils.getConnection(ds);
	        String query = "select t.text from Todo as t where t.title = ?";
	    	java.sql.PreparedStatement prepStmt = connection.prepareStatement(query);
	    	prepStmt.setString(1, title);
	    	ResultSet resultSet = prepStmt.executeQuery();
	        
	        while (resultSet.next()) {
	        
	        logger.debug("Encrypted string is " + resultSet.getString("text"));
	       
			String decryptedText;
			
				decryptedText = StringEncryptor.getInstance().getDecryptedString(resultSet.getString("text"));
				decryptedTodo.setText(decryptedText);
				decryptedTodo.setTitle(resultSet.getString("title"));
			logger.debug("Decrypted string is " + decryptedText);
			connection.close();
			} 
	    }catch (IllegalBlockSizeException | UnsupportedEncodingException | BadPaddingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    }
	    return decryptedTodo;
	}
}
