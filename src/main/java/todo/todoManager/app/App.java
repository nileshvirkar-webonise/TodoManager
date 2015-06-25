package todo.todoManager.app;

import org.apache.log4j.Logger;

import todo.todoManager.dao.TodoDAO;
import todo.todoManager.model.Todo;

public class App {
	final static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) {

		Todo todo1 = new Todo(1, "title1", "text1");
		TodoDAO todoDao = new TodoDAO();
		todoDao.insertTodo(todo1);

		Todo todo = todoDao.getTodoForTitle("title");
			logger.info(todo.toString());
		
	}
}
