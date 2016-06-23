package beans;

public class Review {
	
	private String id;
	private String user;
	private String date;
	private String rating;
	private String comment;
	private String productId;
	
	public Review() {}
	
	public Review(String id, String user, String date, String rating, String comment,String productId) {
		super();
		this.id = id;
		this.user = user;
		this.date = date;
		this.rating = rating;
		this.comment = comment;
		this.productId = productId;
	}
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
