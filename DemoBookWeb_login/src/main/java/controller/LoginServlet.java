package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Nếu đã đăng nhập thì về adminHome luôn
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            resp.sendRedirect(req.getContextPath() + "/adminHome");
            return;
        }

        // Lấy cookie "rememberedUser" nếu có
        Cookie[] cookies = req.getCookies();
        String rememberedUser = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("rememberedUser".equals(c.getName())) {
                    rememberedUser = c.getValue();
                    break;
                }
            }
        }

        req.setAttribute("rememberedUser", rememberedUser);

        //
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("username", username);

            // Ghi nhớ đăng nhập
            if ("on".equals(remember)) {
                Cookie cookie = new Cookie("rememberedUser", username);
                cookie.setMaxAge(60 * 60 * 24 * 7); // 7 ngày
                cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("rememberedUser", "");
                cookie.setMaxAge(0);
                cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                resp.addCookie(cookie);
            }

            resp.sendRedirect(req.getContextPath() + "/adminHome");
        } else {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
