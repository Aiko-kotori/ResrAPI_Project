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

@WebServlet("/editContent")
public class EditContentServlet extends HttpServlet {

	private static final long serialVersionUID = -6554920927964049383L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for edit operation");
		}
		System.out.println("Content edit requested with id=" + id);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBContentDAO contentDAO = new MongoDBContentDAO(mongo);
		Content c = new Content();
		c.setId(id);
		c = contentDAO.readContent(c);
		request.setAttribute("content", c);
		List<Content> contents = contentDAO.readAllContent();
		request.setAttribute("contents", contents);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/contents.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id"); // keep it non-editable in UI
		if (id == null || "".equals(id)) {
			throw new ServletException("id missing for edit operation");
		}

		String temp = request.getParameter("temp");
		String group = request.getParameter("group");
		//Get the difference in milliseconds from UTC January 1, 1970, midnight, Coordinated Universal Time
		//long millis = System.currentTimeMillis();
		//Create a Timestamp object with milliseconds as arguments
		//Timestamp timestamp = new Timestamp(millis);
		//System.out.println("timestamp");	//Check
		//Value of Timestamp object
		//String time = timestamp.toString();

		if ((temp == null || temp.equals(""))
				|| (group == null || group.equals(""))) {
			request.setAttribute("error", "Temp and Group Can't be empty");
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBContentDAO contentDAO = new MongoDBContentDAO(mongo);
			Content c = new Content();
			c.setId(id);
			c.setTemp(temp);
			c.setGroup(group);
			//c.setITime(time);
			request.setAttribute("content", c);
			List<Content> contents = contentDAO.readAllContent();
			request.setAttribute("contents", contents);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/contents.jsp");
			rd.forward(request, response);
		} else {
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBContentDAO contentDAO = new MongoDBContentDAO(mongo);
			Content c = new Content();
			c.setId(id);
			c.setTemp(temp);
			c.setGroup(group);
			//c.setITime(time);
			contentDAO.updateContent(c);
			System.out.println("Content edited successfully with id=" + id);
			request.setAttribute("success", "Content edited successfully");
			List<Content> contents = contentDAO.readAllContent();
			request.setAttribute("contents", contents);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/contents.jsp");
			rd.forward(request, response);
		}
	}

}
