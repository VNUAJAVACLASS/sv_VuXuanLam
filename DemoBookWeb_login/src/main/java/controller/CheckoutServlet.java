package controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckoutServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
	}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String method = request.getParameter("paymentMethod");

        double amount = 0;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (Exception ignore) {}

        HttpSession session = request.getSession();
        session.setAttribute("checkout_name", fullname);
        session.setAttribute("checkout_method", method);
        session.setAttribute("checkout_amount", amount);

        request.getRequestDispatcher("/WEB-INF/views/payment-success.jsp").forward(request, response);



    }
}
