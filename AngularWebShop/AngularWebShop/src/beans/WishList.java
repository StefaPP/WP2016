package beans;

public class WishList {
	
	private String id;
	private String customerId;
	private String storeId;
	private String productId;
	
	public WishList() {
		
	}
	
	public WishList(String id, String customerId, String storeId, String productId) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.storeId = storeId;
		this.productId = productId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	
	
}
