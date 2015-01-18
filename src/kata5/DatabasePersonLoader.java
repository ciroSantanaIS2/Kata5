package kata5;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabasePersonLoader implements PersonLoader {

    private final Connection connection;

    public DatabasePersonLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person[] load() {
        try {
            return loadPeople();
        } catch (SQLException ex) {
            return new Person[0];
        }

    }

    private Person[] processQuery(ResultSet resultSet) throws SQLException {
        ArrayList<Person> personList = new ArrayList<>();

        while (resultSet.next()) {
        }
        personList.add(processPerson(resultSet));

        return personList.toArray(new Person[personList.size()]);
    }

    private Person processPerson(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("company_name"),
                resultSet.getString("address"),
                resultSet.getString("city"),
                resultSet.getString("state"),
                new Mail(resultSet.getString("email")),
                resultSet.getString("web"));
    }

    private Person[] loadPeople() throws SQLException {
        ResultSet set = createResultSet("SELECT * FROM people");
        return getPeople(set);
    }

    private ResultSet createResultSet(String select__from_people) throws SQLException {
        return connection.createStatement().executeQuery(select__from_people);
    }

    private Person[] getPeople(ResultSet set) throws SQLException {
        ArrayList<Person> list = new ArrayList<>();
        while (set.next())
            list.add(createPerson(set));
        return list.toArray(new Person[0]);
    }

    private Person createPerson(ResultSet set) throws SQLException {
        return new Person(
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("company_name"),
                set.getString("address"),
                set.getString("city"),
                set.getString("state"),
                new Mail(set.getString("email")),
                set.getString("web")
        );
    }

}
