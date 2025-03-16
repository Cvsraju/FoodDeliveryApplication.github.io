package com.tap.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tap.daoimplementation.MenuDAOImpl;
import com.tap.daoimplementation.RestaurantDAOImpl;
import com.tap.model.Menu;
import com.tap.model.Restaurant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/menu")  // Ensure this is not commented
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("✅ MenuServlet Called");

        String restaurantIdParam = req.getParameter("restaurantId");
        if (restaurantIdParam == null || restaurantIdParam.isEmpty()) {
            System.out.println("❌ Missing restaurantId parameter!");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Restaurant ID is required.");
            return;
        }
        int rid = Integer.parseInt(restaurantIdParam);
        System.out.println("✅ Received restaurantId: " + rid);

        MenuDAOImpl menuDao = new MenuDAOImpl();
        RestaurantDAOImpl restaurantDao = new RestaurantDAOImpl();
        try {
            Restaurant restaurant = restaurantDao.getRestaurant(rid);
            System.out.println("✅ Restaurant found: " + (restaurant != null ? restaurant.getName() : "Not Found"));

            req.setAttribute("restaurantName", restaurant != null ? restaurant.getName() : "Unknown Restaurant");
            
            req.setAttribute("cuisineType",  restaurant != null ? restaurant.getCusineType() : "Unknown Type");
            
            req.setAttribute("restaurantRating", restaurant != null ? restaurant.getRating() : "Unknown Rating");

            List<Menu> menuList = menuDao.getAllMenu(rid);
            System.out.println("✅ Menu items found: " + (menuList != null ? menuList.size() : 0));

            req.setAttribute("menus", menuList != null ? menuList : new ArrayList<>());
        } catch (Exception e) {
            System.out.println("❌ Error fetching menu:");
            e.printStackTrace();
            req.setAttribute("menus", new ArrayList<>());
        }

        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
        System.out.println("✅ Forwarded to menu.jsp");
    }
}
