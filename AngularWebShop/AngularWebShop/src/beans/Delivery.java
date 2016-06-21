package beans;

public class Delivery {
	private String id;
	private String name;
	private String description;
	private String country;
	private double rate;
	
	public Delivery(){}
	
	public Delivery(String id, String name, String description, String country, double rate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.country = country;
		this.rate = rate;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}	
