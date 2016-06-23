package services;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.media.jfxmedia.Media;

import beans.Categories;
import beans.Category;
import beans.Product;
import beans.ProductToAdd;
import beans.Products;
import beans.Review;
import beans.Reviews;
import beans.ShoppingCart;
import beans.ShoppingCartItem;
import beans.Store;
import beans.Stores;

@Path("/proizvodi")
public class ProductService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "Hello Jersey";
	}

	@GET
	@Path("/getJustProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getJustProducts() {
		return getProducts().getValues();
	}

	@GET
	@Path("/getCategories")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Category> getCategories(){
		System.out.println("Usao u categorije");
		return getCats().getCategories();
		
	}
	
/*	@GET
	@Path("/getReviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Review> getReviews(){
		return getRevs().getReviews();
	}*/
	
	@GET
	@Path("/getProductsForCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getProductsForCategory(){
		HashMap<String,Product> categoryP = new HashMap<String,Product>();
		String catName = request.getParameter("name");
		for(Product p : getProducts().getValues()){
			if (p.getCategory().equals(catName))
			{
				categoryP.put(p.getId(), p);
			}
				
		}
	
		return categoryP.values();
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String add(ProductToAdd p) {
		getShoppingCart().addItem(getProducts().getProduct(p.id), p.count);
		System.out.println("Product " + getProducts().getProduct(p.id) + " added with count: " + p.count);
		return "OK";
	}

	@GET
	@Path("/getProduct")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Product getProduct(String id) {
		Products products = new Products();
		return products.getProduct(request.getParameter("id"));
	}
	
	@GET
	@Path("/getJustSc")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShoppingCartItem> getJustSc() {
		return getShoppingCart().getItems();
	}

	@POST
	@Path("/addCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCategory(Category c) {
		System.out.println("236t135214526536212154");
		
		Categories cats = (Categories) ctx.getAttribute("categories");
		try{
			Categories.writeCategory(c);
			ctx.setAttribute("categories", cats);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return "Dodata";
	}
	
	@GET
	@Path("/getTotal")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTotal() {
		return "" + getShoppingCart().getTotal();
	}

	@POST
	@Path("/clearSc")
	@Produces(MediaType.APPLICATION_JSON)
	public String clearSc() {
		getShoppingCart().getItems().clear();
		return "OK";
	}

	private Products getProducts() {
		Products products = (Products) ctx.getAttribute("products");
		if (products == null) {
			products = new Products(ctx.getRealPath(""));
			ctx.setAttribute("products", products);
		}
		return products;
	}

	private Categories getCats() {
		Categories cats = (Categories) ctx.getAttribute("categories");
		if (cats == null) {
			cats = new Categories(ctx.getRealPath(""));
			ctx.setAttribute("categories", cats);
		}
		return cats;

	}
	

	private ShoppingCart getShoppingCart() {
		ShoppingCart sc = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
		if (sc == null) {
			sc = new ShoppingCart();
			request.getSession().setAttribute("shoppingCart", sc);
		}
		return sc;
	}

}
