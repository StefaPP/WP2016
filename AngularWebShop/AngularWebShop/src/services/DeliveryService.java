package services;

import java.util.Collection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Deliverers;
import beans.Delivery;
import beans.Product;

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


	private Deliverers getDeliverers() {
		Deliverers deli = (Deliverers) ctx.getAttribute("deliverers");
		if(deli == null){
			deli = new Deliverers(ctx.getRealPath(""));
			ctx.setAttribute("deliverers", deli);
		}
		return deli;
	}
}
