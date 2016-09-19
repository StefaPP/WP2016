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

public class WishListFile {

	private HashMap<String, WishList> wishList = new HashMap<String, WishList>();
	private ArrayList<WishList> wishArrayList = new ArrayList<WishList>();
	private static String path = "C:\\Users\\Strefa\\git\\WP2016\\AngularWebShop\\AngularWebShop\\WebContent\\";

	public WishListFile() {
		this(path);
	}

	public WishListFile(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/wishList.txt");
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

	/*
	 * private String customerId; private String storeId; private String
	 * productId; private String deliveryId;
	 */

	private void readProducts(BufferedReader in) {
		String line, id = "", customerId = "", storeId = "", productId = "", deliveryId = "";

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

				}

				WishList wl = new WishList(id, customerId, storeId, productId);
				wishList.put(wl.getId(), wl);
				wishArrayList.add(wl);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void writeItem(WishList wl) throws IOException {
		String line = "";
		line += wl.getId() + ";";
		line += wl.getCustomerId() + ";";
		line += wl.getStoreId() + ";";
		line += wl.getProductId() + ";";

		File file = new File(path + "/wishList.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		out.append(line);
		out.newLine();
		out.close();
	}

	public static void deleteItem(String id) throws IOException {
		File file = new File(path + "/wishList.txt");
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

	public HashMap<String, WishList> getWishList() {
		return wishList;
	}

	public Collection<WishList> getValues() {
		return wishList.values();
	}

	public ArrayList<WishList> getWishArrayList() {
		return wishArrayList;
	}

	public void setWishArrayList(ArrayList<WishList> wishArrayList) {
		this.wishArrayList = wishArrayList;
	}

}
