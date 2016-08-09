package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import Entities.City;
import Entities.Country;
import Entities.Department;
import Entities.User;

public class TransactionDaoJdbc implements TransactionDao {
	List<Object> alldata = new ArrayList<>();
	List<City> allcities = new ArrayList<>();
	List<Department> allDeps = new ArrayList<>();
	List<Country> allcountries = new ArrayList<>();
	List<Object> allData = new ArrayList<>();
	List<User> allusers = new ArrayList<>();
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	@Override
	public List<Object> getAllData(Connection connection) throws SQLException {

		try {

			alldata.add(getFullCountries(connection));
			alldata.add(getFullDepartments(connection));
			alldata.add(getFullCities(connection));

		} catch (Exception e) {
			e.printStackTrace();
		}
		connection.close();
		return alldata;
	}

	private List<Country> getFullCountries(Connection connection) throws SQLException {
		preparedStatement = null;
		resultSet = null;
		preparedStatement = (PreparedStatement) connection.prepareStatement("select * from countries");

		try {
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Country country = new Country();
				country.setId(resultSet.getInt("id"));
				country.setName(resultSet.getString("name"));
				allcountries.add(country);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allcountries;
	}

	private List<Department> getFullDepartments(Connection connection) throws SQLException {
		preparedStatement = null;
		resultSet = null;
		preparedStatement = (PreparedStatement) connection.prepareStatement("select * from departments");

		try {
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Department dep = new Department();
				dep.setId(resultSet.getInt("id"));
				dep.setName(resultSet.getString("name"));
				dep.getCountry().setId(resultSet.getInt("country_id"));
				allDeps.add(dep);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allDeps;
	}

	private List<City> getFullCities(Connection connection) throws SQLException {
		preparedStatement = null;
		resultSet = null;
		preparedStatement = (PreparedStatement) connection.prepareStatement("select * from cities");

		try {
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				City city = new City();
				city.setId(resultSet.getInt("id"));
				city.setName(resultSet.getString("name"));
				city.getDepartment().setId(resultSet.getInt("department_id"));
				allcities.add(city);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return allcities;
	}

	@Override
	public void saveUser(Connection connection, User user) throws SQLException {

		preparedStatement = null;
		resultSet = null;
		int index = 1;
		preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO users (username, pass) VALUES (?,?)");
		preparedStatement.setString(index++, user.getUsername());
		preparedStatement.setString(index++, user.getPass());
		try {
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

	}

	@Override
	public Boolean findUser(Connection connection, User user) throws SQLException {
		Boolean isValid = false;
		preparedStatement = null;
		resultSet = null;
		int index = 1;
		preparedStatement = (PreparedStatement) connection.prepareStatement("SELECT id, username, pass from users WHERE username = ? AND pass = ?");
		preparedStatement.setString(index++, user.getUsername());
		preparedStatement.setString(index++, user.getPass());

		try {
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				isValid = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return isValid;
	}

	@Override
	public void saveCountry(Connection connection, Country country) throws SQLException {
		preparedStatement = null;
		resultSet = null;
		int index = 1;
		preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO countries (name) VALUES (?)");
		preparedStatement.setString(index++, country.getName());
		try {
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		
	}

	@Override
	public void saveDepartment(Connection connection, Department department) throws SQLException {
		preparedStatement = null;
		resultSet = null;
		int index = 1;
		preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO departments (name, country_id) VALUES (?, ?)");
		preparedStatement.setString(index++, department.getName());
		preparedStatement.setInt(index++, department.getCountry().getId());
		try {
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	@Override
	public void saveCity(Connection connection, City city) throws SQLException {
		preparedStatement = null;
		resultSet = null;
		int index = 1;
		preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO cities (name, department_id) VALUES (?, ?)");
		preparedStatement.setString(index++, city.getName());
		preparedStatement.setInt(index++, city.getDepartment().getId());
		try {
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

}
