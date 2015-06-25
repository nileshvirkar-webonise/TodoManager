package todo.todoManager.app;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import todo.todoManager.dao.TodoDAO;
import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {
		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("spring.xml");
		// HibernateUtil hu = (HibernateUtil) context.getBean("hibernateUtil");
		//
		// SessionFactory sessionFactory = hu.getSessionFactory();
		//
		// Todo todo1 = new Todo(1, "title1", "text1");
		// Session session = sessionFactory.openSession();
		// Transaction tx = session.beginTransaction();
		// session.save(todo1);
		// tx.commit();
		//
		// Criteria criteriaForAllTodos = session.createCriteria(Todo.class);
		// List<Todo> allTodos = criteriaForAllTodos.list();
		// Iterator<Todo> it = allTodos.iterator();
		// while (it.hasNext()) {
		// Todo todo = (Todo) it.next();
		// logger.info(todo.toString());
		// }
		// session.close();

		Todo todo1 = new Todo(1, "title1", "text1");
		TodoDAO todoDao = new TodoDAO();
		todoDao.insertTodo(todo1);

		List<Todo> allTodos = todoDao.getTodos();
		Iterator<Todo> it = allTodos.iterator();
		while (it.hasNext()) {
			Todo todo = (Todo) it.next();
			logger.info(todo.toString());
		}
	}
}
