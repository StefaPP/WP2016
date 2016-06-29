package services;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import beans.Categories;
import beans.Category;
import beans.Product;
import beans.ProductToAdd;
import beans.Products;
import beans.Review;
import beans.Reviews;
import beans.ShoppingCart;
import beans.ShoppingCartItem;
import beans.ShoppingList;
import beans.ShoppingListFile;
import beans.Store;
import beans.Stores;

@Path("/proizvodi")
public class ProductService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

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
		return getCats().getCategories();
		
	}
	
	@POST
	@Path("/addProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addProduct(Product p) {
		Products products = (Products) ctx.getAttribute("products");
		products = getProducts();
		int id = Integer.parseInt(products.getProductList().get(products.getProductList().size()-1).getId());
		id +=1;
		p.setId(Integer.toString(id));
		p.setReview(" ");
		p.setRating(" ");
		
		try { 
			Products.writeProduct(p);
			products.getProducts().put(p.getId(), p);
			products.getProductList().add(p);
			ctx.setAttribute("products", products);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		System.out.println(p + " <<<<<<<<");
		return null;
	}
	
	@DELETE
	@Path("/deleteProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteProduct(Product p){
		Products products = (Products) ctx.getAttribute("products");
		if(products.getProducts().get(p.getId()) != null){
			
			try {
				Products.deleteProduct(p.getId());
				products.getProducts().remove(p.getId());
				products.getProductList().remove(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@DELETE
	@Path("/deleteCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteProduct(Category c){
		Categories categories = (Categories) ctx.getAttribute("categories");
		if(categories.getCats().get(c.getName()) != null){
			
			try {
				Categories.deleteCategory(c.getName());
				categories.getCats().remove(c.getName());
				categories.getCategoryList().remove(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/getProductsOfStore")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getProductsFromStore() {
		HashMap<String,Product> storeProducts = new HashMap<String,Product> ();
		String storeId = request.getParameter("id");
		for(Product p : getProducts().getValues()){
			if (p.getStoreId().equals(storeId))
			{
				storeProducts.put(p.getId(), p);
			}
				
		}
			return storeProducts.values();
	}

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
		getShoppingCart().addItem(getProducts().getProduct(p.id));
		System.out.println("Product " + getProducts().getProduct(p.id) + " added with count: " + p.count);
		return "OK";
	}

	@GET
	@Path("/getShoppingList")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<ShoppingList> getProductList() {
		return getShoppingList().getValues();
	}
	
	@POST
	@Path("/addToCart")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addToCart(ShoppingList sp){
		ShoppingListFile spf = (ShoppingListFile) ctx.getAttribute("shoppingList");
		int id = spf.getShoppingList().size();
		id +=1;
		sp.setId(Integer.toString(id+1));
		sp.setDeliveryId("n/a");
		System.out.println(sp + "    <<<<<<");
		try {
			ShoppingListFile.writeItem(sp);
			spf.getShoppingList().put(sp.getId(), sp);
			spf.getShoppingArrayList().add(sp);
		}catch(IOException e){
			e.printStackTrace();
		}
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
		Categories cats = (Categories) ctx.getAttribute("categories");
		try{
			Categories.writeCategory(c);
			cats.getCats().put(c.getName(), c);
			cats.getCategoryList().add(c);
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
	
	private ShoppingListFile getShoppingList(){
		ShoppingListFile sp = (ShoppingListFile) ctx.getAttribute("shoppingList");
		if(sp == null){
			sp = new ShoppingListFile(ctx.getRealPath(""));
			ctx.setAttribute("shoppingList", sp);
			
		}
		return sp;
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
