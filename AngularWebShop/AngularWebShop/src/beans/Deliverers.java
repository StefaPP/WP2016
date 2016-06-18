package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Deliverers {

private HashMap<String, Delivery> deliverers = new HashMap<String, Delivery>();
private ArrayList<Delivery> deliverersList = new ArrayList<Delivery>();


	public Deliverers() {
		this("C:\\Users\\Bebica\\git\\WP2016\\README.md\\AngularWebShop\\AngularWebShop\\WebContent\\");
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

	/**
	 * Cita proizvode iz datoteke i smesta ih u asocijativnu listu proizvoda.
	 * Kljuc je id proizvoda.
	 *
	 *	this.name = name;
		this.description = description;
		this.country = country;
		this.rate = rate;
	 *
	 */
	
	
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


	/** Vraca kolekciju proizvoda. */
	public Collection<Delivery> getValues() {
		return deliverers.values();
	}
	

	/** Vraca proizvod na osnovu njegovog id-a. */
	public Delivery getDeliverer(String id) {
		return deliverers.get(id);
	}

	/** Vraca listu proizvoda. */
	public ArrayList<Delivery> getDeliverersList() {
		return deliverersList;
	}
}
