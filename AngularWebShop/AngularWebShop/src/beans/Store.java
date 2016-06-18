package beans;

public class Store {
	private String id;
	private String name;
	private String address;
	private String country;
	private String contact;
	private String email;
	private String seller;
	private String rating;
	private String review;

	public Store() {}
	
	public Store(String id, String name, String address, String country, String contact, String email, String seller,
			String rating, String review) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.country = country;
		this.contact = contact;
		this.email = email;
		this.seller = seller;
		this.rating = rating;
		this.review = review;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	
	
}
