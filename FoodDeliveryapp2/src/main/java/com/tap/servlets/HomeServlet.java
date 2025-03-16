package com.tap.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tap.daoimplementation.RestaurantDAOImpl;
import com.tap.model.Restaurant;



@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet Called");

        RestaurantDAOImpl resDAO = new RestaurantDAOImpl();

        List<Restaurant> allRes = resDAO.getAllRestaurants();
        req.setAttribute("restaurants", allRes);

        RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
        rd.forward(req, resp);
    }
}
 