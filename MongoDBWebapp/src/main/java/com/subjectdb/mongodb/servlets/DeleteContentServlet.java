package com.subjectdb.mongodb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.subjectdb.mongodb.dao.MongoDBContentDAO;
import com.subjectdb.mongodb.model.Content;

@WebServlet("/deleteContent")
public class DeleteContentServlet extends HttpServlet {

	private static final long serialVersionUID = 6798036766148281767L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for delete operation");
		}
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBContentDAO contentDAO = new MongoDBContentDAO(mongo);
		Content c = new Content();
		c.setId(id);
		contentDAO.deleteContent(c);
		System.out.println("Content deleted successfully with id=" + id);
		request.setAttribute("success", "Content deleted successfully");
		List<Content> contents = contentDAO.readAllContent();
		request.setAttribute("contents", contents);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/contents.jsp");
		rd.forward(request, response);
	}

}
