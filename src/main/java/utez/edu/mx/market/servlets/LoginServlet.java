package utez.edu.mx.market.servlets;

import utez.edu.mx.market.daos.DaoLogin;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag = (boolean) request.getAttribute("flag");
        request.setAttribute("errorMessage",!flag); //True = session existe: false = no hay error
        request.getRequestDispatcher(flag ? "/view/home.jsp" : "/view/login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        DaoLogin dao = new DaoLogin();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(dao.findUserByUsernameAndPassord(username,password)){
            if(request.getSession(false) == null){
                request.getSession(true);
            }
            request.setAttribute("flag", true);
            request.getSession(false).setAttribute("user", username);
            request.setAttribute("flag",true);
        }else{
            request.setAttribute("flag", false);
        }
        doGet(request,response);
    }
}