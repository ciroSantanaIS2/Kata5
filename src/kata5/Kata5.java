package kata5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Kata5 {

    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        try {
            createSqLiteConnection("people.db");
            PersonLoader loader = new DatabasePersonLoader(connection);
            HistogramBuilder<Person> builder = new HistogramBuilder<> (loader.load());
            Histogram<String> histogram = builder.build(new AttributeExtractor<Person, String>() {

                @Override
                public String extract(Person person) {
                    return person.getMail().getMainDomain();
                }

            });
            HistogramViewer<String> viewer = new ChartHistogramViewer<>();
            viewer.show(histogram);
        }
        finally {
            if (connection == null) return;
            connection.close();
        }
    }

    private static void createSqLiteConnection(String name) throws SQLException {
        DriverManager.registerDriver(new org.sqlite.JDBC());
        connection = DriverManager.getConnection("jdbc:sqlite:" + name);
    }
}