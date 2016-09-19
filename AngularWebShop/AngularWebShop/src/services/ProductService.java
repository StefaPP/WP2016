package services;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import beans.Buying;
import beans.BuyingHistory;
import beans.Categories;
import beans.Category;
import beans.Complaint;
import beans.Complaints;
import beans.Discount;
import beans.Discounts;
import beans.Product;
import beans.Products;
import beans.Review;
import beans.Reviews;
import beans.ShoppingList;
import beans.ShoppingListFile;
import beans.Store;
import beans.Stores;
import beans.User;
import beans.WishList;
import beans.WishListFile;

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
	@Path("/discounts")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Discount> getDisc() {
		return getDiscounts().getValues();
	}
	@GET
	@Path("/getCategories")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Category> getCategories() {
		return getCats().getCategories();

	}
	
	@POST
	@Path("/updateProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateProduct(Product p){
		Products products = (Products) ctx.getAttribute("products");
		products = getProducts();
		if(products.getProducts().containsKey(p.getId())){
			products.getProducts().remove(p.getId());
			products.getProductList().remove(p);
			try{
				Products.deleteProduct(p.getId());
				Products.writeProduct(p);
				products.getProducts().put(p.getId(),p);
				products.getProductList().add(p);
				ctx.setAttribute("products", products);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else
			System.out.println("Update nije uspeo ,navodno mapa ne sadrzi ovaj proizvod");
	}
	
	@POST
	@Path("/updateCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCategory(Category c){
		Categories cats = new Categories();
		System.out.println(c.getName());
		if(cats.getCats().containsKey(c.getName())){
			System.out.println("usao odje");
			cats.getCategories().remove(c);
			cats.getCats().remove(c);
			cats.getCategoryList().remove(c);
			try{
				Categories.deleteCategory(c.getName());
				Categories.writeCategory(c);
				cats.getCats().put(c.getName(),c);
				cats.getCategoryList().add(c);
				ctx.setAttribute("categories", cats);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}else
			System.out.println("Update nije uspeo ,navodno mapa ne sadrzi ovaj proizvod");
		}
	
	

	@POST
	@Path("/addOnSale")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addOnSale(Discount dis) {
		Discounts ds = new Discounts();
		String uniqueID = UUID.randomUUID().toString();
		dis.setId(uniqueID);
		System.err.println(dis);
		try {
			Discounts.writeItem(dis);
			ds.getDiscounts().put(dis.getId(), dis);
			ds.getDiscountList().add(dis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Add on sale service");
	}

	
	@POST
	@Path("/addProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addProduct(Product p) {
		Products products = (Products) ctx.getAttribute("products");
		products = getProducts();
		String uniqueID = UUID.randomUUID().toString();
		p.setId(uniqueID);
		p.setReview(" ");
		p.setRating(" ");
		
		try {
			Products.writeProduct(p);
			products.getProducts().put(p.getId(), p);
			products.getProductList().add(p);
			ctx.setAttribute("products", products);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(p + " <<<<<<<<");
		return null;
	}

	@DELETE
	@Path("/deleteProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteProduct(Product p) {
		Products products = (Products) ctx.getAttribute("products");
		if (products.getProducts().get(p.getId()) != null) {

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
	public void deleteProduct(Category c) {
		Categories categories = (Categories) ctx.getAttribute("categories");
		if (categories.getCats().get(c.getName()) != null) {

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
		HashMap<String, Product> storeProducts = new HashMap<String, Product>();
		String storeId = request.getParameter("id");
		for (Product p : getProducts().getValues()) {
			if (p.getStoreId().equals(storeId)) {
				storeProducts.put(p.getId(), p);
			}

		}
		return storeProducts.values();
	}

	@GET
	@Path("/getProductsForCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Product> getProductsForCategory() {
		HashMap<String, Product> categoryP = new HashMap<String, Product>();
		String catName = request.getParameter("name");
		for (Product p : getProducts().getValues()) {
			if (p.getCategory().equals(catName)) {
				categoryP.put(p.getId(), p);
			}

		}
		return categoryP.values();
	}
	
	@GET
	@Path("/getCategoryByName")
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategoryByName(){
		Categories cats = new Categories();
		String catName = request.getParameter("name");
		for(Category c : cats.getCats().values()){
			if(c.getName().equals(catName))
				return c;
		}
		return null;
	}
	
	@GET
	@Path("/getShoppingList")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<ShoppingList> getProductList() {
		return getShoppingList().getValues();
	}

	@POST
	@Path("/getUsersWishList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<WishList> getWList(User u) {
		HashMap<String, WishList> wl = new HashMap<String, WishList>();
		for (WishList w : getWishList().getWishArrayList()) {
			if (w.getCustomerId().equals(u.getUsername())) {
				wl.put(w.getId(), w);
			}
		}
		return wl.values();
	}

	public String sId;

	@POST
	@Path("/addToCart")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addToCart(ShoppingList sp) {
		ShoppingListFile spf = (ShoppingListFile) ctx.getAttribute("shoppingList");
		spf = getShoppingList();
		for (ShoppingList spl : spf.getShoppingArrayList()) {
			System.out.println(spl.getId());
			sId = spl.getId();
		}
		int i = Integer.parseInt(sId);
		i += 1;
		sp.setId(Integer.toString(i));
		sp.setDeliveryId("n/a");
		try {
			ShoppingListFile.writeItem(sp);
			spf.getShoppingList().put(sp.getId(), sp);
			spf.getShoppingArrayList().add(sp);
			ctx.setAttribute("shoppingList", spf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String wId;

	@POST
	@Path("/addWish")
	public void addToWishList(WishList wl) {
		WishListFile wlf = (WishListFile) ctx.getAttribute("wishList");
		wlf = getWishList();
		for (WishList w : wlf.getWishArrayList()) {
			wId = w.getId();
		}
		int i = Integer.parseInt(wId);
		i += 1;
		wl.setId(Integer.toString(i));
		try {
			WishListFile.writeItem(wl);
			wlf.getWishList().put(wl.getId(), wl);
			wlf.getWishArrayList().add(wl);
			ctx.setAttribute("wishList", wlf);

		} catch (IOException e) {
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
	@Path("/getDiscountProduct")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Product getDiscountProduct(Discount d){
		Products prods = new Products();
		Discounts ds = new Discounts();
		for(Discount dis : ds.getDiscounts().values()){
			if(dis.getId().equals(d.getId())){
				for(Product p : prods.getProducts().values())
					if(p.getId().equals(d.getProductId())){
						System.out.println("nasooo produkts");
						return p;
					}
			}
		}
		return null;
	}
	
	@POST
	@Path("/addCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCategory(Category c) {
		Categories cats = (Categories) ctx.getAttribute("categories");
		try {
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
	public Collection<ShoppingList> getUserShoppingList(ShoppingList u) {
		HashMap<String, ShoppingList> sp = new HashMap<String, ShoppingList>();

		for (ShoppingList s : getShoppingList().getShoppingArrayList()) {
			if (s.getCustomerId().equals(u.getCustomerId())) {
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

		for (ShoppingList s : getShoppingList().getShoppingList().values()) {
			if (s.getCustomerId().equals(u.getCustomerId())) {
				System.out.println(s.getCustomerId() + "==" + u.getCustomerId());
				itemToRemove.setId(s.getId());

				try {
					ShoppingListFile.deleteItem(itemToRemove.getId());
					itemToRemove = spl.getShoppingList().get(itemToRemove.getId());
					spl.getShoppingArrayList().remove(itemToRemove);
					ctx.setAttribute("shoppingList", spl);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@POST
	@Path("/buyProducts")
	@Consumes(MediaType.APPLICATION_JSON)
	public void buyProducts(Buying b) {
		BuyingHistory bh = (BuyingHistory) ctx.getAttribute("buyingHistory");
		String uniqueID = UUID.randomUUID().toString();
		b.setId(uniqueID);
		try {
			BuyingHistory.writeItem(b);
			bh.getBuyingHistory().add(b);
			bh.getHistory().put(b.getId(), b);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@GET
	@Path("/getBuyingHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Buying> getHistory() {
		return getBuyingHistory().getValues();
	}

	@POST
	@Path("/getUsersBuyingHistory")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Buying> getUsersHistory(Buying b) {
		BuyingHistory bh = (BuyingHistory) ctx.getAttribute("buyingHistory");
		HashMap<String, Buying> buyingHistory = new HashMap<String, Buying>();
		bh = new BuyingHistory();
		System.out.println(b.getCustomerId());

		for (Buying bu : bh.getBuyingHistory()) {
			if (bu.getCustomerId().equals(b.getCustomerId())) {
				buyingHistory.put(bu.getId(), bu);
			}
		}
		return buyingHistory.values();
	}

	@POST
	@Path("/sendComplaint")
	@Produces(MediaType.APPLICATION_JSON)
	public void sendComplaint(Complaint c){
		Complaints comps = new Complaints();
		System.out.println(c.getCustomerId() + " : " + c.getDescription());
		String uniqueID = UUID.randomUUID().toString();
		c.setId(uniqueID);
		try {
			Complaints.writeItem(c);
			comps.getComplaints().put(c.getId(), c);
			comps.getComplaintsList().add(c);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
	@GET
	@Path("/getComplaints")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Complaint> getComplaints(){ 
		Complaints c = new Complaints();
		return c.getComplaints().values();
	}
	
	@DELETE
	@Path("/deleteComplaint")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteComplaint(Complaint c){
		Complaints comps = new Complaints();
		System.out.println(c.getId());
		if(comps.getComplaints().get(c.getId()) !=null){
			
			try {
				Complaints.deleteItem(c.getId());
				comps.getComplaints().remove(c.getId());
				comps.getComplaintsList().remove(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	@DELETE
	@Path("/deleteFromHistory")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteFromHistory(Complaint c){
		BuyingHistory bh = new BuyingHistory();
		Buying b = new Buying();
		System.out.println("Treba da obrisem ovo iz istorije "  + c.getStoreId());
		if(bh.getHistory().get(c.getStoreId()) != null){	
				System.out.println("Nasao sam u istoriji");
				b = bh.getHistory().get(c.getStoreId());
				System.out.println("Dodelio sam " + b.getId() + " : " + b.getProductId()+ " : " + b.getCustomerId());
			try{
				BuyingHistory.deleteItem(b.getId());
				bh.getBuyingHistory().remove(b);
				bh.getHistory().remove(b);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@POST
	@Path("/removeItem")
	public void removeItem(ShoppingList sp) {
		ShoppingListFile spl = (ShoppingListFile) ctx.getAttribute("shoppingList");
		ShoppingList itemToRemove = new ShoppingList();
		for (ShoppingList s : getShoppingList().getShoppingArrayList()) {
			if (s.getCustomerId().equals(sp.getCustomerId()) && s.getProductId().equals(sp.getProductId())) {
				itemToRemove.setId(s.getId());

			}
		}
		try {
			ShoppingListFile.deleteItem(itemToRemove.getId());
			itemToRemove = spl.getShoppingList().get(itemToRemove.getId());
			spl.getShoppingArrayList().remove(itemToRemove);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@GET
	@Path("/discountPrice")
	@Produces(MediaType.APPLICATION_JSON)
	public Discount getDiscountPrice() {
		Discounts ds = new Discounts();
		String productId = request.getParameter("id");
		for(Discount d : ds.getDiscountList()) {
			if(d.getProductId().equals(productId)){
				return d;
			}
		}
		return null;

	}
	
	@POST
	@Path("/removeWish")
	public void removeWish(WishList wl) {
		WishListFile wlf = (WishListFile) ctx.getAttribute("wishList");
		WishList wishToRemove = new WishList();
		for (WishList w : getWishList().getWishArrayList()) {
			if (w.getCustomerId().equals(wl.getCustomerId()) && w.getProductId().equals(wl.getProductId())) {
				wishToRemove.setId(w.getId());
			}
		}
		try {
			WishListFile.deleteItem(wishToRemove.getId());
			wishToRemove = wlf.getWishList().get(wishToRemove.getId());
			wlf.getWishArrayList().remove(wishToRemove);
		} catch (IOException e) {
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

	private WishListFile getWishList() {
		WishListFile wl = (WishListFile) ctx.getAttribute("wishList");
		if (wl == null) {
			wl = new WishListFile(ctx.getRealPath(""));
			ctx.setAttribute("wishList", wl);
		}
		return wl;
	}

	private ShoppingListFile getShoppingList() {
		ShoppingListFile sp = (ShoppingListFile) ctx.getAttribute("shoppingList");
		if (sp == null) {
			sp = new ShoppingListFile(ctx.getRealPath(""));
			ctx.setAttribute("shoppingList", sp);
		}
		return sp;
	}

	private Discounts getDiscounts() {
		Discounts ds = (Discounts) ctx.getAttribute("discounts");
		if (ds == null) {
			ds = new Discounts(ctx.getRealPath(""));
			ctx.setAttribute("discounts", ds);
		}
		return ds;
	}

	private BuyingHistory getBuyingHistory() {
		BuyingHistory bh = (BuyingHistory) ctx.getAttribute("buyingHistory");
		if (bh == null) {
			bh = new BuyingHistory(ctx.getRealPath(""));
			ctx.setAttribute("buyingHistory", bh);
		}
		return bh;
	}

}
