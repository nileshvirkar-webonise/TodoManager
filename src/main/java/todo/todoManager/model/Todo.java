package todo.todoManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jasypt.hibernate4.type.EncryptedStringType;

import todo.todoManager.Annotations.Encrypted;

@Entity
@TypeDef(name = "encryptedString", typeClass = EncryptedStringType.class, parameters = { @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor") })
public class Todo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	String title;
	
	@Encrypted
	@Type(type = "text")
	String text;
	
	public Todo() {
		super();
	}

	public Todo(int id, String title, String text) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", title=" + title + ", text=" + text + "]";
	}
	
}
