package beans;

/** Reprezentuje jedan proizvod. Cuva se id, naziv i jedinicna cena. */
public class Product {

	private String id;
	private String name;
	private String description;
	private double price;
	private double size;
	private double weight;
	private String origin;
	private String brandName;
	private String category;
	private String image;
	private String storeId;
	private String rating;
	private String review;
	private int lager;
	private String color;

	/** Koristi se samo za AngularJS */
	private int count;

	public Product() {
	}

	public Product(String id, String name,String description ,double price, double size, double weight, String origin, String brandName,
			String category, String image, String storeId, String rating, String review, int lager,String color) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.size = size;
		this.weight = weight;
		this.origin = origin;
		this.brandName = brandName;
		this.category = category;
		this.image = image;
		this.storeId = storeId;
		this.rating = rating;
		this.review = review;
		this.lager = lager;
		this.color = color;
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

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
