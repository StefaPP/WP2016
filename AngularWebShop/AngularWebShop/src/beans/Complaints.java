package beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Complaints {

	
	
	private HashMap<String,Complaint> complaints = new HashMap<String,Complaint>();
	private ArrayList<Complaint> complaintsList = new ArrayList<Complaint>();
	
	private static String path = "C:\\Users\\Bebica\\Desktop\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
	
	public  Complaints() {
		this(path);
	}

	public Complaints(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/complaints.txt");
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
		String line, id="",customerId = "",storeId="",productId="",description="";
		
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
					description = st.nextToken().trim();
				}
				Complaint c = new Complaint(id, customerId, storeId, productId,description);
				complaints.put(c.getId(), c);
				complaintsList.add(c);
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void writeItem(Complaint c) throws IOException{
		String line = "";
		line += c.getId() + ";";
		line += c.getCustomerId() + ";";
		line += c.getStoreId() + ";";
		line += c.getProductId() + ";";
		line += c.getDescription() + ";";

		File file = new File(path + "/complaints.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}
	
	public static void deleteItem(String id) throws IOException{
		File file = new File(path + "/complaints.txt");
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

	public HashMap<String, Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(HashMap<String, Complaint> complaints) {
		this.complaints = complaints;
	}

	public ArrayList<Complaint> getComplaintsList() {
		return complaintsList;
	}

	public void setComplaintsList(ArrayList<Complaint> complaintsList) {
		this.complaintsList = complaintsList;
	}
	
	
	
}
