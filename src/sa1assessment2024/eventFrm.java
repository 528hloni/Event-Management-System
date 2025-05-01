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
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;





/**
 *
 * @author hloni
 */
public class eventFrm extends javax.swing.JFrame {

    /**
     * Creates new form eventFrm
     */
    public eventFrm() {
        initComponents();
    }
    
    Boolean boolRecordExists=false; //boolean will be used to check if record exists
    String strEventName;
    String strDate;
    String strTime;
    String strDescription;
    String strOrganiserDetails;
    
   
    
    
    private void mGetValuesFromGUI(){
        //values from the user
        
        
        try {
            strEventName = txtEventName.getText();
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");//format
        strDate = sdf1.format(dcDate.getDate());
        
        java.util.Date selectedTime = (java.util.Date)spnTime.getValue();
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");//format
        strTime = sdf2.format(selectedTime);
        
        strDescription = txtDescription.getText();
        strOrganiserDetails = txtOrganiserDetails.getText(); 
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Enter correct input "+""+e );
        }
       
    }
        
        
        private void mSetValuesToUpperCase(){
        //values will be uppercase
        strEventName = strEventName.toUpperCase();
        strDescription = strDescription.toUpperCase();
        strOrganiserDetails = strOrganiserDetails.toUpperCase();
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
            String strQuery = " Select * from event_details where event_name = '" + strEventName + //SQL statements
                "' and date= '" + strDate + 
                "' and time= '" + strTime +
                "' and description='" + strDescription+
                "' and organiser_details='"+strOrganiserDetails +"'";
            stStatement.execute(strQuery); // Execute sql statements against the database table
            rs=stStatement.getResultSet();
            boolRecordExists=rs.next(); //Confirm if the record exist or not in the database table
            
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
   private void mCreateEvent()
           //Creating new events
    {
        java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL2 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User2 = "root"; //User name to connect to database
        String Password2 = "528_hloni"; //User password to connect to database
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL2,User2,Password2); //used to gain access to database
            Statement myStatement = conMySQLConnectionString.createStatement(); 
            String sqlinsert = "insert into event_details (event_name,date,time,description,organiser_details) " + //Initialises the 'insert sql statement' to store the values inserted in the textfield
            "values ('" + strEventName + "','"+ strDate+"','"+ strTime + "','"+ strDescription +"','"+strOrganiserDetails +"')";
            myStatement.executeUpdate(sqlinsert); // Execute sql statements against the database table
            myStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Complete");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        
        }
        
    }
   
   private void mEditUpdate()
           //  Edit/Update existing events
   { 
    java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
        
        //Get selected row information
         DefaultTableModel model = (DefaultTableModel) tblEvents.getModel(); //Gets model of table
      int selectedIndex = tblEvents.getSelectedRow();
      int intEventID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement euStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "Update event_details Set event_name = '" + strEventName + 
                  "', date = '" + strDate + 
                  "', time = '" + strTime + 
                  "', description = '" + strDescription + 
                  "', organiser_details = '" + strOrganiserDetails + 
                  "' Where event_id = "+intEventID;
            euStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            euStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Updated");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }
   }
   
   
   private void mDelete()
           // Delete events
   {
      java.sql.Connection conMySQLConnectionString ; //Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
        String URL3 = "jdbc:mysql://localhost:3306/emsdb"; //Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
        
        //Gets selected row
         DefaultTableModel model = (DefaultTableModel) tblEvents.getModel();
      int selectedIndex = tblEvents.getSelectedRow();
      int intEventID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
      
        try {
            conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3); //used to gain access to database
            Statement dtStatement = conMySQLConnectionString.createStatement(); 
            String strQuery = "DELETE FROM event_details WHERE event_id = '" + intEventID +
                    "' AND event_name = '" + strEventName + 
                    "' AND date = '" + strDate + 
                    "' AND description = '" + strDescription + 
                    "' AND organiser_details = '" + strOrganiserDetails + "'";
            
            dtStatement.executeUpdate(strQuery); // Execute sql statements against the database table
            dtStatement.close(); //Close connection of the database
            JOptionPane.showMessageDialog(null,"Record Deleted");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
           
        }  
       
       
   }       
   
   private void mTableView(){
       //View the table
       
     int CC; //Column count
     java.sql.Connection conMySQLConnectionString ;//Declares connection string named conMySQLConnectionString, it will contain the driver for the connection string to the database
      String URL3 = "jdbc:mysql://localhost:3306/emsdb";//Connection string to the database
        String User3 = "root"; //User name to connect to database
        String Password3 = "528_hloni"; //User password to connect to database
     PreparedStatement pst;
     
        try {
         conMySQLConnectionString = DriverManager.getConnection(URL3,User3,Password3);  //used to gain access to database 
         pst = conMySQLConnectionString.prepareStatement("SELECT * FROM event_details"); //SQL query to retrieve all records from table
         
         ResultSet Rs1 = pst.executeQuery();// Execute the query and store result in ResultSet.
         
        // ResultSetMetaData object provides information about the structure of the result set (e.g., the number of columns).
         ResultSetMetaData RSMD = Rs1.getMetaData();
         CC = RSMD.getColumnCount(); //Store column count
         DefaultTableModel DFT = (DefaultTableModel) tblEvents.getModel();
         DFT.setRowCount(0); // Set row count to 0 to start fresh.
           
         //Filling the the table
         while (Rs1.next()) {
             Vector v2 = new Vector(); //v2 holds passenger details
             
             for (int ii =1; ii <= CC; ii++){
                 v2.add(Rs1.getString("event_id"));
                 v2.add(Rs1.getString("event_name"));
                 v2.add(Rs1.getString("date"));
                 v2.add(Rs1.getString("time"));
                 v2.add(Rs1.getString("description"));
                 v2.add(Rs1.getString("organiser_details"));
             }
             DFT.addRow(v2);
         }        
        } catch (Exception e) {
          JOptionPane.showMessageDialog(null, e);  
        }
   }
   
    private void mClearTextFields(){ //This will clear textfields once the values have been captured
        txtEventName.setText("");
        dcDate.setDate(null);
        txtDescription.setText("");
        txtOrganiserDetails.setText("");
        
    }
        
        
        
        
        
        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblEventPlanning = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblEventName = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblOrganiserDetails = new javax.swing.JLabel();
        txtEventName = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtOrganiserDetails = new javax.swing.JTextArea();
        dcDate = new com.toedter.calendar.JDateChooser();
        spnTime = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEvents = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        btnView = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 153));

        lblEventPlanning.setBackground(new java.awt.Color(0, 0, 204));
        lblEventPlanning.setFont(new java.awt.Font("PMingLiU-ExtB", 3, 24)); // NOI18N
        lblEventPlanning.setForeground(new java.awt.Color(255, 255, 255));
        lblEventPlanning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEventPlanning.setText("EVENT   PLANNING");
        lblEventPlanning.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        lblEventPlanning.setOpaque(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));

        lblEventName.setForeground(new java.awt.Color(0, 0, 255));
        lblEventName.setText("Event Name");

        lblDate.setForeground(new java.awt.Color(0, 0, 255));
        lblDate.setText("Date");

        lblTime.setBackground(new java.awt.Color(255, 255, 255));
        lblTime.setForeground(new java.awt.Color(0, 0, 204));
        lblTime.setText("Time");

        lblDescription.setForeground(new java.awt.Color(0, 0, 204));
        lblDescription.setText("Description");

        lblOrganiserDetails.setForeground(new java.awt.Color(0, 0, 204));
        lblOrganiserDetails.setText("Organiser Details");

        txtEventName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));

        txtDescription.setColumns(20);
        txtDescription.setRows(5);
        txtDescription.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));
        jScrollPane2.setViewportView(txtDescription);

        txtOrganiserDetails.setColumns(20);
        txtOrganiserDetails.setRows(5);
        txtOrganiserDetails.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));
        jScrollPane3.setViewportView(txtOrganiserDetails);

        dcDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));

        spnTime = new JSpinner(new SpinnerDateModel());
        spnTime.setEditor(new JSpinner.DateEditor(spnTime,"HH:mm"));
        spnTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 153)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEventName, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))
                    .addComponent(lblTime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblOrganiserDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEventName, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(spnTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEventName)
                    .addComponent(txtEventName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDate)
                    .addComponent(dcDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime)
                    .addComponent(spnTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOrganiserDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblEvents.setBackground(new java.awt.Color(0, 0, 255));
        tblEvents.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 1, true));
        tblEvents.setForeground(new java.awt.Color(255, 255, 255));
        tblEvents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Event Name", "Date", "Time", "Description", "Organiser Details"
            }
        ));
        tblEvents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEventsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblEvents);

        btnSave.setBackground(new java.awt.Color(0, 0, 255));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Save");
        btnSave.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(0, 0, 204));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(0, 0, 204));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 0, 204));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnReturn.setBackground(new java.awt.Color(0, 0, 204));
        btnReturn.setForeground(new java.awt.Color(255, 255, 255));
        btnReturn.setText("Return");
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });

        btnView.setBackground(new java.awt.Color(0, 0, 204));
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
                .addGap(133, 133, 133)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(284, 284, 284)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEdit)
                    .addComponent(btnView))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete)
                    .addComponent(btnReturn))
                .addGap(114, 114, 114))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEventPlanning, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblEventPlanning, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnView)
                    .addComponent(btnReturn))
                .addGap(80, 80, 80))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        
         mainFrm frmM = new mainFrm();
       frmM.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
      //Create
        

        //Validation of inputs
      if(txtEventName.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null,"The Event Name field cannot be left empty");
            txtEventName.requestFocusInWindow();
        }
        else if(dcDate.getDate() == null)
        {
        JOptionPane.showMessageDialog(null,"The Date field cannot be left empty");
        dcDate.requestFocusInWindow();
        }
        else if(txtDescription.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The Description field cannot be left empty");
        txtDescription.requestFocusInWindow();
        }
        else if(txtOrganiserDetails.getText().equals(""))
        {
        JOptionPane.showMessageDialog(null,"The  Organiser field cannot be left empty");
        txtOrganiserDetails.requestFocusInWindow();
        }
        else
        {
        mGetValuesFromGUI();
        mSetValuesToUpperCase();
        mCheckIfItemsExistInTable();
        
        //if the record exist then show jOptionPane message then set boolean record to false
        //if the record doesnt exist then create passeneger,update the table then clear textfields
        if(boolRecordExists==true)
        {
        boolRecordExists=false;
        JOptionPane.showMessageDialog(null, "Event already exists");
        }
        else if (boolRecordExists==false)
        {
        mCreateEvent();
        mTableView();
        mClearTextFields();
        }    
        }   
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblEventsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEventsMouseClicked
      //selected row from table will appear on textfields 
      DefaultTableModel model = (DefaultTableModel) tblEvents.getModel();
      int selectedIndex = tblEvents.getSelectedRow();
      
      int intEventID = Integer.parseInt(model.getValueAt(selectedIndex,0).toString());
      txtEventName.setText(model.getValueAt(selectedIndex, 1).toString());
      
  
      String dateString = model.getValueAt(selectedIndex, 2).toString();
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the format to match your date string
    
    try {
        Date dateValue = sdf3.parse(dateString); // Convert string to Date
        dcDate.setDate(dateValue); // Set the date in JDateChooser
    } catch (ParseException e) {
        e.printStackTrace();
    }
    
    
     String timeString = model.getValueAt(selectedIndex, 3).toString();
    SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm"); // Adjust format to match your time string
    try {
        Date timeValue = sdf4.parse(timeString); // Convert time string to Date
        spnTime.setValue(timeValue); // Set the time in JSpinner
    } catch (ParseException e) {
        e.printStackTrace();
    }
    
      
      
      txtDescription.setText(model.getValueAt(selectedIndex, 4).toString());
      txtOrganiserDetails.setText(model.getValueAt(selectedIndex,5).toString());
    }//GEN-LAST:event_tblEventsMouseClicked

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        //View
        
        
        mTableView();
    }//GEN-LAST:event_btnViewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        //Edit 
        
        //Ensures a row is selected to edit selected row
        int selectedIndex = tblEvents.getSelectedRow();
    if (selectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to edit.");
    }else{
        mGetValuesFromGUI();
         mSetValuesToUpperCase();
        mCheckIfItemsExistInTable();
        
        
        //if the record exist then show jOptionPane message then set boolean record to false
        //if the record doesnt exist then create Event,update the table then clear textfields
        if(boolRecordExists==true)
        {
        boolRecordExists=false;
        JOptionPane.showMessageDialog(null, "Event already exists");
        }
        else if (boolRecordExists==false)
        {
        mEditUpdate();
       
        mTableView();
        mClearTextFields();
        }
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        //Clear textfields
        
        
        mClearTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
           //Delete

        
        
         int selectedIndex = tblEvents.getSelectedRow();
    if (selectedIndex == -1) { // No row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
    }else{
        mGetValuesFromGUI();
        
         //Add confirmation JOptionPane dialog before deleting a record
         int response = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To DELETE this Event",
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
            java.util.logging.Logger.getLogger(eventFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(eventFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(eventFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(eventFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new eventFrm().setVisible(true);
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
    private com.toedter.calendar.JDateChooser dcDate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblEventName;
    private javax.swing.JLabel lblEventPlanning;
    private javax.swing.JLabel lblOrganiserDetails;
    private javax.swing.JLabel lblTime;
    private javax.swing.JSpinner spnTime;
    private javax.swing.JTable tblEvents;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtEventName;
    private javax.swing.JTextArea txtOrganiserDetails;
    // End of variables declaration//GEN-END:variables
}
