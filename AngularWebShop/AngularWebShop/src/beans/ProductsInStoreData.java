package beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductsInStoreData {
	
	private ArrayList<ProductsInStore> products = new ArrayList<ProductsInStore>();
	private static String path = "D:\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
	
	public ProductsInStoreData() {
		this(path);
	}
	
	public ProductsInStoreData(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/productsInStore.txt");
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
		String line="",storeId="",productId="";
		

		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					storeId = st.nextToken().trim();
					productId = st.nextToken().trim();
					
				}
				ProductsInStore pis = new ProductsInStore(storeId, productId);
				products.add(pis);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeProduct(ProductsInStore p) throws IOException{
		String line="";
		line += p.getStoreId() + ";";
		line += p.getProductId() + ";";

		
		File file = new File(path + "/productsInStore.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}

	public ArrayList<ProductsInStore> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<ProductsInStore> products) {
		this.products = products;
	}

}
