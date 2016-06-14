package beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Users 	{
	
private HashMap<String, User> users = new HashMap<String, User>();
private ArrayList<User> userList = new ArrayList<User>();

public Users() {
	this("D:\\apache\\webapps\\AngularWebShop");
}

public Users(String path) {
	BufferedReader in = null;
	try {
		File file = new File(path + "/users.txt");
		System.out.println(file.getCanonicalPath());
		in = new BufferedReader(new FileReader(file));
		readProducts(in);
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
 */
private void readProducts(BufferedReader in) {
	String line, username = "", password = "", firstName = "",
			lastName = "",role="",contact="",
			email="",address="",country="";
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

public Collection<User> getValues() {
	return users.values();
}

/** Vraca proizvod na osnovu njegovog id-a. */
public User getUser(String id) {
	return users.get(id);
}

/** Vraca listu proizvoda. */
public ArrayList<User> getUserList() {
	return userList;
}
}
