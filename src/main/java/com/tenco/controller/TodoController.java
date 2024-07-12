package com.tenco.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/todo/*")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private TodoDAO todoDAO;
	
    public TodoController() {
    	todoDAO = new TodoDAOImpl();
    }
    
    // http://localhost:8080/mvc/todo/todoForm (권장 x )
    // http://localhost:8080/mvc/todo/formxxx
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		HttpSession session = request.getSession();
	 	UserDTO principal = (UserDTO)session.getAttribute("principal");
	 	
	 	// 인증 검사 
	 	if(principal == null) {
	 		// 로그인을 안한 상태
	 		response.sendRedirect("/mvc/user/signIn?message=invalid");
	 		return;
	 	}
		
		switch (action) {
		case "/todoForm":
			todoFormPage(request, response);
			break;
			
		case "/list":
			todoListPage(request, response, principal.getId());
			break;
		case "/detail":
			todoDetailPage(request, response, principal.getId());
			break;
		case "/delete":
			deleteTodo(request, response, principal.getId());
			break;
		
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
	}
	


	/**
	 * todo 삭제 기능
	 * @param request
	 * @param response
	 * @param principalId
	 * @throws IOException
	 */
	private void deleteTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		try {
			int todoId = Integer.parseInt(request.getParameter("id"));
			todoDAO.deleteTodo(todoId, principalId);
			
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/todo/list?error=invalid");
		}
		
		response.sendRedirect(request.getContextPath() + "/todo/list");
		
	}

	/**
	 * 상세보기 화면
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	// http://localhost:8080/mvc/todo/detail?id=2
	private void todoDetailPage(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		
		try {
			int todoId = Integer.parseInt(request.getParameter("id"));
			TodoDTO dto = todoDAO.getTodoById(todoId);
			if(dto.getUserId() == principalId) {
				
				//상세보기 화면으로 이동 처리
				request.setAttribute("todo", dto);
				request.getRequestDispatcher("/WEB-INF/views/todoDetail.jsp").forward(request, response);
			} else {
				//권한 없음 잘못된 접근	
				response.setContentType("text/html charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script> alert('권한이 없습니다'); history.back(); </script>");
			}
			
			
			
		} catch (Exception e) {
			response.sendRedirect(request.getContextPath() + "/todo/list?error=inbalid");
		}
		
		
		
	}

	// 리스트 목록 페이지 이동
	// http://localhot:8080/mvc/todo/list
	/**
	 * t사용자별 todo 리스트 화면 이동
	 * @param request
	 * @param response
	 * @param principal
	 * @throws IOException
	 * @throws ServletException
	 */
	private void todoListPage(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException, ServletException {
		
		
		List<TodoDTO> list = todoDAO.getTodosByUserId(principalId);
		request.setAttribute("list", list);
		// 데이터를 담아서 던질 예정(DB 조회)
		
		
		// todoList.jsp 페이지로 내부에서 이동처리
		request.getRequestDispatcher("/WEB-INF/views/todoList.jsp").forward(request, response);
	}

	/**
	 * todo 작성 페이지 이동
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void todoFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 로그인한 사용자만 접근을 허용하도록 설계
	 	request.getRequestDispatcher("/WEB-INF/views/todoForm.jsp").forward(request, response);	
	 	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 수정 예정 - 인증검사 추후 추가 처리 
		HttpSession session = request.getSession();
		UserDTO principal =  (UserDTO)session.getAttribute("principal");
		if(principal == null) {
			response.sendRedirect(request.getContextPath() + "/user/signIn?error=invalid");
			return;
			
		}
		
		String action = request.getPathInfo();
		switch (action) {
		case "/add":
			addTodo(request, response, principal.getId());
			break;
		case "/update":
			updateTodo(request, response, principal.getId());
			break;
				
		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}
		
	}
	
	/**
	 * todo 수정기능
	 * @param request
	 * @param response
	 * @param principalId - 세션 ID 값
	 * @throws IOException
	 */
	private void updateTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
        try {
            int todoId = Integer.parseInt( request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String dueDate = request.getParameter("dueDate");
            System.out.println("3333333333333" + request.getParameter("completed"));
            boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
            System.out.println("completed : " + completed);
            TodoDTO dto = TodoDTO.builder()
            		.id(todoId)
            		.userId(principalId)
            		.title(title)
            		.description(description)
            		.dueDate(dueDate)
                    .completed(String.valueOf(completed)).build();

            todoDAO.updateTodo(dto, principalId);
            System.out.println("todoId : " + dto + todoId);
        } catch (Exception e) {
             response.setContentType("text/html; charset=UTF-8");
             PrintWriter out = response.getWriter();
             out.println("<script> alert('잘못된 요청입니다'); history.back();  </script>");
        }
        // 리스트화면 재사용 요청
        response.sendRedirect(request.getContextPath() + "/todo/list");
    }

	/**
	 * 세션별 사용자 todo 등록
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void addTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String dueDate = request.getParameter("dueDate");
	
		// checkBox는 여러개 선택 가능한 태그 : String[] 배열로 선언 했음
		// 이번에 checkBox 하나만 사용 중 
		// 체크박스가 선택되지 않았으면 null을 반환하고 체크가 되어 있다면 "on" 으로 넘어 온다.
		boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
		
		TodoDTO dto = TodoDTO.builder()
				.userId(principalId)
				.title(title)
				.description(description)
				.dueDate(dueDate)
				.completed(String.valueOf(completed))
				.build();
		
		todoDAO.addTodo(dto, principalId);
		
		response.sendRedirect(request.getContextPath() + "/todo/list");
	}

}
