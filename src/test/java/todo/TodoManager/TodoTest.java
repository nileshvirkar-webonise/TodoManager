package todo.TodoManager;

import org.junit.Assert;
import org.junit.Test;

import todo.todoManager.dao.TodoDAO;
import todo.todoManager.model.Todo;

public class TodoTest{
    
	@Test
	public void insertedDataShouldSameWithFecthedData()
	{
		//Assert.assertEquals("text", "text");

		Todo todo1 = new Todo(1, "title1", "text1");
		TodoDAO todoDao = new TodoDAO();
		todoDao.insertTodo(todo1);
		
		Todo todo2 = todoDao.getTodoForTitle("title1");
		Assert.assertEquals(todo1.getText(), todo2.getText());
	}
}
