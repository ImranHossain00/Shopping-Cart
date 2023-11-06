package eshoppers.web;

import com.imran.eshoppers.repository.CartItemRepositoryImpl;
import com.imran.eshoppers.repository.CartRepositoryImpl;
import com.imran.eshoppers.repository.ProductRepositoryImpl;
import com.imran.eshoppers.service.CartService;
import com.imran.eshoppers.service.CartServiceImpl;
import com.imran.eshoppers.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(Checkout.class);

    private CartService cartService
            = new CartServiceImpl(new CartRepositoryImpl(),
                                  new ProductRepositoryImpl(),
                                  new CartItemRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        LOGGER.info("serving checkout page");

        var curUser = SecurityContext.getCurrentUser(req);
        var cart = cartService.getCartByUser(curUser);
        req.setAttribute("cart", cart);

        RequestDispatcher rd
                = req.getRequestDispatcher("/WEB-INF/checkout.jsp");
        rd.forward(req, resp);
    }
}
