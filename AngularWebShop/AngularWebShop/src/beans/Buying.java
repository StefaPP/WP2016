package beans;

public class Buying {
	
	private String id;
	private String customerId;
	private String storeId;
	private String productId;
	private String deliveryId;
	private String date;
	private String totalPrice;
	
	public Buying(){
		
	}
	
	public Buying(String id, String customerId, String storeId, String productId, String deliveryId,String date,
			String totalPrice) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.storeId = storeId;
		this.productId = productId;
		this.deliveryId = deliveryId;
		this.date = date;
		this.totalPrice = totalPrice;
	}
	
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
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
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


}
