<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Hủy session hiện tại
    if (session != null) {
        session.invalidate();
    }

    
    response.sendRedirect("login"); 
    
%>
