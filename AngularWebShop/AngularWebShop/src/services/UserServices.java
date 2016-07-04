package services;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.ShoppingList;
import beans.ShoppingListFile;
import beans.Stores;
import beans.User;
import beans.Users;
@Path("/users")
public class UserServices {

	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsersService(){
		return getUsers().getValues();
	}
	
	@POST
	@Path("/signUp")
	@Produces(MediaType.APPLICATION_JSON)
	public void signUp(User u) {
		Users users = (Users) ctx.getAttribute("users");
		System.out.println( "Signup request from " + u.getFirstName() + " ");
		
		try {
			u.setRole("customer");
			Users.writeUser(u);
			users.getUsers().put(u.getUsername(),u);
			users.getUserList().add(u);
			ctx.setAttribute("users", users);
		}catch(IOException e) {
			e.printStackTrace();
		}
}
	
	
	
	/*for(ShoppingList s : getShoppingList().getShoppingArrayList())
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
	}*/
	
	@POST
	@Path("/hireSeller")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void hireSeller(User u) {
		Users users = (Users) ctx.getAttribute("users");
		Stores stores = (Stores) ctx.getAttribute("stores");
		stores = getStores();
		users = getUsers();
		System.out.println(request.getParameter("store") + " AJDI RADNJE ");
		User promoteToSeller = new User();
		for (User user : users.getValues()) {
			if (u.getUsername().equals(u.getUsername()))
				promoteToSeller.setUsername(u.getUsername());
		}
		promoteToSeller = users.getUsers().get(u.getUsername());
		users.getUsers().remove(promoteToSeller.getUsername());
		users.getUserList().remove(promoteToSeller);
		try {
			Users.deleteUser(promoteToSeller.getUsername());
			promoteToSeller.setRole("seller");
			System.out.println(promoteToSeller.getUsername() + " " + promoteToSeller.getAddress() + " " + promoteToSeller.getRole());
			Users.writeUser(promoteToSeller);
			users.getUsers().put(promoteToSeller.getUsername(), promoteToSeller);
			users.getUserList().add(promoteToSeller);
			ctx.setAttribute("users", users);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User login(User u) {
		Users users = (Users) ctx.getAttribute("users");
		System.out.println("This is the customer " + u);
		if (users.getUsers().containsKey(u.getUsername()) && (users.getUsers().get(u.getUsername())).getPassword().equals(u.getPassword())){
			return users.getUsers().get(u.getUsername());
		}
		else {
			System.out.println("ne postoji !");
			return null;
	}
	}
	
	
	private Stores getStores() {
		Stores stores = (Stores) ctx.getAttribute("stores");
		if (stores == null) {
			stores = new Stores(ctx.getRealPath(""));
			ctx.setAttribute("stores", stores);
		}
		return stores;

	}
	
	
	private Users getUsers(){
		Users users = (Users) ctx.getAttribute("users");
		if(users == null){
			users = new Users(ctx.getRealPath(""));
			ctx.setAttribute("users",users);
		}
		return users;
	}
}
