package beans;

/** Reprezentuje jedan proizvod. Cuva se id, naziv i jedinicna cena. */
public class Product {

	private String id;
	private String name;
	private double price;
	private double size;
	public double weight;
	private String origin;
	private String brandName;
	private String category;
	private String image;
	private String video;
	private String rating;
	private String review;
	private int lager;
	
	/** Koristi se samo za AngularJS */

	
	private int count;

	public Product() {
		this.count = 1;
	}
	
	public Product(String id, String name, double price) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = 1;
	}
	
	
	public void setId(String i) {
		id = i;
	}

	public String getId() {
		return id;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public void setPrice(double p) {
		price = p;
	}

	public double getPrice() {
		return price;
	}

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ "]";
	}

	public Product(String id, String name, double price, double size, double weight, String origin, String brandName,
			String category, String image, String video, String rating, String review, int lager) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.size = size;
		this.weight = weight;
		this.origin = origin;
		this.brandName = brandName;
		this.category = category;
		this.image = image;
		this.video = video;
		this.rating = rating;
		this.review = review;
		this.lager = lager;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
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

	public int getLager() {
		return lager;
	}

	public void setLager(int lager) {
		this.lager = lager;
	}
	
	
	
}
