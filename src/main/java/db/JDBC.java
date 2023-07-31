package db;

import java.sql.*;

public class JDBC {
private static final String protocol = "jdbc";
private static final String vendor = ":mysql:";
private static final String location = "//localhost/";
private static final String databaseName = "client_schedule";
private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
private static final String userName = "sqlUser"; // Username
private static final String password = "Passw0rd!"; // Password
private static Connection connection = null;  // Connection Interface
private static PreparedStatement preparedStatement;

public static void makeConnection() {

    connection = startConnection();
}

public static Connection startConnection()
{
    Connection _connection = null;
    try {
        Class.forName(driver); // Locate Driver
        // password = Details.getPassword(); // Assign password
        _connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
        System.out.println("Connection successful!");
    } catch (ClassNotFoundException e) {
        System.out.println("Error:" + e.getMessage());
    } catch (SQLException e) {
        System.out.println("Error:" + e.getMessage());
    }
    return _connection;
}

public static Connection getConnection() {
    return connection;
}
public static void closeConnection() {
    try {
        connection.close();
        System.out.println("Connection closed!");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}
public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
    if (conn != null) preparedStatement = conn.prepareStatement(sqlStatement);
    else
        System.out.println("Prepared Statement Creation Failed!");

    var resultSet = preparedStatement.executeQuery();
    var result = resultSet.next();
}
public static PreparedStatement getPreparedStatement() throws SQLException {
    if (preparedStatement != null)
        return preparedStatement;
    else System.out.println("Null reference to Prepared Statement");
    return null;
}
public static int checkLogin (String userName, String password) throws SQLException {
    var query = "SELECT * FROM users WHERE User_Name = '" + userName + "' AND Password = '" + password + "'";
    if (connection != null) preparedStatement = connection.prepareStatement(query);
    else {
        System.out.println("Prepared Statement Creation Failed!");
        return 0;
    }
    var resultSet = preparedStatement.executeQuery();
    if(!resultSet.next()) return 0;
    return (resultSet.getInt("User_ID"));
}
}

