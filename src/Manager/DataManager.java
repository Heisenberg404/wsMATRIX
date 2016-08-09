package Manager;

import java.util.List;

import com.mysql.jdbc.Connection;

import Entities.City;
import Entities.Country;
import Entities.Department;
import Entities.User;

public interface DataManager {

	// obtiene un arreglo de datos con todas las entidades para mostrar
	List<Object> getAllData() throws Exception;

	// almacena un nuevo usuario
	void saveUser(User user) throws Exception;

	// almacena un nuevo Country
	void saveCountry(Country country) throws Exception;

	// almacena un nuevo Department
	void saveDepartment(Department department) throws Exception;

	// almacena un nuevo City
	void saveCity(City city) throws Exception;

	// comprueba que un usuario exista.
	Boolean findUser(User user) throws Exception;
}
