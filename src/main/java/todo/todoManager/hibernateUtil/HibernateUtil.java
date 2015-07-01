package todo.todoManager.hibernateUtil;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateUtil {
	@Autowired
	private SessionFactory sessionFactory;

	private HibernateUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
