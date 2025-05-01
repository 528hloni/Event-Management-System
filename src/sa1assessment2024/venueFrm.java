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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hloni
 */
public class venueFrm extends javax.swing.JFrame {

    /**
     * Creates new form venueFrm
     */
    public venueFrm() {
        initComponents();
    }
     Boolean boolRecordExists=false; //boolean will be used to check if record exists
    String strVenueName;
    String strAddress;
    Integer intCapacity;
    String strAvailability;
   
    
    
    private void mGetValuesFromGUI(){
        //values from the user
        
        try {
      strVenueName = txtVenueName.getText();
        strAddress = txtAddress.getText();
        intCapacity = Integer.parseInt(txtCapacity.getText());
        strAvailability = (String) cbAvailability.getSelectedItem();      
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Enter correct input "+""+e);
        }
        
        
       
    }
    
      
        private void mSetvaluesToUpperCase(){
        //values will be uppercase
        strVenueName = strVenueName.toUpperCase();
        strAddress = strAddress.toUpperCase();
        strAvailability = strAvailability.toUpperCase();
        }
    
         private void mCheckIfItemsExistInTable(){
         //Validation to prevent duplication
         
        String URL1 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User1 = "root"; //User name to connect to database
        String Password1 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
        
        // try catch contains code to run the query against database table
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL1,User1,Password1); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
            String strQuery = " Select * from venue_bookings where venue_name = '" + strVenueName + "' and address= '" //SQL statement
            + strAddress + "' and capacity= '"+ intCapacity +"'and availability='" + strAvailability+"'";
            stStatement.execute(strQuery); // Execute sql statements against the database table
            rs=stStatement.getResultSet();
            boolRecordExists=rs.next(); //Confirm if the record exist or not in the database
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally { //final block has try catch that closes connection of the database
            try {
                stStatement.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Connection String not closed"+""+e);
            }
  
        }
    }
         
      private void mCreateVenue()
              //Creating new venues
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL2 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User2 = "root"; //User name to connect to database
        String Password2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL2,User2,Password2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
            String sqlinsert = "insert into venue_bookings (venue_name,address,capacity,availability) " + //Initialises the 'insert sql statement' to store the values inserted in the textfield
            "values ('" + strVenueName + "','"+ strAddress+"','"+ intCapacity + "','"+ strAvailability +"')";
            myStatement.executeUpdate(sqlinsert); // Execute sql statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        
        }
        
    }    
      
     private void mTableView(){
         //Viewing table
         
     int CC; //Column count
     java.sql.Connection conMySQLConnectionString ;//Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
      String URL3 = "jdbc:mysql://localhost:3306/emsdb";//Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
     PreparedStatement pst;
        try {
         conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3);  //used to gain access to database 
         pst = conMySQLConnectionString.prepareStatement("SELECT * FROM venue_bookings"); //SQL query to retrieve all records from 'passenger_details'
         ResultSet Rs1 = pst.executeQuery();// Execute the query and store result in ResultSet.
         
        // ResultSetMetaData object provides information about the structure of the result set (e.g., the number of columns).
         ResultSetMetaData RSMD = Rs1.getMetaData();
         CC = RSMD.getColumnCount(); //Store column count
         DefaultTableModel DFT = (DefaultTableModel) tblVenue.getModel();
         DFT.setRowCount(0); // Set row count to 0 to start fresh.
           
         //Filling the the table
         while (Rs1.next()) {
             Vector v2 = new Vector(); //v2 holds passenger details
             
             for (int ii =1; ii <= CC; ii++){
                 v2.add(Rs1.getString("venue_id"));
                 v2.add(Rs1.getString("venue_name"));
                 v2.add(Rs1.getString("address"));
                 v2.add(Rs1.getString("capacity"));
                 v2.add(Rs1.getString("availability"));
             }
             DFT.addRow(v2);
         }        
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);  
        }
   }
   
    private void mClearTextFields(){ //This will clear textfields once the values have been captured
        txtVenueName.setText("");
        txtAddress.setText("");
        txtCapacity.setText("");
        cbAvailability.setSelectedIndex(-1);
        
    } 
    
     private void mEditUpdate()
             //  Update/Edit existing venues
   { 
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
        
        //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblVenue.getModel();//Get model of table
      int selectedIndex = tblVenue.getSelectedRow();
      int intVenueID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement euStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "Update venue_bookings Set venue_name = '" + strVenueName + 
                  "', address = '" + strAddress + 
                  "', capacity = '" + intCapacity + 
                  "', availability = '" + strAvailability + 
                  "' Where venue_id = "+intVenueID;
            euStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            euStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }
   }
     
     private void mDelete()
            // Delete venue 
   {
      java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
        
        //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblVenue.getModel();//Get model of table
      int selectedIndex = tblVenue.getSelectedRow();
      int intVenueID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement dtStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "DELETE FROM venue_bookings WHERE venue_id = '" + intVenueID + "' AND venue_name = '" + strVenueName + 
                          "' AND address = '" + strAddress + "' AND capacity = '" + intCapacity + 
                          "' AND availability = '" + strAvailability + "'";
            
            dtStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            dtStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
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

        lblVenueManagement = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblVenueName = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblCapacity = new javax.swing.JLabel();
        lblAvailability = new javax.swing.JLabel();
        txtVenueName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtCapacity = new javax.swing.JTextField();
        cbAvailability = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVenue = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnView = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblVenueManagement.setBackground(new java.awt.Color(102, 255, 0));
        lblVenueManagement.setFont(new java.awt.Font("PMingLiU-ExtB", 3, 24)); // NOI18N
        lblVenueManagement.setForeground(new java.awt.Color(255, 255, 255));
        lblVenueManagement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVenueManagement.setText("Venue Management");
        lblVenueManagement.setOpaque(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));

        lblVenueName.setBackground(new java.awt.Color(255, 255, 255));
        lblVenueName.setForeground(new java.awt.Color(102, 255, 0));
        lblVenueName.setText("Venue");

        lblAddress.setForeground(new java.awt.Color(102, 255, 0));
        lblAddress.setText("Address");

        lblCapacity.setForeground(new java.awt.Color(102, 255, 0));
        lblCapacity.setText("Capacity");

        lblAvailability.setForeground(new java.awt.Color(102, 255, 0));
        lblAvailability.setText("Availability");

        txtVenueName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));

        txtAddress.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));

        txtCapacity.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));

        cbAvailability.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NO", "YES" }));
        cbAvailability.setSelectedIndex(-1);
        cbAvailability.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                        .addComponent(cbAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblVenueName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCapacity, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCapacity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVenueName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVenueName)
                    .addComponent(txtVenueName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCapacity)
                    .addComponent(txtCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAvailability)
                    .addComponent(cbAvailability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        tblVenue.setBackground(new java.awt.Color(102, 255, 0));
        tblVenue.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 0)));
        tblVenue.setForeground(new java.awt.Color(255, 255, 255));
        tblVenue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address", "Capacity", "Availability"
            }
        ));
        tblVenue.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVenueMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVenue);

        btnSave.setBackground(new java.awt.Color(102, 255, 0));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(102, 255, 0));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(102, 255, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(102, 255, 0));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnReturn.setBackground(new java.awt.Color(102, 255, 0));
        btnReturn.setForeground(new java.awt.Color(255, 255, 255));
        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnView.setBackground(new java.awt.Color(102, 255, 0));
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
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnClear)
                                .addGap(345, 345, 345))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnView)
                            .addComponent(btnEdit))
                        .addGap(325, 325, 325)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnReturn, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(34, 34, 34))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVenueManagement, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(338, 338, 338))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblVenueManagement)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnReturn)
                    .addComponent(btnView))
                .addContainerGap(301, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        // return to main menu
         mainFrm frmM = new mainFrm();
       frmM.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
      //Save
      
        
        if(txtVenueName.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"The field cannot be left empty");
            txtVenueName.requestFocusInWindow();
        }
        else if(txtAddress.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        txtAddress.requestFocusInWindow();
        }
        else if(txtCapacity.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        txtCapacity.requestFocusInWindow();
        }
        else if(cbAvailability.getSelectedIndex() == -1)
        {
        JOptionPane.showMessageDialog(null,"The field cannot be left empty");
        cbAvailability.requestFocusInWindow();
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
        JOptionPane.showMessageDialog(null, "Venue already exists");
        }
        else if (boolRecordExists==false)
        {
        mCreateVenue();
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
    //  Update/Edit
    
    
          int selectedIndex = tblVenue.getSelectedRow();
    if (selectedIndex == -1) { // No row is selected
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
        JOptionPane.showMessageDialog(null, "Venue already exists");
        }
        else if (boolRecordExists==false)
        {
        mEditUpdate();
        mTableView();
        mClearTextFields();
    }
    }   
        
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        //Delete
         
         int selectedIndex = tblVenue.getSelectedRow();
    if (selectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }else{
      
    //Add confirmation JOptionPane dialog before deleting a record
 int response = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To DELETE this Venue",
        "Select An Option",JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE);
        
       if (response == JOptionPane.YES_OPTION){
        mGetValuesFromGUI();
        mDelete();  
        mTableView();
        mClearTextFields();
    }
       else{
           JOptionPane.showMessageDialog(null, "Delete Cancelled");
       }
    }    
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblVenueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVenueMouseClicked
         //selected row from table will appear on textfields 
      DefaultTableModel model = (DefaultTableModel) tblVenue.getModel();
      int selectedIndex = tblVenue.getSelectedRow();
      
      int intVenueID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
      txtVenueName.setText(model.getValueAt(selectedIndex, 1).toString());
      txtAddress.setText(model.getValueAt(selectedIndex, 2).toString());
      txtCapacity.setText(model.getValueAt(selectedIndex, 3).toString());
      cbAvailability.setSelectedItem(model.getValueAt(selectedIndex, 4));
    
     
    }//GEN-LAST:event_tblVenueMouseClicked

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
            java.util.logging.Logger.getLogger(venueFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(venueFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(venueFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(venueFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new venueFrm().setVisible(true);
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
    private javax.swing.JComboBox<String> cbAvailability;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblAvailability;
    private javax.swing.JLabel lblCapacity;
    private javax.swing.JLabel lblVenueManagement;
    private javax.swing.JLabel lblVenueName;
    private javax.swing.JTable tblVenue;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCapacity;
    private javax.swing.JTextField txtVenueName;
    // End of variables declaration//GEN-END:variables
}
