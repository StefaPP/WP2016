package services;



import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
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
			Users.writeStore(u);
			users.getUsers().put(u.getUsername(),u);
			users.getUserList().add(u);
		}catch(IOException e) {
			e.printStackTrace();
		}
	
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
