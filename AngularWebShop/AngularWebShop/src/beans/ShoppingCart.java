package beans;

import java.util.ArrayList;

/**
 * Reperezentuje korpu za kupovinu. Sadrzi vektor stavki (uredjeni par
 * (proizvod, kolicina)).
 */
public class ShoppingCart {
	
	private ArrayList<ShoppingCartItem> items;
	
	public ShoppingCart() {
		items = new ArrayList<ShoppingCartItem>();
	}

	public void addItem(Product product) {
		items.add(new ShoppingCartItem(product));
	}

	public ArrayList<ShoppingCartItem> getItems() {
		return items;
	}
	
	public double getTotal() {
		double retVal = 0;
		for (ShoppingCartItem item : items) {
			retVal += item.getTotal();
		}
		return retVal;
	}
}
