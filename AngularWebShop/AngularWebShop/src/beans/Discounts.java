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

public class Discounts {
	private HashMap<String, Discount> discounts = new HashMap<String, Discount>();
	private ArrayList<Discount> discountList = new ArrayList<Discount>();
	private static String path = "C:\\Users\\Strefa\\git\\WP2016\\AngularWebShop\\AngularWebShop\\WebContent\\";

	public Discounts() {
		this(path);
	}

	public Discounts(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/discounts.txt");
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
		String line, id = "", productId = "", storeId = "", startDate = "", endDate = "", discountRate = "";

		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					productId = st.nextToken().trim();
					storeId = st.nextToken().trim();
					startDate = st.nextToken().trim();
					endDate = st.nextToken().trim();
					discountRate = st.nextToken().trim();
				}

				Discount disc = new Discount(id, productId, storeId, startDate, endDate, discountRate);
				discounts.put(disc.getId(), disc);
				discountList.add(disc);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	

	public static void writeItem(Discount d) throws IOException{
		String line = "";
		line += d.getId() + ";";
		line += d.getProductId() + ";";
		line += d.getStoreId() + ";";
		line += d.getStartDate() + ";";
		line += d.getEndDate() + ";";
		line += d.getDiscountRate() + ";";
		
		File file = new File(path + "/discounts.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}
	
	public static void deleteItem(String id) throws IOException{
		File file = new File(path + "/discounts.txt");
		File temp = new File(path + "/temp.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		String line;
		synchronized (Store.class) { 
			while((line = reader.readLine()) != null){
				if(!line.startsWith(id)){
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
	
	public HashMap<String, Discount> getDiscounts() {
		return discounts;
	}

	
	public Collection<Discount> getValues() {
		return discounts.values();
	}

	public ArrayList<Discount> getDiscountList() {
		return discountList;
	}

	
}
