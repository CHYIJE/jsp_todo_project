package com.tenco.controller;

import java.io.IOException;
import java.util.List;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test/*")
public class TestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	private TodoDAO todoDAO;

	public TestController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		//userDAO = new UserDAOImpl();
		todoDAO = new TodoDAOImpl();
	}
	
	// http://localhost:8080/mvc/test/t1
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		
		
		//  아이디
//		List<TodoDTO> list = todoDAO.getTodosByUserId(1);
//		System.out.println(list.toString());
		
		// all
//		todoDAO.getAllTodos();
		
		
		// delet
//		todoDAO.deleteTodo(6, 11);
	
		
		
		// add 
//		TodoDTO dto = TodoDTO.builder()
//				.title("콘센트 사기")
//				.description("뭘 사야하는지 심사숙고 중")
//				.dueDate("2024-07-11")
//				.completed(null)
//				.build();
//		todoDAO.addTodo(dto, 11);
		
		//수정
		TodoDTO dto = TodoDTO.builder()
				.id(7)
				.title("집에가고싶어")
				.dueDate("9999-01-01")
				.description("수수수수퍼노바3시간보게")
				.completed(null)
				.userId(11)
				.build();
		todoDAO.updateTodo(dto, 1);
		

//		
//		
//		TodoDTO todoDTO = todoDAO.getTodoById(1);
		// System.out.println(todoDTO.toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
	}

}
