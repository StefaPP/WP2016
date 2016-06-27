package services;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Review;
import beans.Reviews;

@Path("/reviews")
public class ReviewService {

	@Context
	HttpServletRequest request;
	@Context
	ServletContext ctx;

	@GET
	@Path("/getReviews")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Review> getReviews(){
		return getRevs().getReviews();
	}
	
	
	@POST
	@Path("/addReview")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addReview(Review r){
		Reviews revs = (Reviews) ctx.getAttribute("reviews");
		Date date = new Date();
		int id = Integer.parseInt(revs.getReviewList().get(revs.getReviewList().size()-1).getId());
		id +=1;
		r.setId(Integer.toString(id));
		r.setDate(date.toString());
		r.setUser("1");
		try {
			Reviews.writeReview(r);
			revs.getRevs().put(r.getId(), r);
			revs.getReviewList().add(r);
			ctx.setAttribute("reviews",revs);
			getReviews();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return "dodat rivju";
	}
	
	
	private Reviews getRevs(){
		Reviews revs = (Reviews) ctx.getAttribute("reviews");
		if(revs == null)
		{
			revs = new Reviews(ctx.getRealPath(""));
			ctx.setAttribute("reviews", revs);
		}
		return revs;
	}
	
	public Object getLastElement(final Collection c) {
	    final Iterator itr = c.iterator();
	    Object lastElement = itr.next();
	    while(itr.hasNext()) {
	        lastElement=itr.next();
	    }
	    return lastElement;
	}
	
}
