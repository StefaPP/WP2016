package beans;

/** Reprezentuje stavku u korpi. Stavku cine proizvod i kolicina. */
public class ShoppingCartItem {

	private Product product;
	
	public ShoppingCartItem(Product p) {
		this.product = p;
	}
	public void setProduct(Product p) {
		product = p;
	}
	public Product getProduct() {
		return product;
	}
	public double getTotal() {
		return product.getPrice();
	}

}
