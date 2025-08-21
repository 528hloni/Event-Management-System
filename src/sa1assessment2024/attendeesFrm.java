/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sa1assessment2024;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author hloni
 */
public class attendeesFrm extends javax.swing.JFrame {

    /**
     * Creates new form attendeesFrm
     */
    public attendeesFrm() {
        initComponents();
    }
    
     Boolean boolRecordExists=false; //boolean will be used to check if record exists
    String strAttendeeName;
    String strEmail;
    Integer intContactNumber;
    String strEventSelection;
    
   
     private void mGetValuesFromGUI(){
        //values from the user
         try {
          strAttendeeName = txtAttendeeName.getText();
        strEmail = txtEmail.getText();
        intContactNumber = Integer.parseInt(txtContactNumber.getText());
        
        strEventSelection = (String) cbEventSelection.getSelectedItem();   
         } catch (Exception e) {
             e.printStackTrace();
              JOptionPane.showMessageDialog(null, "Enter correct input"+""+e);
             
         }
       
        
       
    }
     
     
   
     
     private void mLoadComboBoxItems() {
    // Loading items from Event table (event name)
    
    
    // Load database credentials from config file to avoid hardcoding sensitive information
    Properties props = new Properties();

    try (InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
        if (fis == null) {
            JOptionPane.showMessageDialog(this, "Config file not found!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Load DB credentials from config.properties
        props.load(fis);
        String URL6 = props.getProperty("db.url");
        String User6 = props.getProperty("db.username");
        String Password6 = props.getProperty("db.password");
        
         java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table

        // Connect to DB
        try{
        conMySQLConnectionString = DriverManager.getConnection(URL6,User6,Password6); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
            String strQuery = "SELECT event_name FROM event_details";
            stStatement.execute(strQuery);
         rs=stStatement.getResultSet();

            // Clear existing items
            cbEventSelection.removeAllItems();

            // Populate combo box
            while (rs.next()) {
                cbEventSelection.addItem(rs.getString("event_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to database", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading config file", "File Error", JOptionPane.ERROR_MESSAGE);
    }
}
     
     
     
      private void mSetvaluesToUpperCase(){
        //values will be uppercase
        strAttendeeName = strAttendeeName.toUpperCase();
        strEmail = strEmail.toUpperCase();
       
        }
      
       
       
       private void mCheckIfItemsExistInTable(){
         //Validation to prevent duplication
         
         // Load database credentials from config file to avoid hardcoding sensitive information
        Properties props = new Properties();
        
        try (InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (fis == null) {
                JOptionPane.showMessageDialog(this, "Config file not found!", "File Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Load DB credentials from config.properties
            props.load(fis);
            String URL1 = props.getProperty("db.url");
            String User1 = props.getProperty("db.username");
            String Password1 = props.getProperty("db.password");
            
            java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
            PreparedStatement pstStatement = null; //Declares prepared statement which will contain sql statement
            ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
            
            // try catch contains code to run the query against database table
            try {
                conMySQLConnectionString = DriverManager.getConnection(URL1,User1,Password1); //used to gain access to database
                
               
                String strQuery = "SELECT * FROM attendee_info WHERE attendee_name = ? AND e_mail = ? AND contact_number = ? AND event_selection = ?";
                
                // Prepare the statement with the parameterized query
                conMySQLConnectionString.prepareStatement(strQuery); 
                
                
                pstStatement.setString(1, strAttendeeName);    // Set first ? placeholder with attendee name
                pstStatement.setString(2, strEmail);           // Set second ? placeholder with email  
                pstStatement.setInt(3, intContactNumber);   // Set third ? placeholder with contact number
                pstStatement.setString(4, strEventSelection);  // Set fourth ? placeholder with event selection
                
               
                rs = pstStatement.executeQuery(); // Execute sql statements against the database table
                boolRecordExists=rs.next(); //Confirm if the record exist or not in the database
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally { //final block has try catch that closes connection of the database
                try {
                    pstStatement.close(); // Close the PreparedStatement to free up database resources
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Connection String not closed"+""+e);
                }
      
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading config file: " + e.getMessage(), "Config Error", JOptionPane.ERROR_MESSAGE);
        }
    }
       
       
      
        
        
        private void mCreateAttendee()
            //Create a new attendee
{
    // Load database credentials from config file to avoid hardcoding sensitive information
    Properties props = new Properties();
    
    try (InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
        if (fis == null) {
            JOptionPane.showMessageDialog(this, "Config file not found!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Load DB credentials from config.properties
        props.load(fis);
        String URL2 = props.getProperty("db.url");        
        String User2 = props.getProperty("db.username");   
        String Password2 = props.getProperty("db.password"); 
        
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        PreparedStatement pstStatement = null; //Declares prepared statement 
        
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL2,User2,Password2); //used to gain access to database
            
            
            String sqlinsert = "INSERT INTO attendee_info (attendee_name,e_mail,contact_number,event_selection) VALUES (?,?,?,?)";
            
            // Prepare the statement with the parameterized query
            pstStatement = conMySQLConnectionString.prepareStatement(sqlinsert);
            
           
            pstStatement.setString(1, strAttendeeName);    // Set first ? placeholder with attendee name
            pstStatement.setString(2, strEmail);           // Set second ? placeholder with email
            pstStatement.setInt(3, intContactNumber);   // Set third ? placeholder with contact number
            pstStatement.setString(4, strEventSelection);  // Set fourth ? placeholder with event selection
            
            // Execute the safe parameterized insert statement
            pstStatement.executeUpdate(); // Execute sql statements against the database table
            
            JOptionPane.showMessageDialog(null,"Complete");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e );
        } finally {
            // Clean up database resources in finally block to ensure they're always closed
            try {
                if (pstStatement != null) {
                    pstStatement.close(); // Close the PreparedStatement to free up database resources
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "PreparedStatement not closed: " + e);
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading config file: " + e.getMessage(), "Config Error", JOptionPane.ERROR_MESSAGE);
    }
}
        
       
         
         private void mTableView(){
         //View table
         
 int CC; //Column count
 
 // Load database credentials from config file to avoid hardcoding sensitive information
 Properties props = new Properties();
 
 try (InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
     if (fis == null) {
         JOptionPane.showMessageDialog(this, "Config file not found!", "File Error", JOptionPane.ERROR_MESSAGE);
         return;
     }

     // Load DB credentials from config.properties
     props.load(fis);
     String URL3 = props.getProperty("db.url");        
     String User3 = props.getProperty("db.username");   
     String Password3 = props.getProperty("db.password"); 
     
     java.sql.Connection conMySQLConnectionString = null;//Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
     PreparedStatement pst = null; // PreparedStatement for safe database querying
     ResultSet Rs1 = null; // ResultSet to hold query results
     
     try {
      conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3);  //used to gain access to database 
      
      // Created prepared statement with SQL query to retrieve all records
      pst = conMySQLConnectionString.prepareStatement("SELECT * FROM attendee_info"); 
      
      // Execute the query and store result in ResultSet
      Rs1 = pst.executeQuery();
      
     // ResultSetMetaData object provides information about the structure of the result set (e.g the number of columns).
      ResultSetMetaData RSMD = Rs1.getMetaData();
      CC = RSMD.getColumnCount(); //Store column count
      DefaultTableModel DFT = (DefaultTableModel) tblAttendees.getModel();
      DFT.setRowCount(0); // Set row count to 0 to start fresh.
        
      //Filling the table with database records
      while (Rs1.next()) {
          Vector v2 = new Vector(); //v2 holds attendee details for current row
          
          
          v2.add(Rs1.getString("attendee_id"));     // Add attendee ID
          v2.add(Rs1.getString("attendee_name"));   // Add attendee name
          v2.add(Rs1.getString("e_mail"));          // Add email address
          v2.add(Rs1.getString("contact_number"));  // Add contact number
          v2.add(Rs1.getString("event_selection")); // Add event selection
          
          // Add the completed row to the table model
          DFT.addRow(v2);
      }        
     } catch (Exception e) {
       JOptionPane.showMessageDialog(null, e);  
     } finally {
         // Clean up database resources in finally block to ensure they're always closed
         try {
             if (Rs1 != null) {
                 Rs1.close(); // Close ResultSet to free up resources
             }
             if (pst != null) {
                 pst.close(); // Close PreparedStatement to free up resources
             }
             if (conMySQLConnectionString != null) {
                 conMySQLConnectionString.close(); // Close database connection
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error closing database resources: " + e);
         }
     }
 } catch (Exception e) {
     JOptionPane.showMessageDialog(this, "Error loading config file: " + e.getMessage(), "Config Error", JOptionPane.ERROR_MESSAGE);
 }
         }
         
         
         
          private void mClearTextFields(){ 

//This will clear textfields once the values have been captured
        txtAttendeeName.setText("");
        txtEmail.setText("");
        txtContactNumber.setText("");
        cbEventSelection.setSelectedIndex(-1);
        
    } 
          
         
           
           
           private void mEditUpdate()
       //   Update/Edit existing attendee        
{ 
    // Load database credentials from config file to avoid hardcoding sensitive information
    Properties props = new Properties();
    
    try (InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
        if (fis == null) {
            JOptionPane.showMessageDialog(this, "Config file not found!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Load DB credentials from config.properties
        props.load(fis);
        String URL4 = props.getProperty("db.url");        
        String User4 = props.getProperty("db.username");   
        String Password4 = props.getProperty("db.password"); 
        
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        PreparedStatement pstStatement = null; //Declares prepared statement (safer than regular statement - prevents SQL injection)
        
         //Get selected row information from the table
         DefaultTableModel model = (DefaultTableModel) tblAttendees.getModel();//Get model of table
         int intSelectedIndex = tblAttendees.getSelectedRow(); // Get the selected row index
         int intAttendeeID = Integer.parseInt(model.getValueAt(intSelectedIndex,0).toString()); // Get attendee ID from first column
         
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL4,User4,Password4); //used to gain access to database
            
            
            String strQuery = "UPDATE attendee_info SET attendee_name = ?, e_mail = ?, contact_number = ?, event_selection = ? WHERE attendee_id = ?";
            
            // Prepare the statement with the parameterized query
            pstStatement = conMySQLConnectionString.prepareStatement(strQuery);
            
            
            pstStatement.setString(1, strAttendeeName);    // Set first ? placeholder with attendee name
            pstStatement.setString(2, strEmail);           // Set second ? placeholder with email
            pstStatement.setInt(3, intContactNumber);      // Set third ? placeholder with contact number (using setInt for integer)
            pstStatement.setString(4, strEventSelection);  // Set fourth ? placeholder with event selection
            pstStatement.setInt(5, intAttendeeID);         // Set fifth ? placeholder with attendee ID (using setInt for integer)
            
            // Execute the safe parameterized update statement
            pstStatement.executeUpdate(); // Execute sql statements against the database table
            
            JOptionPane.showMessageDialog(null,"Record Updated");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            // Clean up database resources in finally block to ensure they're always closed
            try {
                if (pstStatement != null) {
                    pstStatement.close(); // Close the PreparedStatement to free up database resources
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "PreparedStatement not closed: " + e);
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading config file: " + e.getMessage(), "Config Error", JOptionPane.ERROR_MESSAGE);
    }
}
           
      
            
            
            private void mDelete()
        //Delete attendee        
{
    // Load database credentials from config file to avoid hardcoding sensitive information
    Properties props = new Properties();
    
    try (InputStream fis = getClass().getClassLoader().getResourceAsStream("config.properties")) {
        if (fis == null) {
            JOptionPane.showMessageDialog(this, "Config file not found!", "File Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Load DB credentials from config.properties
        props.load(fis);
        String URL3 = props.getProperty("db.url");        
        String User3 = props.getProperty("db.username");   
        String Password3 = props.getProperty("db.password"); 
        
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        PreparedStatement pstStatement = null; //Declares prepared statement 
        
        // Get selected row information from the table
        DefaultTableModel model = (DefaultTableModel) tblAttendees.getModel();
        int intSelectedIndex = tblAttendees.getSelectedRow(); // Get the selected row index
        int intAttendeeID = Integer.parseInt(model.getValueAt(intSelectedIndex,0).toString()); // Get attendee ID from first column
        
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            
            // Create SQL delete statement with ? placeholder for attendee ID only
            
            String strQuery = "DELETE FROM attendee_info WHERE attendee_id = ?";
            
            // Prepare the statement with the parameterized query
            pstStatement = conMySQLConnectionString.prepareStatement(strQuery);
            
            
            pstStatement.setInt(1, intAttendeeID); // Set first ? placeholder with attendee ID
            
            // Execute the parameterized delete statement
            int rowsAffected = pstStatement.executeUpdate(); // Execute sql statement and get number of affected rows
            
            // Check if the delete operation was successful
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null,"Record Deleted");
            } else {
                JOptionPane.showMessageDialog(null,"No record found to delete");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            // Clean up database resources in finally block to ensure they're always closed
            try {
                if (pstStatement != null) {
                    pstStatement.close(); // Close the PreparedStatement to free up database resources
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "PreparedStatement not closed: " + e);
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading config file: " + e.getMessage(), "Config Error", JOptionPane.ERROR_MESSAGE);
    }
}
   
     
    
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAttendees = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblContactInformation = new javax.swing.JLabel();
        lblEventSelection = new javax.swing.JLabel();
        txtAttendeeName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtContactNumber = new javax.swing.JTextField();
        cbEventSelection = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAttendees = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnView = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblAttendees.setBackground(new java.awt.Color(0, 255, 255));
        lblAttendees.setFont(new java.awt.Font("PMingLiU-ExtB", 3, 24)); // NOI18N
        lblAttendees.setForeground(new java.awt.Color(255, 255, 255));
        lblAttendees.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAttendees.setText("Attendees Registration");
        lblAttendees.setOpaque(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255)));

        lblName.setForeground(new java.awt.Color(0, 255, 255));
        lblName.setText("Name");

        lblEmail.setForeground(new java.awt.Color(0, 255, 255));
        lblEmail.setText("E-mail");

        lblContactInformation.setForeground(new java.awt.Color(0, 255, 255));
        lblContactInformation.setText("Contact Number");

        lblEventSelection.setForeground(new java.awt.Color(0, 255, 255));
        lblEventSelection.setText("Event Selection");

        txtAttendeeName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255)));

        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        txtContactNumber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        cbEventSelection.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContactInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEventSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAttendeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEventSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblName)
                    .addComponent(txtAttendeeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContactInformation)
                    .addComponent(txtContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEventSelection)
                    .addComponent(cbEventSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblAttendees.setBackground(new java.awt.Color(0, 255, 255));
        tblAttendees.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 255, 255)));
        tblAttendees.setForeground(new java.awt.Color(255, 255, 255));
        tblAttendees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "E-Mail", "Contact Number", "Event Selection"
            }
        ));
        tblAttendees.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAttendeesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAttendees);

        btnSave.setBackground(new java.awt.Color(0, 255, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(0, 255, 255));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 255, 255));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 255, 255));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnReturn.setBackground(new java.awt.Color(0, 255, 255));
        btnReturn.setForeground(new java.awt.Color(255, 255, 255));
        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnView.setBackground(new java.awt.Color(0, 255, 255));
        btnView.setForeground(new java.awt.Color(255, 255, 255));
        btnView.setText("View");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave)
                    .addComponent(btnClear))
                .addGap(259, 259, 259)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit)
                    .addComponent(btnView))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 366, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReturn)
                    .addComponent(btnDelete))
                .addGap(74, 74, 74))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAttendees, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(385, 385, 385))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lblAttendees, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnDelete))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnReturn)
                    .addComponent(btnView))
                .addContainerGap(345, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        //Returns to main menu
        mainFrm frmM = new mainFrm();
       frmM.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        //Create
        
        
        //Validation
      if(txtAttendeeName.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"The field cannot be left empty");
            txtAttendeeName.requestFocusInWindow();
        }
        else if(txtEmail.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        txtEmail.requestFocusInWindow();
        }
        else if(txtContactNumber.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        txtContactNumber.requestFocusInWindow();
        }
        else if(cbEventSelection.getSelectedIndex() == -1)
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        cbEventSelection.requestFocusInWindow();
       
        }
        else
        {
          
        mGetValuesFromGUI();
        mSetvaluesToUpperCase();
        mCheckIfItemsExistInTable();
        
        //if the record exist then show jOptionPane message then set boolean record to false
        //if the record doesnt exist then create passeneger,update the table then clear textfields
        if(boolRecordExists==true)
        {
        boolRecordExists=false;
        JOptionPane.showMessageDialog(null, "Attendee already exists");
        }
        else if (boolRecordExists==false)
        {
        mCreateAttendee();
        mTableView();
        mClearTextFields();
        }    
        }     
                                             
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        mClearTextFields();
        
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
       //View
        
        mTableView();
        
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        //     Update/ Edit 
        
        
          int intSelectedIndex = tblAttendees.getSelectedRow();
    if (intSelectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to edit.");
    }else{
      
    

        mGetValuesFromGUI();
        
        mSetvaluesToUpperCase();
        mCheckIfItemsExistInTable();
        //if the record exist then show jOptionPane message then set boolean record to false
        //if the record doesnt exist then create passeneger,update the table then clear textfields
        if(boolRecordExists==true)
        {
        boolRecordExists=false;
        JOptionPane.showMessageDialog(null, "Attendee already exists");
        }
        else if (boolRecordExists==false)
        {
        mEditUpdate();
        mTableView();
        mClearTextFields();
        }
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblAttendeesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAttendeesMouseClicked
        
           //selected row from table will appear on textfields 
      DefaultTableModel model = (DefaultTableModel) tblAttendees.getModel();
      int intSelectedIndex = tblAttendees.getSelectedRow();
      
      int intAttendeeID = Integer.parseInt(model.getValueAt(intSelectedIndex,0).toString());
      txtAttendeeName.setText(model.getValueAt(intSelectedIndex, 1).toString());
      txtEmail.setText(model.getValueAt(intSelectedIndex, 2).toString());
      txtContactNumber.setText(model.getValueAt(intSelectedIndex, 3).toString());
      cbEventSelection.setSelectedItem(model.getValueAt(intSelectedIndex, 4));
    
    }//GEN-LAST:event_tblAttendeesMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //when the form loads,the items from the Event table(event name column) load into the combo box
       mLoadComboBoxItems();
       cbEventSelection.setSelectedIndex(-1);
    }//GEN-LAST:event_formWindowOpened

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //Delete
        
          int intSelectedIndex = tblAttendees.getSelectedRow();
    if (intSelectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }else{
        mGetValuesFromGUI();
        
         //Add confirmation JOptionPane dialog before deleting a record
         int response = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To DELETE this Attendee",
        "Select An Option",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
        
       if (response == JOptionPane.YES_OPTION){
        mDelete();
        mTableView();
        mClearTextFields();
       }
       else{
           JOptionPane.showMessageDialog(null, "Delete Cancelled");
       }
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(attendeesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(attendeesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(attendeesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(attendeesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new attendeesFrm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnView;
    private javax.swing.JComboBox<String> cbEventSelection;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAttendees;
    private javax.swing.JLabel lblContactInformation;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEventSelection;
    private javax.swing.JLabel lblName;
    private javax.swing.JTable tblAttendees;
    private javax.swing.JTextField txtAttendeeName;
    private javax.swing.JTextField txtContactNumber;
    private javax.swing.JTextField txtEmail;
    // End of variables declaration//GEN-END:variables
}
