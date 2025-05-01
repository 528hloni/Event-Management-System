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
         //Loading items from Event table(event name)
         
         String URL5 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User5 = "root"; //User name to connect to database
        String Password5 = "528_hloni"; //User password to connect to database
        java.sql.Connection conMySQLConnectionString; //Declares connection string named conMySQLConnectionString,it will contain the driver for the connection string to the database
        Statement stStatement = null; //Declares statement named stStatement which will contain sql statement
        ResultSet rs = null; //Declares statement named rs which will contain quiried data from the table
         
        try{
        conMySQLConnectionString = DriverManager.getConnection(URL5,User5,Password5); //used to gain access to database
            stStatement = conMySQLConnectionString.createStatement();//This will instruct stStatement to execute SQL statement against the table in database
            String strQuery = "SELECT event_name FROM event_details";
            stStatement.execute(strQuery);
         rs=stStatement.getResultSet();
        
        // Clear existing items in case of reloading
        cbEventSelection.removeAllItems();

        // Populate combo box with items from database
        while (rs.next()) {
            cbEventSelection.addItem(rs.getString("event_name"));
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading data", "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
     
     
      private void mSetvaluesToUpperCase(){
        //values will be uppercase
        strAttendeeName = strAttendeeName.toUpperCase();
        strEmail = strEmail.toUpperCase();
       
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
            String strQuery = " Select * from attendee_info where attendee_name = '" + strAttendeeName +
                    "' and e_mail= '" + strEmail + 
                    "' and contact_number= '"+ intContactNumber +
                    "'and event_selection='" + strEventSelection+"'";
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
       
       
        private void mCreateAttendee()
                //Create a new attendee
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL2 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User2 = "root"; //User name to connect to database
        String Password2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL2,User2,Password2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
            String sqlinsert = "insert into attendee_info (attendee_name,e_mail,contact_number,event_selection) " + //Initialises the 'insert sql statement' to store the values inserted in the textfield
            "values ('" + strAttendeeName + "','"+ strEmail+"','"+ intContactNumber + "','"+ strEventSelection +"')";
            myStatement.executeUpdate(sqlinsert); // Execute sql statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e );
        
        }
        
    }  
        
         private void mTableView(){
             //View table
             
     int CC; //Column count
     java.sql.Connection conMySQLConnectionString ;//Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
      String URL3 = "jdbc:mysql://localhost:3306/emsdb";//Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
     PreparedStatement pst;
        try {
         conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3);  //used to gain access to database 
         pst = conMySQLConnectionString.prepareStatement("SELECT * FROM attendee_info"); //SQL query to retrieve all records 
         ResultSet Rs1 = pst.executeQuery();// Execute the query and store result in ResultSet.
         
        // ResultSetMetaData object provides information about the structure of the result set (e.g., the number of columns).
         ResultSetMetaData RSMD = Rs1.getMetaData();
         CC = RSMD.getColumnCount(); //Store column count
         DefaultTableModel DFT = (DefaultTableModel) tblAttendees.getModel();
         DFT.setRowCount(0); // Set row count to 0 to start fresh.
           
         //Filling the the table
         while (Rs1.next()) {
             Vector v2 = new Vector(); //v2 holds passenger details
             
             for (int ii =1; ii <= CC; ii++){
                 v2.add(Rs1.getString("attendee_id"));
                 v2.add(Rs1.getString("attendee_name"));
                 v2.add(Rs1.getString("e_mail"));
                 v2.add(Rs1.getString("contact_number"));
                 v2.add(Rs1.getString("event_selection"));
             }
             DFT.addRow(v2);
         }        
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);  
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
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL4 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User4 = "root"; //User name to connect to database
        String Password4 = "528_hloni"; //User password to connect to database
        
         //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblAttendees.getModel();//Get model of table
      int intSelectedIndex = tblAttendees.getSelectedRow();
      int intAttendeeID = Integer.parseInt(model.getValueAt(intSelectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL4,User4,Password4); //used to gain access to database
            Statement euStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "Update attendee_info Set attendee_name = '" + strAttendeeName + 
                  "', e_mail = '" + strEmail + 
                  "', contact_number = '" + intContactNumber + 
                  "', event_selection = '" + strEventSelection + 
                  "' Where attendee_id = "+intAttendeeID;
            euStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            euStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }
   }
           
            private void mDelete()
            //Delete attendee        
   {
      java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
        
         DefaultTableModel model = (DefaultTableModel) tblAttendees.getModel();
      int intSelectedIndex = tblAttendees.getSelectedRow();
      int intAttendeeID = Integer.parseInt(model.getValueAt(intSelectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement dtStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "DELETE FROM attendee_info WHERE attendee_id = '" + intAttendeeID + "' AND attendee_name = '" + strAttendeeName + 
                          "' AND e_mail = '" + strEmail + "' AND contact_number = '" + intContactNumber + 
                          "' AND event_selection = '" + strEventSelection + "'";
            
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
