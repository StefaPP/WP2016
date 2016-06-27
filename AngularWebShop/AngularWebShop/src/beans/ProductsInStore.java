package beans;

public class ProductsInStore {
	private String storeId;
	private String productId;
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
	public ProductsInStore(String storeId, String productId) {
		super();
		this.storeId = storeId;
		this.productId = productId;
	}
	
	
	public ProductsInStore(){
		
	}
}
