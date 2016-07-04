package services;

import java.io.IOException;
import java.util.Collection;
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

import beans.Deliverers;
import beans.Delivery;
import beans.Product;
import beans.Store;
import beans.Stores;

@Path("/delivery")
public class DeliveryService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;


	@GET
	@Path("/getDeliverers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Delivery> getJustProducts() {
		return getDeliverers().getValues();
	}
	
	@POST
	@Path("/addDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addDeliverer(Delivery d){
		Deliverers deli = (Deliverers) ctx.getAttribute("deliverers");
		deli = getDeliverers();
		System.out.println(d.getName() + " " + d.getCountry());
		String uniqueID = UUID.randomUUID().toString();
		d.setId(uniqueID);
		
		try{
			Deliverers.writeDelivery(d);
			deli.getDeliverersList().add(d);
			deli.getDeliverers().put(uniqueID, d);
			ctx.setAttribute("deliverers", deli);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	@POST
	@Path("/updateDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateDeliverer(Delivery d){
		Deliverers deli = (Deliverers) ctx.getAttribute("deliverers");
		deli = getDeliverers();
		
		deli.getDeliverers().remove(d.getId());
		deli.getDeliverersList().remove(d);
		try{
			Deliverers.deleteDelivery(d.getId());
			Deliverers.writeDelivery(d);
			deli.getDeliverers().put(d.getId(),d);
			deli.getDeliverersList().add(d);
			ctx.setAttribute("deliverers", deli);
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@DELETE
	@Path("/deleteDeliverer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteStore(Delivery d) {
		Deliverers deli = (Deliverers) ctx.getAttribute("deliverers");

		try {
			Deliverers.deleteDelivery(d.getId());
			deli.getDeliverers().remove(d.getId());
			deli.getDeliverersList().remove(d);
			ctx.setAttribute("deliverers", deli);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	

	private Deliverers getDeliverers() {
		Deliverers deli = (Deliverers) ctx.getAttribute("deliverers");
		if(deli == null){
			deli = new Deliverers(ctx.getRealPath(""));
			ctx.setAttribute("deliverers", deli);
		}
		return deli;
	}
}
