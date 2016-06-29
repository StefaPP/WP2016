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

public class ShoppingListFile {
	
	private HashMap<String,ShoppingList> shoppingList = new HashMap<String,ShoppingList>();
	private ArrayList<ShoppingList> shoppingArrayList = new ArrayList<ShoppingList>();
	//private static String path = "D:\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
	private static String path = "/home/student/git/WP2016/AngularWebShop/AngularWebShop/WebContent";
	
	public ShoppingListFile()
	{
		this(path);
	}

	public ShoppingListFile(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/shoppingList.txt");
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

/*	private String customerId;
	private String storeId;
	private String productId;
	private String deliveryId;*/
	
	private void readProducts(BufferedReader in) {
		String line, id="",customerId = "",storeId="",productId="",deliveryId="";
		
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					customerId = st.nextToken().trim();
					storeId = st.nextToken().trim();
					productId = st.nextToken().trim();
					deliveryId = st.nextToken().trim();

				}
				ShoppingList sp = new ShoppingList(id, customerId, storeId, productId, deliveryId);
				shoppingList.put(sp.getId(), sp);
				shoppingArrayList.add(sp);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void writeItem(ShoppingList sp) throws IOException{
		String line = "";
		line += sp.getId() + ";";
		line += sp.getCustomerId() + ";";
		line += sp.getStoreId() + ";";
		line += sp.getProductId() + ";";
		line += sp.getDeliveryId() + ";";
		
		File file = new File(path + "/shoppingList.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}

	public HashMap<String, ShoppingList> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(HashMap<String, ShoppingList> shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	public Collection<ShoppingList> getValues() {
		return shoppingList.values();
	}

	public ArrayList<ShoppingList> getShoppingArrayList() {
		return shoppingArrayList;
	}

	public void setShoppingArrayList(ArrayList<ShoppingList> shoppingArrayList) {
		this.shoppingArrayList = shoppingArrayList;
	}
		
	
}
