package todo.TodoManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

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
		
		Todo todo2 = null;
		try {
			todo2 = todoDao.getTodoForTitle("title1");
		} catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals("text1", todo2.getText());
	}
}
