package beans;

public class Category {
	String name;
	String description;
	Category subcategory;
	
	public Category() {}
	
	public Category(String name, String description,Category sub) {
		super();
		this.name = name;
		this.description = description;
		this.subcategory = sub;
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

	public Category getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Category subcategory) {
		this.subcategory = subcategory;
	}
	
	
	
	
}
