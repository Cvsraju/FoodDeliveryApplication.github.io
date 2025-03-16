package com.tap.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tap.daoimplementation.OrderDAOImpl;
import com.tap.daoimplementation.OrderitemDAOImpl; // Import OrderitemDAOImpl
import com.tap.model.Order;
import com.tap.model.Orderitem; // Import Orderitem model
import com.tap.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/orderhistory")
public class OrderHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("user");

        if (loggedUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            OrderDAOImpl orderDao = new OrderDAOImpl();
            OrderitemDAOImpl orderItemDao = new OrderitemDAOImpl(); // ✅ Correct DAO

            List<Order> orderList = orderDao.getAllOrders(loggedUser.getUserId());

            Map<Integer, List<Orderitem>> orderItemsMap = new HashMap<>();

            if (orderList != null && !orderList.isEmpty()) {
                for (Order order : orderList) {
                    List<Orderitem> items = orderItemDao.getOrderItemsByOrderId(order.getOrderId()); // ✅ Fetch from correct DAO
                    orderItemsMap.put(order.getOrderId(), items);
                }
            }

            request.setAttribute("orderList", orderList);
            request.setAttribute("orderItemsMap", orderItemsMap);
            request.getRequestDispatcher("orderhistory.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to error page if something goes wrong
        }

    }


}