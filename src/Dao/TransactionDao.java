package Dao;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;

import Entities.City;
import Entities.Country;
import Entities.Department;
import Entities.User;

public interface TransactionDao {
	// obtiene un arreglo de datos con todas las entidades para mostrar
	List<Object> getAllData(Connection connection) throws Exception;

	// almacena el usuario nuevo
	void saveUser(Connection connection, User user) throws SQLException;

	// busca un usuario en la db
	Boolean findUser(Connection connection, User user) throws SQLException;

	// almacena el nuevo pais
	void saveCountry(Connection connection, Country country) throws SQLException;
	

	// almacena el nuevo department
	void saveDepartment(Connection connection, Department department) throws SQLException;


	// almacena una nueva ciudad
	void saveCity(Connection connection, City city) throws SQLException;

}
