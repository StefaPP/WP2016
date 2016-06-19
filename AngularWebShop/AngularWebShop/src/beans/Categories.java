package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Categories {
	
	private HashMap<String,Category> categories = new HashMap<>();
	private ArrayList<Category> categoryList = new ArrayList<>();
	
	
	public Categories() {
		this("C:\\Users\\Bebica\\git\\WP2016\\README.md\\AngularWebShop\\AngularWebShop\\WebContent\\");
	}
	
	
	public Categories(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/categories.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readCategories(in);
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

	private void readCategories(BufferedReader in) {
		String line, id = "", name = "", description = "";
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					name = st.nextToken().trim();
					description = st.nextToken().trim();
				}
				Category cat = new Category(name, description);
				categories.put(name, cat);
				categoryList.add(cat);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Collection<Category> getCategories() {
		return categories.values();
	}
	public void setCategories(HashMap<String, Category> categories) {
		this.categories = categories;
	}
	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	

}
