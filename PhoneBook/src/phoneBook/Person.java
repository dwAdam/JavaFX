package phoneBook;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Adam
 */
public class Person {
    
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    public SimpleStringProperty id;
    
    public Person() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
    }
    
    public Person(String fName, String lName, String email) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty("");
    }
    
    public Person(Integer id, String fName, String lName, String email) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getEmail() {
        return email.get();
    }
    
    public void setFirstName(String fName) {
        firstName.set(fName);
    }
    
    public void setLastName(String lName) {
        lastName.set(lName);
    }
        
    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }
    
    
    
    
}
