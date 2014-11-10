package kata4;

import java.sql.Connection;

public class DatabasePersonLoader implements PersonLoader {
    private final Connection connection;

    public DatabasePersonLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person[] load() {
        connection.createStatement().executeQuery("SELECT * FROM people");
    }
    
    
}
