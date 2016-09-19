package beans;

public class Complaint {
	
	private String id;
	private String customerId;
	private String storeId;
	private String productId;
	private String description;


	private Complaint(){}
	
	public Complaint(String id,String customerId, String storeId, String productId, String description) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.storeId = storeId;
		this.productId = productId;
		this.description = description;
	}

	
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStoreId() {
		return storeId;
	}


	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}


	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
