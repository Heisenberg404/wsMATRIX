package Manager;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import DBConnection.ConnectionDatabase;
import Dao.TransactionDaoJdbc;
import Entities.City;
import Entities.Country;
import Entities.Department;
import Entities.User;

public class DataManagerImpl extends ConnectionDatabase implements DataManager {
	TransactionDaoJdbc transac = new TransactionDaoJdbc();

	@Override
	public List<Object> getAllData() throws Exception {

		List<Object> allData = new ArrayList<Object>();
		Connection connection = (Connection) this.getConnection();
		allData = transac.getAllData(connection);
		return allData;
	}

	@Override
	public void saveUser(User user) throws Exception {
		Connection connection = (Connection) this.getConnection();
		transac.saveUser(connection, user);
	}

	@Override
	public Boolean findUser(User user) throws Exception {
		Connection connection = (Connection) this.getConnection();
		boolean response = transac.findUser(connection, user);
		return response;
	}
	
	@Override
	public void saveCountry(Country country) throws Exception {
		Connection connection = (Connection) this.getConnection();
		transac.saveCountry(connection, country);
	}

	@Override
	public void saveDepartment(Department department) throws Exception {
		Connection connection = (Connection) this.getConnection();
		transac.saveDepartment(connection, department);
		
	}

	@Override
	public void saveCity(City city) throws Exception {
		Connection connection = (Connection) this.getConnection();
		transac.saveCity(connection, city);
		
	}

}
