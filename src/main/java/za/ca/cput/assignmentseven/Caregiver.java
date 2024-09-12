package za.ca.cput.assignmentseven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Caregiver {
    static final String DATABASE_URL = "jdbc:derby://localhost:1527/Caregiverregistration";
    private final String dbUsername = "Username";
    private final String dbPassword = "12345";
    
    private String caregiverCode;
    private String firstName;
    private String lastName;
    private String caregiverType;
    private Boolean equipment;

    
    public Caregiver() {

    }
    public Caregiver(String caregiverCode, String firstName, String lastName, String caregiverType, Boolean equipment) {
        this.caregiverCode = caregiverCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.caregiverType = caregiverType;
        this.equipment = equipment;
    }

    public String getCaregiverCode() {
        return caregiverCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCaregiverType() {
        return caregiverType;
    }

    public Boolean isEquipment() {
        return equipment;
    }

    public void setCaregiverCode(String caregiverCode) {
        this.caregiverCode = caregiverCode;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCaregiverType(String caregiverType) {
        this.caregiverType = caregiverType;
    }

    public void setEquipment(Boolean equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "Caregiver{" + "caregiverCode=" + caregiverCode + ", firstName=" + firstName + ", lastName=" + lastName + ", caregiverType=" + caregiverType + ", Equipment=" + equipment + '}';
    }
    
    public void save() {
        Connection connect = null; //connection object 
        PreparedStatement pStatement = null; //query statement
        int ok;
        String sql = "INSERT INTO Caregivers VALUES (?, ?, ?, ?, ?)";
        
        try {
            connect = DriverManager.getConnection(DATABASE_URL, dbUsername, dbPassword);
            pStatement = connect.prepareStatement(sql);
            pStatement.setString(1, caregiverCode);
            pStatement.setString(2, firstName);
            pStatement.setString(3, lastName);
            pStatement.setString(4, caregiverType);
            pStatement.setBoolean(5, equipment);
            
            ok = pStatement.executeUpdate();
            if (ok > 0) {
                JOptionPane.showMessageDialog(null, "Success Caregiver Added!");
            } else {
                JOptionPane.showMessageDialog(null, "Error! Could not add Caregiver ");
            }
            
            
        } catch (SQLException sqlException){
            JOptionPane.showMessageDialog(null, "Error!" + sqlException.getMessage());    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error!" + e.getMessage()); 
        }
        finally {
            try {
                if (pStatement != null) 
                    pStatement.close();
                
            } catch (Exception exception) { 
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning!", JOptionPane.ERROR_MESSAGE);
            }
            try {
                if (connect != null) 
                    connect.close();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}