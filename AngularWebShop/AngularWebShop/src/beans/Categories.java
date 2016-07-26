package beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Categories {
	
	private HashMap<String,Category> categories = new HashMap<String,Category>();
	private ArrayList<Category> categoryList = new ArrayList<>();
	private static String path = "C:\\Users\\Bebica\\Desktop\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
	//private static String path = "/home/student/git/WP2016/AngularWebShop/AngularWebShop/WebContent";
	
	public Categories() {
		this(path);
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
	
	public static void writeCategory(Category c) throws IOException{
		String line="";
		line += c.getName() + " ;";
		line += c.getDescription() + " ;";
		File file = new File(path + "/categories.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}
	
	public static void deleteCategory(String name) throws IOException{
		File file = new File(path + "/categories.txt");
		File temp = new File(path + "/temp.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		String line;
		synchronized (Store.class) { 
			while((line = reader.readLine()) != null){
				if(!line.startsWith(name)){
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
	
	public static void updateCategory(String name,Category newCategory) throws IOException{
		File file = new File(path + "/categories.txt");
		File temp = new File(path + "/temp.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp));
		String line;
		synchronized (Store.class) { 
			while((line = reader.readLine()) != null){
				if(!line.startsWith(name)){
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
	
	
	
	public HashMap<String, Category> getCats() {
		return categories;
	}

	public Collection<Category> getCategories() {
		return categories.values();
	}
	public ArrayList<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}
	
	

}
