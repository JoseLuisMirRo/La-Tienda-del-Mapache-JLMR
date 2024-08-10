package utez.edu.mx.market.servlets.product;

import utez.edu.mx.market.daos.DaoProduct;
import utez.edu.mx.market.entities.Category;
import utez.edu.mx.market.entities.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateProductServlet", value = "/CreateProductServlet")
public class CreateProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/home.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        DaoProduct daoProduct = new DaoProduct();
        String name = request.getParameter("name");
        long stock = Long.parseLong(request.getParameter("stock"));
        int idCategory = Integer.parseInt(request.getParameter("category"));
        String description = request.getParameter("description");

        Product product = new Product(name,description,stock,true,new Category(idCategory,null));
        request.setAttribute("success", daoProduct.createProduct(product));

        doGet(request,response);
    }
}