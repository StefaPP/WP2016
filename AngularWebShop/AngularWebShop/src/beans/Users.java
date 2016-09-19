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

public class Users {

	private HashMap<String, User> users = new HashMap<String, User>();
	private ArrayList<User> userList = new ArrayList<User>();
	private static String path = "C:\\Users\\Strefa\\git\\WP2016\\AngularWebShop\\AngularWebShop\\WebContent\\";
	public Users() {
		this(path);
	}

	public Users(String path) {
		BufferedReader in = null;
		try {
			File file = new File(path + "/users.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readUsers(in);
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
	private void readUsers(BufferedReader in) {
		String line, username = "", password = "", firstName = "", lastName = "", role = "", contact = "", email = "",
				address = "", country = "";
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					username = st.nextToken().trim();
					password = st.nextToken().trim();
					firstName = st.nextToken().trim();
					lastName = st.nextToken().trim();
					role = st.nextToken().trim();
					contact = st.nextToken().trim();
					email = st.nextToken().trim();
					address = st.nextToken().trim();
					country = st.nextToken().trim();
				}
				User user = new User(username, password, firstName, lastName, role, contact, email, address, country);
				users.put(username, user);
				userList.add(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeUser(User u) throws IOException {
		String line = "";
		line += u.getUsername() + ";";
		line += u.getPassword() + ";";
		line += u.getFirstName() + ";";
		line += u.getLastName() + ";";
		line += u.getRole() +  ";";
		line += u.getContact() + ";";
		line += u.getEmail() + ";";
		line += u.getAddress() + ";";
		line += u.getCountry() + ";";

		
		File file = new File(path + "/users.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
	}
	
	
	public static void deleteUser(String id) throws IOException {
		
		File file = new File(path + "/users.txt");
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
	

	public Collection<User> getValues() {
		return users.values();
	}
	
	public HashMap<String,User> getUsers() {
		return users;
	}

	public User getUser(String id) {
		return users.get(id);
	}
	
	public ArrayList<User> getUserList() {
		return userList;
	}
}
