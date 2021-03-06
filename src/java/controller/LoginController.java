package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.Constant;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            response.sendRedirect(request.getContextPath() + "/waiting");
            return;
        }
        // Check cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    session = request.getSession(true);
                    session.setAttribute("username", cookie.getValue());
                    response.sendRedirect(request.getContextPath() + "/waiting");
                    return;
                }
            }
        }

        request.getRequestDispatcher("view/client/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isRememberMe = false;
        String remember = request.getParameter("remember");

        if ("on".equals(remember)) {
            isRememberMe = true;
        }

        String alertMsg = "";

        if (username.isEmpty() || password.isEmpty()) {
            alertMsg = "Username and password can't be empty!";
            request.setAttribute("alert", alertMsg);
            request.getRequestDispatcher("/view/client/view/login.jsp").forward(request, response);
            return;
        }

        UserService service = new UserServiceImpl();

        User user = service.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("account", user);

            if (isRememberMe) {
                saveRemeberMe(response, username);
            }

            response.sendRedirect(request.getContextPath() + "/waiting");
        } else {
            alertMsg = "Username or password isn't correct";
            request.setAttribute("alert", alertMsg);
            request.getRequestDispatcher("/view/client/view/login.jsp").forward(request, response);
        }
    }

    private void saveRemeberMe(HttpServletResponse response, String username) {
        Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
        cookie.setMaxAge(100 * 600);
        response.addCookie(cookie);
    }
}
