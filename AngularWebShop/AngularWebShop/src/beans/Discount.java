package beans;

public class Discount {

	private String id;
	private String productId;
	private String storeId;
	private String startDate;
	private String endDate;
	private String discountRate;

	private Discount() {

	}

	public Discount(String id, String productId,String storeId, String startDate, String endDate, String discountRate) {
		super();
		this.id = id;
		this.productId = productId;
		this.storeId = storeId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.discountRate = discountRate;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

}
