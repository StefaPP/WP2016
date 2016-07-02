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

public class Stores {

	private HashMap<String, Store> stores = new HashMap<String, Store>();
	private ArrayList<Store> storeList = new ArrayList<Store>();
	//private static String path = "D:\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
	private static String path = "/home/student/git/WP2016/AngularWebShop/AngularWebShop/WebContent/";
	//private static String path = "/home/student/git/WP2016/AngularWebShop/AngularWebShop/WebContent";
	
	public Stores() {
		this(path);
	}

public Stores(String path) {
	BufferedReader in = null;
	try {
		File file = new File(path + "/stores.txt");
		in = new BufferedReader(new FileReader(file));
		readStores(in);
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		if ( in != null ) {
			try {
				in.close();
			}
			catch (Exception e) { }
		}
	}
}


private void readStores(BufferedReader in) {
	String line, id = "", name = "", address = "",
			country = "",contact="",email="",
			seller="",rating="",review="";
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
				address = st.nextToken().trim();
				country = st.nextToken().trim();
				contact = st.nextToken().trim();
				email = st.nextToken().trim();
				seller = st.nextToken().trim();
				rating = st.nextToken().trim();
				review = st.nextToken().trim();
			}
			Store store = new Store(id, name, address, country, contact, email, seller, rating, review);
			stores.put(id, store);
			storeList.add(store);
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}
}

public static void writeStore(Store s) throws IOException {
	String line = "";
	line += s.getId() + ";";
	line += s.getName() + ";";
	line += s.getAddress() + ";";
	line += s.getCountry() + ";";
	line += s.getContact() + ";";
	line += s.getEmail() + ";";
	line += s.getSeller() + ";";
	line += " " + ";";
 	line += " " + ";";

	
	File file = new File(path + "/stores.txt");
	BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
	out.append(line);
	out.newLine();
	out.close();
}

public static void deleteStore(String id) throws IOException {
	
	File file = new File(path + "/stores.txt");
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
	System.out.println(temp);
	file.delete();
	temp.renameTo(file);
}

public HashMap<String,Store> getStores(){
	return stores;
}

public Collection<Store> getValues() {
	return stores.values();
}

/** Vraca prodavnicu na osnovu njenog id-a. */
public Store getStore(String id) {
	return stores.get(id);
}

/** Vraca listu prodavnica. */
public ArrayList<Store> getStoreList() {
	return storeList;
}

}
