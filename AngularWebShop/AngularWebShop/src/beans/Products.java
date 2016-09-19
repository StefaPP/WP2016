package beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;


public class Products {
	private HashMap<String, Product> products = new HashMap<String, Product>();
	private ArrayList<Product> productList = new ArrayList<Product>();
	private static String path = "C:\\Users\\Strefa\\git\\WP2016\\AngularWebShop\\AngularWebShop\\WebContent\\";

	public Products() {

		this(path);
	}

	public Products(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/products.txt");
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

	private void readProducts(BufferedReader in) {
		String line, id = "", name = "", description = "", price = "", size = "", weight = "", origin = "",
				brandName = "", category = "", image = "", storeId = "", rating = "", review = "", lager = "",
				color = "";

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
					description = st.nextToken().trim();
					price = st.nextToken().trim();
					size = st.nextToken().trim();
					weight = st.nextToken().trim();
					origin = st.nextToken().trim();
					brandName = st.nextToken().trim();
					category = st.nextToken().trim();
					image = st.nextToken().trim();
					storeId = st.nextToken().trim();
					rating = st.nextToken().trim();
					review = st.nextToken().trim();
					lager = st.nextToken().trim();
					color = st.nextToken().trim();

				}
				Product product = new Product(id, name, description, Double.parseDouble(price),
						Double.parseDouble(size), Double.parseDouble(weight), origin, brandName, category, image,
						storeId, rating, review, Integer.parseInt(lager), color);
				products.put(id, product);
				productList.add(product);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void writeProduct(Product p) throws IOException {
		String line = "";
		line += p.getId() + ";";
		line += p.getName() + ";";
		line += p.getDescription() + ";";
		line += p.getPrice() + ";";
		line += p.getSize() + ";";
		line += p.getWeight() + ";";
		line += p.getOrigin() + ";";
		line += p.getBrandName() + ";";
		line += p.getCategory() + ";";
		line += p.getImage() + ";";
		line += p.getStoreId() + ";";
		line += p.getRating() + " ;";
		line += p.getReview() + " ;";
		line += p.getLager() + ";";
		line += p.getColor() + ";";

		File file = new File(path + "/products.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		out.append(line);
		out.newLine();
		out.close();
	}

	public static void updateProduct(Product p) throws IOException {

		deleteProduct(p.getId());

		/*
		 * String line, id = "", name = "", price = "", size = "", weight = "",
		 * origin = "", brandName = "", category = "", image = "", storeId = "",
		 * rating = "", review = "", lager = "";
		 * 
		 * 
		 * File file = new File(path + "/products.txt"); File temp = new
		 * File(path + "/temp.txt");
		 * 
		 * BufferedReader reader = new BufferedReader(new FileReader(file));
		 * BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		 * 
		 * String line;
		 * 
		 * StringTokenizer st; st = new StringTokenizer(line, ";");
		 * 
		 * while (st.hasMoreTokens()) { id = st.nextToken().trim(); name =
		 * st.nextToken().trim(); price = st.nextToken().trim(); size =
		 * st.nextToken().trim(); weight = st.nextToken().trim(); origin =
		 * st.nextToken().trim(); brandName = st.nextToken().trim(); category =
		 * st.nextToken().trim(); image = st.nextToken().trim(); storeId =
		 * st.nextToken().trim(); rating = st.nextToken().trim(); review =
		 * st.nextToken().trim(); lager = st.nextToken().trim(); }
		 */
	}

	public static void deleteProduct(String id) throws IOException {
		File file = new File(path + "/products.txt");
		File temp = new File(path + "/temp.txt");

		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		String line;
		synchronized (Store.class) {
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith(id)) {
					writer.write(line);
					writer.newLine();
				}

			}
			reader.close();
			writer.close();
		}
		file.delete();
		temp.renameTo(file);

	}

	public HashMap<String, Product> getProducts() {
		return products;
	}

	public Collection<Product> getValues() {
		return products.values();
	}

	public Product getProduct(String id) {
		return products.get(id);
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}
}
