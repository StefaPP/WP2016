package services;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

import beans.Buying;
import beans.BuyingHistory;
import beans.Categories;
import beans.Category;
import beans.Product;
import beans.Products;
import beans.Review;
import beans.Reviews;
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
	

	@GET
	@Path("/getShoppingList")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<ShoppingList> getProductList() {
		return getShoppingList().getValues();
	}
	
	public String sId;
	@POST
	@Path("/addToCart")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addToCart(ShoppingList sp){
		ShoppingListFile spf = (ShoppingListFile) ctx.getAttribute("shoppingList");		
		spf = getShoppingList();
		for(ShoppingList spl : spf.getShoppingArrayList()){
			System.out.println(spl.getId());
			sId = spl.getId();
		}
		int i = Integer.parseInt(sId);
		i+=1;
		sp.setId(Integer.toString(i));
		sp.setDeliveryId("n/a");
		try {
			ShoppingListFile.writeItem(sp);
			spf.getShoppingList().put(sp.getId(), sp);
			spf.getShoppingArrayList().add(sp);
			ctx.setAttribute("shoppingList", spf);
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

	@POST
	@Path("/getUsersShoppingList")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<ShoppingList> getUserShoppingList(ShoppingList u){
		HashMap<String,ShoppingList> sp = new HashMap<String,ShoppingList>();
		
		for(ShoppingList s : getShoppingList().getShoppingArrayList())
		{
			if(s.getCustomerId().equals(u.getCustomerId())){
				sp.put(s.getId(), s);
			}	
		}
		return sp.values();
	}
	
	
	@POST
	@Path("/clearShoppingList")
	public void clearShoppingList(ShoppingList u) {
		ShoppingListFile spl = (ShoppingListFile) ctx.getAttribute("shoppingList");
		ShoppingList itemToRemove = new ShoppingList();	
		
		for(ShoppingList s : getShoppingList().getShoppingList().values())
		{
			if(s.getCustomerId().equals(u.getCustomerId())){
				System.out.println(s.getCustomerId() + "==" + u.getCustomerId());
				itemToRemove.setId(s.getId());
			
		try{
			ShoppingListFile.deleteItem(itemToRemove.getId());
			itemToRemove = spl.getShoppingList().get(itemToRemove.getId());
			spl.getShoppingArrayList().remove(itemToRemove);
			ctx.setAttribute("shoppingList", spl);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
}
		
	
	@POST
	@Path("/buyProducts")
	@Consumes(MediaType.APPLICATION_JSON)
	public void buyProducts(Buying b){
		BuyingHistory bh = (BuyingHistory) ctx.getAttribute("buyingHistory");
		
		/*int id = bh.getBuyingHistory().size();
		id +=1;
		b.setId(Integer.toString(id+1));	*/
		
		String uniqueID = UUID.randomUUID().toString();
		b.setId(uniqueID);
		try {
			BuyingHistory.writeItem(b);
			bh.getBuyingHistory().add(b);
			bh.getHistory().put(b.getId(), b);
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	@GET
	@Path("/getBuyingHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Buying> getHistory(){
		return getBuyingHistory().getValues();
	}
	
	
	
	@POST
	@Path("/removeItem")
	public void removeItem(ShoppingList sp){
		ShoppingListFile spl = (ShoppingListFile) ctx.getAttribute("shoppingList");
		ShoppingList itemToRemove = new ShoppingList();
		for(ShoppingList s : getShoppingList().getShoppingArrayList())
		{
			if(s.getCustomerId().equals(sp.getCustomerId()) && s.getProductId().equals(sp.getProductId())){
				itemToRemove.setId(s.getId());
			
			}
		}
		try{
			ShoppingListFile.deleteItem(itemToRemove.getId());
			itemToRemove = spl.getShoppingList().get(itemToRemove.getId());
			spl.getShoppingArrayList().remove(itemToRemove);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
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

	private BuyingHistory getBuyingHistory(){
		BuyingHistory bh = (BuyingHistory) ctx.getAttribute("buyingHistory");
		if(bh == null){
			bh = new BuyingHistory(ctx.getRealPath(""));
			ctx.setAttribute("buyingHistory", bh);
		}
		return bh;
	}

}
