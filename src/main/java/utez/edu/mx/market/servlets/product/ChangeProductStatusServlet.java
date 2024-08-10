package utez.edu.mx.market.servlets.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utez.edu.mx.market.daos.DaoProduct;

import java.io.IOException;

@WebServlet("/ChangeProductStatusServlet")
public class ChangeProductStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/home.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        DaoProduct daoProduct = new DaoProduct();
        long id = Long.parseLong(req.getParameter("id"));
        req.setAttribute("sucess", daoProduct.changeProductStatus(id));

        doGet(req,resp);
    }
}
