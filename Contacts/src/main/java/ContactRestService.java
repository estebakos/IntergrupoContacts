
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

@Path("/contacts")
public class ContactRestService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> getContacts() {
		List<Contact> listOfContacts = new ArrayList<Contact>();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contacts.json");
		String jsonData = readFile(inputStream);
		JSONArray jarr;
		try {
			jarr = new JSONArray(jsonData);
			for (int i = 0; i < jarr.length(); i++) {
				Contact contact = new Gson().fromJson(jarr.getJSONObject(i).toString(), Contact.class);
				listOfContacts.add(contact);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return listOfContacts;
	}

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

	@GET
	@Path("{id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Contact getCountryById(@PathParam("id") int id) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("contacts.json");
		String jsonData = readFile(inputStream);
		JSONArray jarr;
		try {
			jarr = new JSONArray(jsonData);
			for (int i = 0; i < jarr.length(); i++) {
				Contact contact = new Gson().fromJson(jarr.getJSONObject(i).toString(), Contact.class);
				if(contact.getId() == id)
				{
					return contact;
				}				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
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

	// Utiliy method to create country list.
	public List<Contact> createContacts() {
		Contact estebanContact = new Contact();
		estebanContact.setAlias("estebakos");
		estebanContact.setId(1);
		estebanContact.setCity("Medellin");
		estebanContact.setCountry("Colombia");
		estebanContact.setLastName("Marin");
		estebanContact.setName("Esteban");
		estebanContact.setProfileImage(
				"http://vignette3.wikia.nocookie.net/blazblue/images/9/92/Ragna_the_Bloodedge_(Chibi).png/revision/latest?cb=20151115031624&path-prefix=es");
		estebanContact.setPhoneNumber("3175030999");
		estebanContact.setState(1);

		Contact alejandro = new Contact();
		alejandro.setAlias("alejorius");
		alejandro.setId(2);
		alejandro.setCity("Medellin");
		alejandro.setCountry("Colombia");
		alejandro.setLastName("Villa");
		alejandro.setName("Alejandro");
		alejandro.setProfileImage(
				"http://vignette3.wikia.nocookie.net/blazblue-fandom/images/9/92/Ragna_the_Bloodedge_(Chibi).png/revision/latest?cb=20140424215659");
		alejandro.setPhoneNumber("3207564738");
		alejandro.setState(2);

		Contact erikaContact = new Contact();
		erikaContact.setAlias("flaquerika");
		erikaContact.setId(3);
		erikaContact.setCity("Itagui");
		erikaContact.setCountry("Colombia");
		erikaContact.setLastName("Bedoya");
		erikaContact.setName("Erika");
		erikaContact.setProfileImage(
				"http://vignette4.wikia.nocookie.net/blazblue/images/f/f9/Nu-13_(Chibi).png/revision/latest?cb=20120116194033");
		erikaContact.setPhoneNumber("3165736674");
		erikaContact.setState(3);

		Contact gloriaContact = new Contact();
		gloriaContact.setAlias("pato");
		gloriaContact.setId(1);
		gloriaContact.setCity("Paris");
		gloriaContact.setCountry("Francia");
		gloriaContact.setLastName("Betancur");
		gloriaContact.setName("Gloria");
		gloriaContact.setProfileImage("http://www.fightersgeneration.com/nx7/char/blazblue-cp-chibi-kokonoe.png");
		gloriaContact.setPhoneNumber("3215039873");
		gloriaContact.setState(4);

		List<Contact> listContacts = new ArrayList<Contact>();
		listContacts.add(estebanContact);
		listContacts.add(erikaContact);
		listContacts.add(alejandro);
		listContacts.add(gloriaContact);
		return listContacts;
	}
}
