package phoneBook;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class DB {
    
//    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
//    final String USERNAME = "";
//    final String PASSWORD = "";
    
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    ResultSet rs1 = null;
    
        public DB() {
        
        try {
            conn = DriverManager.getConnection(URL/*, USERNAME, PASSWORD*/);
            System.out.println("Connection 1");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection 0");
        }
        
        if(conn != null) {
            try {
                createStatement = conn.createStatement();
                System.out.println("Statement 1");
            } catch (SQLException ex) {
                System.out.println("Statement 0");
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            dbmd = conn.getMetaData();
            System.out.println("DatabaseMetaData 1");
        } catch (SQLException ex) {
            System.out.println("DatabaseMetaData 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            rs1 = dbmd.getTables(null, "APP", "CONTACTS", null);
            //System.out.println("ResultSet 1");
            if (!rs1.next()) {
                createStatement.execute("CREATE TABLE contacts(id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),firstname varchar(20), lastname varchar(20), email varchar(30))");
            }
        } catch (SQLException ex) {
            System.out.println("ResultSet 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
        /**
        Osszeallitunk a konstruktor segitsegevel
        egy uj Person-t, a visszaerkezo adatok
        felhasznalasaval, hozzaadjuk a listahoz,
        es azt a listat onnantol barki hasznalhatja
        */
        public ArrayList<Person> getAllContacts() {
        String sql = "SELECT * FROM contacts";  //kuldj nekem vissza minden contactot
        ArrayList<Person> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            System.out.println("getAllUsers 1");
            
            users = new ArrayList<>();
            
            while(rs.next()) {
                Person actualPerson = new Person(rs.getInt("id"), rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"));
                users.add(actualPerson);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("getAllUsers 0");
        }
        return users;
    }
        
        public void addContact(Person person) {
        try {
              String sql = "INSERT INTO contacts (firstname, lastname, email) VALUES(?, ?, ?)";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, person.getFirstName());
              preparedStatement.setString(2, person.getLastName());
              preparedStatement.setString(3, person.getEmail());
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("addContact 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void updateContact(Person person) {
        try {
              String sql = "UPDATE contacts set firstname = ?, lastname = ?, email = ? WHERE id = ?";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, person.getFirstName());
              preparedStatement.setString(2, person.getLastName());
              preparedStatement.setString(3, person.getEmail());
              preparedStatement.setInt(4, Integer.parseInt(person.getId()));
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("addContact 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        public void removeContact(Person person) {
        try {
              String sql = "DELETE FROM contacts WHERE id = ?";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setInt(1, Integer.parseInt(person.getId()));
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("removeContact 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
