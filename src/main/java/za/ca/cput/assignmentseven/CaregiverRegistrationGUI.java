package za.ca.cput.assignmentseven;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static za.ca.cput.assignmentseven.Caregiver.DATABASE_URL;


public class CaregiverRegistrationGUI extends JFrame implements ActionListener {
    
    private JPanel panelNorth;
    private ImageIcon icImage;
    private Image newImage;
    private JLabel lblImage;
    
    private JPanel panelCenter, panelGender;
    private JLabel lblCaregiverCode, lblFirstName, lblLastName, lblCaregiverType, lblEquipment, lblHeading;
    private JTextField txtCode, txtFirstName, txtLastName;
    private JComboBox cbxType;
    private JRadioButton rbYes, rbNo;
    private ButtonGroup yesOrNo;
    
    private JPanel panelSouth;
    private JButton btnSave, btnReset, btnExit;
    
    private ArrayList<Caregiver> caregiverTypesList = new ArrayList<>();
    
    public CaregiverRegistrationGUI() {
        super ("Caregiver Registration");
        
        //Adding north Panel
        panelNorth = new JPanel();
        lblHeading = new JLabel("Caregiver Registration");
        lblHeading.setFont(new Font("Serif", Font.PLAIN, 20));
        lblHeading.setForeground(Color.YELLOW);
    
    /*    icImage = new ImageIcon(getClass().getResource("man2.png"));
        lblImage = new JLabel(icImage); 
        newImage = icImage.getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT);
        icImage = new ImageIcon(newImage);
        add(lblImage);  */

        
        //Adding Center Panel
        panelCenter = new JPanel();
        //1st Row
        lblCaregiverCode = new JLabel("Caregiver Code: ", SwingConstants.RIGHT);
        txtCode = new JTextField();
        
        //2nd Row
        lblFirstName = new JLabel("First Name: ", SwingConstants.RIGHT);
        txtFirstName = new JTextField();
 
        //3rd row
        lblLastName = new JLabel("Last Name: ", SwingConstants.RIGHT);
        txtLastName = new JTextField();
        
        //4th row
        lblCaregiverType = new JLabel("Caregiver Type: ", SwingConstants.RIGHT);
        cbxType = new JComboBox();
        populateComboBox();
        //5th row
        lblEquipment = new JLabel("Do you have caregiver Equipment: ", SwingConstants.RIGHT);
        panelGender = new JPanel();    
        rbYes = new JRadioButton("Yes");
        rbNo = new JRadioButton("No");
        yesOrNo = new ButtonGroup();
        yesOrNo.add(rbYes);
        yesOrNo.add(rbNo);
        panelGender.setLayout(new GridLayout(1, 2));
        rbYes.setSelected(true);
        panelGender.add(rbYes);
        panelGender.add(rbNo);
        
        //adding South panel
        panelSouth = new JPanel();
        //adding buttons
        btnSave = new JButton("Save");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");
    }
    
    public void setGUI() {
        panelNorth.setBackground(new Color(42, 188, 255));
        panelCenter.setBackground(new Color(42, 188, 255));
        panelGender.setBackground(new Color(42, 188, 255));
        
        //North panel components
        //panelNorth.add(lblImage);
        panelNorth.add(lblHeading);
        
        //Center panel components
        panelCenter.setLayout(new GridLayout(5, 2));
        //1st row
        panelCenter.add(lblCaregiverCode);
        panelCenter.add(txtCode);
        //2nd row 
        panelCenter.add(lblFirstName);
        panelCenter.add(txtFirstName);
        //3rd row
        panelCenter.add(lblLastName);
        panelCenter.add(txtLastName);
        //4th row
        panelCenter.add(lblCaregiverType);
        panelCenter.add(cbxType);
        
       
        //5th row
        panelCenter.add(lblEquipment);
        panelCenter.add(panelGender);
        
        //South panel components
        panelSouth.setLayout(new GridLayout(1, 3));
        panelSouth.add(btnSave);
        panelSouth.add(btnReset);
        panelSouth.add(btnExit);
        
        this.add(panelNorth, BorderLayout.NORTH);
        this.add(panelCenter, BorderLayout.CENTER);
        this.add(panelSouth, BorderLayout.SOUTH);
        
        btnSave.addActionListener(this);
        btnReset.addActionListener(this);
        btnExit.addActionListener(this);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //this.pack();
        this.setSize(450, 320);
   
    }
    
    public boolean inputValid() {
        boolean valid = true;
        
        Connection connect = null; //connection object 
        Statement statement = null; //query statement
        
        String val = "Select Cargivercode * FROM Caregivers WHERE Caregivercode='" + txtCode +"'";
        
        try {
            connect = DriverManager.getConnection(DATABASE_URL, "Username", "12345");
            statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(val);
            
            while (rs.next()) {
                if (rs.getObject(1).equals(txtCode)) {
                      JOptionPane.showMessageDialog(null, "The Caregiver code is not unique! Try again!");      
                } else 
                    JOptionPane.showMessageDialog(null, "Success! The information has been saved");   
            }            
        } // end try
        catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "Error! Could not read from DB");
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
                
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
        return valid;
    }
    
    public void resetForm() {
        txtCode.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        cbxType.setSelectedIndex(0);
        rbYes.setSelected(true);
    }
    
    public ArrayList<Caregiver> populateComboBox() {
        Connection connect = null; //connection object 
        Statement statement = null; //query statement
        
        ArrayList<Caregiver> caregiverTypesList = new ArrayList<>();
        String retrieve_Values_qry = "Select * FROM CAREGIVERTYPES";
        
        try {
            connect = DriverManager.getConnection(DATABASE_URL, "Username", "12345");
            statement = connect.createStatement();
            ResultSet rs = statement.executeQuery(retrieve_Values_qry);
            
            if (rs != null) {
                while (rs.next())
                    cbxType.addItem(rs.getString("CAREGIVERTYPENAME"));    
            }
        } // end try
        catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "Error! Could not read from DB");
        }
        finally {
            try {
                if (statement != null) 
                    statement.close();
                
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
        return caregiverTypesList;    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            if (inputValid()) {
                Caregiver u = new Caregiver(txtCode.getText(), txtFirstName.getText(), txtLastName.getText(),cbxType.getSelectedItem().toString(), rbYes.isSelected());
                u.save();
                resetForm();
            }
        } else if (e.getSource() == btnReset) {
            resetForm();
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        } 
    }
    
}
