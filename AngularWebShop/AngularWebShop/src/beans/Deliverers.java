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
import java.util.UUID;

public class Deliverers {

private HashMap<String, Delivery> deliverers = new HashMap<String, Delivery>();
private ArrayList<Delivery> deliverersList = new ArrayList<Delivery>();
private static String path = "D:\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
//private static String path = "/home/student/git/WP2016/AngularWebShop/AngularWebShop/WebContent";

	public Deliverers() {
		this(path);
	}
	
	public Deliverers(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/deliverers.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readDeliverers(in);
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

	private void readDeliverers(BufferedReader in) {
		String line, id = "", name = "", description = "",country = " ",rate = "";
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
					country= st.nextToken().trim();
					rate = st.nextToken().trim();
				}
				Delivery delivery = new Delivery(id, name, description, country, Double.parseDouble(rate));
				deliverers.put(id, delivery);
				deliverersList.add(delivery);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void writeDelivery(Delivery d) throws IOException{
		String line="";
		line += d.getId() + ";";
		line += d.getName() + ";";
		line += d.getDescription() + ";";
		line += d.getCountry() + ";";
		line += d.getRate() + ";";
		
		File file = new File(path + "/deliverers.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}
	
	public static void deleteDelivery(String id) throws IOException{
		File file = new File(path + "/deliverers.txt");
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
	
	
	
	public HashMap<String, Delivery> getDeliverers(){
		return deliverers;
	}
	
	public Collection<Delivery> getValues() {
		return deliverers.values();
	}
	
	public Delivery getDeliverer(String id) {
		return deliverers.get(id);
	}

	public ArrayList<Delivery> getDeliverersList() {
		return deliverersList;
	}
}
