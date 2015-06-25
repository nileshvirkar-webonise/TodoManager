package todo.todoManager.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import todo.todoManager.hibernateUtil.HibernateUtil;
import todo.todoManager.model.Todo;

public class TodoDAO {

    ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    HibernateUtil hu = (HibernateUtil) context.getBean("hibernateUtil");
    
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
        SessionFactory sessionFactory = hu.getSessionFactory();
        Session session = sessionFactory.openSession();
		Criteria todoGetCriteria = session.createCriteria(Todo.class);
		todoGetCriteria.add(Restrictions.eq("title", title));
		Todo todo = (Todo) todoGetCriteria.uniqueResult();
		session.close();
		return todo;
	}
}
