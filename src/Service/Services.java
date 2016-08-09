package Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import Entities.City;
import Entities.Country;
import Entities.Department;
import Entities.User;
import Manager.DataManagerImpl;
import Util.Crypter;



@Path("api")
public class Services {

	DataManagerImpl DataManager = new DataManagerImpl();
	Crypter crypto = new Crypter();

	@GET
	@Path("alldata")
	@Produces("application/json")

	public Response getAllData() throws Exception {
		List<Object> data = DataManager.getAllData();
		String allData = new JSONArray(data).toString();
		return Response.status(200).entity(allData).build();
	}

	@POST
	@Path("users")
	public Response registerUser(@FormParam("username") String username, @FormParam("pass") String password) throws Exception {
		if (username == null || username.isEmpty())
			return Response.status(400).entity("The username is required").build();
		if (password == null || password.isEmpty())
			return Response.status(400).entity("The password is required").build();
		else {
			User user = new User();
			user.setUsername(username);
			user.setPass(crypto.crypto(password));
			DataManager.saveUser(user);
			return Response.status(201).entity("The user " + username + " was saved successfull").build();

		}
	}

	@POST
	@Path("login")
	public Response logUser(@FormParam("username") String username, @FormParam("pass") String password) throws Exception {
		if (username == null || username.isEmpty())
			return Response.status(400).entity("The username is required").build();
		if (password == null || password.isEmpty())
			return Response.status(400).entity("The password is required").build();
		else {
			User user = new User();
			user.setUsername(username);
			user.setPass(crypto.crypto(password));

			if (DataManager.findUser(user))
				return Response.status(200).entity("The user " + username + " was found correctly").build();
			else
				return Response.status(403).entity("The user " + username + " not exist").build();

		}
	}

	@POST
	@Path("Countries")
	public Response addCountry(@FormParam("nameCountry") String name) throws Exception {
		if (name == null || name.isEmpty())
			return Response.status(400).entity("The name").build();
		else {
			Country country = new Country();
			country.setName(name);
			DataManager.saveCountry(country);
			return Response.status(201).entity("The country:  " + name + " was saved successfull").build();

		}
	}

	@POST
	@Path("departments")
	public Response addDepartment(@FormParam("nameDep") String name, @FormParam("countryId") int id_country) throws Exception {
		if (name == null || name.isEmpty())
			return Response.status(400).entity("The name").build();
		if (id_country == 0)
			return Response.status(400).entity("The name").build();
		else {
			Department department = new Department();
			department.setName(name);
			department.getCountry().setId(id_country);
			DataManager.saveDepartment(department);
			return Response.status(201).entity("The department:  " + name + " was saved successfull").build();

		}
	}

	@POST
	@Path("cities")
	public Response addCity(@FormParam("nameCity") String name, @FormParam("DepartmentId") int id_dep) throws Exception {
		if (name == null || name.isEmpty())
			return Response.status(400).entity("The name").build();
		if (id_dep == 0)
			return Response.status(400).entity("The name").build();
		else {
			City city= new City();
			city.setName(name);
			city.getDepartment().setId(id_dep);
			DataManager.saveCity(city);
			return Response.status(201).entity("The City:  " + name + " was saved successfull").build();

		}
	}
	
	@POST
	@Path("file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		
		String uploadedFileLocation = "d://uploaded/" + fileDetail.getFileName();
		writeToFile(uploadedInputStream, uploadedFileLocation);
		
		return Response.status(200).entity("Saved").build();
		
	}
	
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File (uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != 1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
