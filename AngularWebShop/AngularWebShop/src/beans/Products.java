package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Klasa koja reprezentuje spisak raspolozivih proizvoda za kupovinu. Spisak
 * proizvoda se inicijalizuje iz tekstualne datoteke, koja je oblika: <br>
 * id;naziv;jedinicna cena
 */
public class Products {
	private HashMap<String, Product> products = new HashMap<String, Product>();

	public void setProducts(HashMap<String, Product> products) {
		this.products = products;
	}

	private ArrayList<Product> productList = new ArrayList<Product>();

	public Products() {
		
		this("C:\\Users\\Strefa\\Desktop\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\");
	}

	public Products(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/products.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readProducts(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Cita proizvode iz datoteke i smesta ih u asocijativnu listu proizvoda.
	 * Kljuc je id proizvoda.
	 *
	 *
	 * private String id; private String name; private double price; private
	 * double size; public double weight; private String origin; private String
	 * brandName; private String category; private String image; private String
	 * video; private String rating; private String review; private int lager;
	 */
	private void readProducts(BufferedReader in) {
		String line, id = "", name = "", price = "", size = "", weight = "", origin = "", brandName = "", category = "",
				image = "", video = "", rating = "", review = "", lager = "";

		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					name = st.nextToken().trim();
					price = st.nextToken().trim();
					size = st.nextToken().trim();
					weight = st.nextToken().trim();
					origin = st.nextToken().trim();
					brandName = st.nextToken().trim();
					category = st.nextToken().trim();
					image = st.nextToken().trim();
					video = st.nextToken().trim();
					rating = st.nextToken().trim();
					review = st.nextToken().trim();
					lager = st.nextToken().trim();

				}
				Product product = new Product(id, name, Double.parseDouble(price), Double.parseDouble(size), Double.parseDouble(weight), origin, brandName, category, image, video,
						rating, review, Integer.parseInt(lager));
				products.put(id, product);
				productList.add(product);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Vraca kolekciju proizvoda. */
	public Collection<Product> getValues() {
		return products.values();
	}

	public HashMap<String, Product> getProducts() {
		return (HashMap<String, Product>) products.values();
	}

	/** Vraca proizvod na osnovu njegovog id-a. */
	public Product getProduct(String id) {
		return products.get(id);
	}

	/** Vraca listu proizvoda. */
	public ArrayList<Product> getProductList() {
		return productList;
	}
}
