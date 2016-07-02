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

public class BuyingHistory {

	private HashMap<String,Buying> buyingHistory = new HashMap<String,Buying>();
	private ArrayList<Buying> buyingHistoryList = new ArrayList<Buying>();
	private static String path = "D:\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";

	public  BuyingHistory() {
		this(path);
	}

	public BuyingHistory(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/buyingHistory.txt");
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
		String line, id="",customerId = "",storeId="",productId="",deliveryId="",totalPrice="";
		
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
					totalPrice = st.nextToken().trim();
				}
				Buying b = new Buying(id, customerId, storeId, productId, deliveryId,totalPrice);
				buyingHistory.put(b.getId(), b);
				buyingHistoryList.add(b);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void writeItem(Buying b) throws IOException{
		String line = "";
		line += b.getId() + ";";
		line += b.getCustomerId() + ";";
		line += b.getStoreId() + ";";
		line += b.getProductId() + ";";
		line += b.getDeliveryId() + ";";
		line += b.getTotalPrice() + ":";
		
		File file = new File(path + "/buyingHistory.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}
	
	
	public static void deleteItem(String id) throws IOException{
		File file = new File(path + "/shoppingList.txt");
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

	public HashMap<String,Buying> getHistory(){
		return buyingHistory;
	}
	
	public Collection<Buying> getValues() {
		return buyingHistory.values();
	}
	
	public ArrayList<Buying> getBuyingHistory() {
		return buyingHistoryList;
	}

	
}