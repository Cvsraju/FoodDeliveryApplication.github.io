<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Cart, com.tap.model.CartItem,com.tap.model.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart - FoodDelivery</title>
    <link rel="stylesheet" href="home.css">
    <link rel="stylesheet" href="cart.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<body>
    <header class="header">
        <nav class="nav">
            <div class="logo">FoodDelivery</div>
            <ul class="nav-links">
                <li><a href="/FoodDeliveryapp2/home">Home</a></li>
                <li><a href="/FoodDeliveryapp2/cart" class="active">Cart <span class="cart-count">0</span></a></li>
                <li><a href="/FoodDeliveryapp2/orderhistory">Orders</a></li>
                 <li><a href="dashboard">
    <ion-icon name="person-circle" class="profile-icon"></ion-icon>
    <span class="nav-text"> 
        <%
            User loggedUser = (User) session.getAttribute("user");
            if (loggedUser != null) {
                System.out.println("✅ JSP: User Found in Session - " + loggedUser.getName());
                out.print(loggedUser.getName().split(" ")[0]); // Display first name
            } else {
                System.out.println("❌ JSP: User Not Found in Session");
                out.print("Login");
            }
        %>
    </span>
</a></li>

            </ul>
        </nav>
    </header>

    <main class="cart-container">
        <div class="cart-content">
            <h1>Your Cart</h1>
            <div class="cart-items">
                <%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }

   
    String restaurantId = request.getParameter("restaurantId");
    if (restaurantId == null || restaurantId.isEmpty()) {
        // Cast the session attribute properly to String
        restaurantId = String.valueOf(session.getAttribute("restaurantId"));
    }
%>




    <%
        if (!cart.getItems().isEmpty()) {
            for (CartItem item : cart.getItems().values()) {
    %>
        <div class="cart-item">
            <img src="<%= item.getImagePath() %>" alt="<%= item.getName() %>">
            <div class="item-details">
                <h3><%= item.getName() %></h3>
                <p><%= item.getDescription() %></p>

                <div class="quantity-controls">
                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="restaurantId" value="<%= restaurantId != null ? restaurantId : "" %>">
                        <input type="hidden" name="itemId" value="<%= item.getId() %>">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                        <button class="quantity-btn minus" type="submit" <% if (item.getQuantity() == 1) { %>disabled<% } %>>-</button>
                    </form>

                    <span class="quantity"><%= item.getQuantity() %></span>

                    <form action="cart" method="post" style="display: inline;">
                        <input type="hidden" name="restaurantId" value="<%= restaurantId != null ? restaurantId : "" %>">
                        <input type="hidden" name="itemId" value="<%= item.getId() %>">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                        <button class="quantity-btn plus" type="submit">+</button>
                    </form>
                </div>

            </div>
            <div class="item-price">₹<%= item.getPrice() * item.getQuantity() %></div>

            <form action="cart" method="post">
                <input type="hidden" name="restaurantId" value="<%= restaurantId != null ? restaurantId : "" %>">
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="action" value="remove">
                <button class="remove-item" type="submit" title="Remove item"><i class="fas fa-trash"></i></button>
            </form>

            <script>
                console.log("Item ID: <%= item.getId() %>");
            </script>

        </div>
    <%
            }
        } else {
    %>
        <p style="text-align: center; color: #757575;">Your Cart is Empty</p>
    <%
        }
    %>
	</div>

	<% if (!cart.getItems().isEmpty()) { %>
			<div class="cart-summary">
			    <div class="summary-row total">
			        <span>Total Amount</span>
			        <span class="total-price">₹<%= cart.getTotalPrice() %></span>
			    </div>
			    <div class="button-group">
			        <a href="menu?restaurantId=<%= restaurantId %>"><button class="add-more-btn">Add More Items</button></a>
			         
						    <input type="hidden" name="totalAmount" value="<%= cart.getTotalPrice() %>">
						    <button><a href="checkout" class="checkout-btn">Proceed to Checkout</a></button>
						
			    </div>
    
	</div>
	<% } %>

        </div>
    </main>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const cartCount = document.querySelector('.cart-count');
            const itemCount = document.querySelectorAll('.cart-item').length;
            cartCount.textContent = itemCount;
            
            if (itemCount === 0) {
                document.querySelector('.cart-items').innerHTML = '<p class="empty-cart">Your cart is empty</p>';
            }
        });
    </script>
</body>
</html>