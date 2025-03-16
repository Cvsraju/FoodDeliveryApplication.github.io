package com.tap.servlets;

import java.io.IOException;
import java.util.List;

import com.tap.daoimplementation.OrderDAOImpl;
import com.tap.model.Order;
import com.tap.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Don't create a new session if it doesn't exist
        if (session != null) {
            User user = (User) session.getAttribute("user");

            if (user != null) {
                OrderDAOImpl orderDAO = new OrderDAOImpl();
                List<Order> recentOrders = orderDAO.getRecentOrdersByUserId(user.getUserId()); // Fetch last 5 orders

                req.setAttribute("user", user);
                req.setAttribute("recentOrders", recentOrders); // Pass recent orders to JSP

                req.getRequestDispatcher("user.jsp").forward(req, resp);
                return;
            } else {
                System.out.println("User attribute is null in session");
            }
        }
        resp.sendRedirect("login.html"); // Redirect if no session
    }
}