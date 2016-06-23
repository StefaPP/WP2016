package beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Reviews {
	
	private HashMap<String,Review> reviews = new HashMap<String , Review>();
	private ArrayList<Review> reviewList = new ArrayList<Review>();
	private static String path = "C:\\Users\\Strefa\\Desktop\\WP\\AngularWebShop\\AngularWebShop\\WebContent\\";
	
	public Reviews() {
		this(path);
	}

	public Reviews(String path) {
		
		BufferedReader in = null;
		try {
			File file = new File(path + "/reviews.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			readReviews(in);
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

	private void readReviews(BufferedReader in) {
		String line,id="", user = "",date = "",rating="",comment="",productId = "";
		
		StringTokenizer st;
		try {
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					id = st.nextToken().trim();
					user = st.nextToken().trim();
					date = st.nextToken().trim();
					rating = st.nextToken().trim();
					comment = st.nextToken().trim();
					productId = st.nextToken().trim();
				}
			Review rev = new Review(id, user, date, rating, comment,productId);
				reviews.put(id, rev);
				reviewList.add(rev);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeReview(Review r) throws IOException {
		String line = "";
		line += r.getId() + ";";
		line += r.getUser() + ";";
		line += r.getDate() + ";";
		line += r.getRating() + ";";
		line += r.getComment() + ";";
		line += r.getProductId() + ";";
		
		File file = new File(path + "/reviews.txt");
		BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
		out.append(line);
		out.newLine();
		out.close();
		
	}
	

	public Collection<Review> getReviews() {
		return reviews.values();
	}

	public ArrayList<Review> getReviewList() {
		return reviewList;
	}


}


