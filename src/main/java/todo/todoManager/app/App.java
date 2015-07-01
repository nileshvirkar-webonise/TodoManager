package todo.todoManager.app;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.log4j.Logger;

import todo.todoManager.dao.TodoDAO;
import todo.todoManager.model.Todo;

public class App {
    final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {

        Todo todo1 = new Todo(1, "title1", "text1");
        TodoDAO todoDao = new TodoDAO();
        todoDao.insertTodo(todo1);

        Todo todo = null;
        try {
            todo = todoDao.getTodoForTitle(1);
        } catch (SecurityException | IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info(todo.toString());
    }
}
