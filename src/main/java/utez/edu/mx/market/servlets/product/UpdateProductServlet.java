package utez.edu.mx.market.servlets.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utez.edu.mx.market.daos.DaoProduct;
import utez.edu.mx.market.entities.Category;
import utez.edu.mx.market.entities.Product;

import java.io.IOException;

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/view/home.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        DaoProduct daoProduct = new DaoProduct();
        long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        long stock = Long.parseLong(request.getParameter("stock"));
        int idCategory = Integer.parseInt(request.getParameter("category"));
        String description = request.getParameter("description");

        Product product = new Product(id,name,description,stock,true,new Category(idCategory,null));
        request.setAttribute("success", daoProduct.updateProduct(product));

        doGet(request,response);
    }
}
