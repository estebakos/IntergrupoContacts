
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("/users")
public class UserRestService {

	public static String readFile(InputStream stream) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private boolean writeFile(String json) {
		File file = new File(System.getProperty("user.home") + "/Desktop/users.json");
		BufferedWriter bw = null;
		try {
			Writer w = new OutputStreamWriter(new FileOutputStream(file));
			bw = new BufferedWriter(w);
			bw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				bw.close();
			} catch (IOException ex) {
				ex.printStackTrace();

				return false;
			}
		}
		return true;
	}
	@GET
	@Path("/updatepass")
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse updatePassword(@QueryParam("username") String username,
			@QueryParam("oldPassword") String oldPassword, @QueryParam("newPassword") String newPassword) {
		UserResponse response = new UserResponse();
		if (validateUser(username, oldPassword)) {
			User user = getUserByUsername(username, oldPassword);
			if (user != null) {
				user.setPassword(newPassword);
				if (writeFile(new Gson().toJson(user))) {
					response.setResponse(1);
				} else {
					response.setResponse(2);
				}
			} else {
				response.setResponse(2);
			}
		} else {
			response.setResponse(2);
		}
		return response;
	}

	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponse login(@QueryParam("username") String username, @QueryParam("password") String password) {
		UserResponse response = new UserResponse();
		if (validateUser(username, password)) {
			response.setResponse(1);
		} else {
			response.setResponse(2);
		}
		return response;
	}

	// @GET
	// @Path("/number")
	// @Produces(MediaType.APPLICATION_JSON)
	// public NumberCountries getCountryCount() {
	// List<Country> listOfCountries = new ArrayList<Country>();
	// listOfCountries = createCountryList();
	//
	// NumberCountries countries = new NumberCountries(listOfCountries.size());
	// return countries;
	// }

	private User getUserByUsername(String username, String password) {
		
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(System.getProperty("user.home") + "/Desktop/users.json"));
			String jsonData = readFile(inputStream);
			User user = new Gson().fromJson(jsonData, User.class);
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}

	private boolean validateUser(String username, String password) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(System.getProperty("user.home") + "/Desktop/users.json"));
			String jsonData = readFile(inputStream);
			User user = new Gson().fromJson(jsonData, User.class);
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
