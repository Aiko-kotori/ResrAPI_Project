package com.subjectdb.mongodb.servlets;

import java.io.IOException;
import java.sql.Timestamp;
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

@WebServlet("/addContent")
public class AddContentServlet extends HttpServlet {

	private static final long serialVersionUID = -7060758261496829905L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String temp = request.getParameter("temp");
		String group = request.getParameter("group");
		//Get the difference in milliseconds from UTC January 1, 1970, midnight, Coordinated Universal Time
		long millis = System.currentTimeMillis();
		//Create a Timestamp object with milliseconds as arguments
		Timestamp timestamp = new Timestamp(millis);
		System.out.println(timestamp);	//Check
		//Value of Timestamp object
		String time = timestamp.toString();

		if ((temp == null || temp.equals(""))
				|| (group == null || group.equals(""))) {
			request.setAttribute("error", "Mandatory Parameters Missing");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/contents.jsp");
			rd.forward(request, response);
		} else {
			Content c = new Content();
			c.setGroup(group);
			c.setTemp(temp);
			c.setTime(time);
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBContentDAO contentDAO = new MongoDBContentDAO(mongo);
			contentDAO.createContent(c);
			System.out.println("Content Added Successfully with id="+c.getId());
			request.setAttribute("success", "Content Added Successfully");
			List<Content> contents = contentDAO.readAllContent();
			request.setAttribute("contents", contents);

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/contents.jsp");
			rd.forward(request, response);
		}
	}

}
