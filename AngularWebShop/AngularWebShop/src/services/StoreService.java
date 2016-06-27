package services;

import java.io.IOException;
import java.util.ArrayList;
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

import beans.ProductsInStore;
import beans.ProductsInStoreData;
import beans.Store;
import beans.Stores;

@Path("/stores")
public class StoreService {
	
	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;
	
	@GET
	@Path("/getStores")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Store> getStoresService(){
		return getStores().getValues();
	}
	
	@GET
	@Path("/getStoreProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<ProductsInStore> getStoreProducts(){
		return getProductsOfStore().getProducts();
	}
	
	@GET
	@Path("/getStore")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Store getStore(String s) {
	Stores stores = new Stores();
	s = request.getParameter("id");
	return stores.getStore(s);
	}
	
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public String addStore(Store s){
		Stores stores = (Stores) ctx.getAttribute("stores");
		int id = Integer.parseInt(stores.getStoreList().get(stores.getStoreList().size()-1).getId());
		id +=1;
		s.setId(Integer.toString(id));
		s.setSeller("n/a");
		try {
			Stores.writeStore(s);
			stores.getStoreList().add(s);
			ctx.setAttribute("stores", stores);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Dodata";
	}
	
	private Stores getStores(){
		Stores stores = (Stores) ctx.getAttribute("stores");
		if(stores == null){
			stores = new Stores(ctx.getRealPath(""));
			ctx.setAttribute("stores",stores);
		}
		return stores;
		
	}
	
	private ProductsInStoreData getProductsOfStore() {
		ProductsInStoreData pis = (ProductsInStoreData) ctx.getAttribute("pis");
		if(pis == null){
			pis = new ProductsInStoreData(ctx.getRealPath(""));
			ctx.setAttribute("pis", pis);
		}
		return pis;
	}
	
	
}

