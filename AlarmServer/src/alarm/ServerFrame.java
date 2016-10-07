/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import util.Converter;

/**
 *
 * @author ConsejoSJ
 */
public class ServerFrame extends javax.swing.JFrame {

    ServerSocket providerSocket = null;
    Socket connection = null;
    PrintWriter out;
    InputStreamReader in;
    String message;
    boolean close_conn = false;
    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
    }

    
    public void startProcess(){
        try{
            close_conn = false;
            //1. creating a server socket
            providerSocket = new ServerSocket(8085, 10);
            //2. Wait for connection
            print("Waiting for connection",true);
            connection = providerSocket.accept();
            print("Connection received from " + connection.getInetAddress().getHostName(), true);
            //3. get Input and Output streams
            in = new InputStreamReader(connection.getInputStream());
            out = new PrintWriter(connection.getOutputStream(), true);
            //4. The two parts communicate via the input and output streams
            do{
                try{
                    int x = in.read();
                    StringBuilder sb = new StringBuilder();
                    boolean end = false;
                    while(x != -1 && !end){
                        sb.append((char) x);
                        if(sb.indexOf("$AP") != -1)
                            end = true;
                        else
                            x = in.read();
                    }
                    message = sb.toString();
                    if(!message.equals("")){
                        processMessage(message);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }while(!close_conn);
            print("Connection Thread Closed", true);
        }catch(IOException ioException){
            ioException.printStackTrace();
        }finally{
            //4: Closing connection
            try{
                in.close();
                out.close();
                connection.close();
                providerSocket.close();
            }catch(IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
    
    public void processMessage(String message){
        System.out.println(message);
        if(message.contains("#")){
            String[] split = message.split("\\$");
            if(split.length == 4)  //State
                printState(Protocol.decodeMessage(message));
            else
                print(message, false);
        }
        else{
            //Reply
            reply(message);
        }
    }
    
    public void reply(String msg){
        String[] split = msg.split("\\$");
        if(split.length == 4){ // Login / Hands Check / Close
            String imei = split[1];
            String code = split[2];
            if(code.equals("20") || code.equals("22")){
                print("Automatic Reply: "+ imei +"-"+ code, true);
                sendMessage("PA$"+ imei +"$"+ code +"$AP");
            }
            else{
                if(code.equals("21")){
                    closeConnection();
                }
                else{
                    print(msg, false);
                }
            }
        }
        if(split.length == 5){
            String imei = split[1];
            String code = split[2];
            String cmd = split[3];
            print("Command Reply: "+ imei +" -- S/N:"+ code +" -- CMD:"+ cmd ,false);
        }
    }
    
    // PRINT 
    
    public void print(String text, boolean server){
        StyledDocument doc = jTextPaneOutput.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        if(server)
            StyleConstants.setForeground(keyWord, Color.RED);
        else
            StyleConstants.setForeground(keyWord, Color.GREEN);
        //  Add some text
        try{
            doc.insertString(doc.getLength(), ">"+ text + System.getProperty("line.separator"), keyWord );
        }catch(Exception e) { 
            e.printStackTrace(); 
        }
        jTextPaneOutput.setCaretPosition(doc.getLength());
    }
    
    public void printState(AlarmState state){
        StyledDocument doc = jTextPaneOutput.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        try{
            doc.insertString(doc.getLength(), "#########################################################"+ System.getProperty("line.separator"), null);
            if(state != null){
                doc.insertString(doc.getLength(), Converter.dateTimeFormat(state.getDate()) + System.getProperty("line.separator"), null);
                StyleConstants.setForeground(keyWord, Color.GREEN);
                doc.insertString(doc.getLength(), "Position: "+ state.getLatitude() +":"+ state.getLongitude() + System.getProperty("line.separator"), keyWord);
                doc.insertString(doc.getLength(), "Battery Level: "+ state.getBattery() + System.getProperty("line.separator"), keyWord);
                doc.insertString(doc.getLength(), "Alarm Status: "+ state.getAlarmStatus() + System.getProperty("line.separator"), keyWord);
                doc.insertString(doc.getLength(), "System Status: "+ state.getSystemStatus() + System.getProperty("line.separator"), keyWord);
            }
            else{
                StyleConstants.setForeground(keyWord, Color.YELLOW);
                doc.insertString(doc.getLength(), "Invalid State" + System.getProperty("line.separator"), keyWord);
            }
            doc.insertString(doc.getLength(), "#########################################################"+ System.getProperty("line.separator"), null);
        }catch(Exception e){
            e.printStackTrace();
        }
        jTextPaneOutput.setCaretPosition(doc.getLength());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldInput = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneOutput = new javax.swing.JTextPane();
        jButtonStart = new javax.swing.JButton();
        jButtonQuery = new javax.swing.JButton();
        jButtonArm = new javax.swing.JButton();
        jButtonDisarm = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextFieldInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldInputKeyPressed(evt);
            }
        });

        jTextPaneOutput.setEditable(false);
        jScrollPane1.setViewportView(jTextPaneOutput);

        jButtonStart.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButtonStart.setForeground(new java.awt.Color(0, 153, 0));
        jButtonStart.setText("Start");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        jButtonQuery.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButtonQuery.setText("Params");
        jButtonQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonQueryActionPerformed(evt);
            }
        });

        jButtonArm.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButtonArm.setForeground(new java.awt.Color(0, 102, 255));
        jButtonArm.setText("ARM");
        jButtonArm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArmActionPerformed(evt);
            }
        });

        jButtonDisarm.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jButtonDisarm.setForeground(new java.awt.Color(255, 0, 0));
        jButtonDisarm.setText("DISARM");
        jButtonDisarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDisarmActionPerformed(evt);
            }
        });

        jButtonStop.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonStop.setForeground(new java.awt.Color(255, 0, 0));
        jButtonStop.setText("Stop");
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldInput, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonQuery)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonStop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonArm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDisarm)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonStart)
                    .addComponent(jButtonQuery)
                    .addComponent(jButtonArm)
                    .addComponent(jButtonDisarm)
                    .addComponent(jButtonStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldInputKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            processInput();
        }
    }//GEN-LAST:event_jTextFieldInputKeyPressed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        // TODO add your handling code here:
        jTextFieldInput.setText("START");
        processInput();
    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jButtonQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonQueryActionPerformed
        // TODO add your handling code here:
        jTextFieldInput.setText(Protocol.getMessage("", Protocol.QUERY_PARAMS));
        processInput();
    }//GEN-LAST:event_jButtonQueryActionPerformed

    private void jButtonArmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArmActionPerformed
        // TODO add your handling code here:
        jTextFieldInput.setText(Protocol.getMessage("", Protocol.ARM));
        processInput();
    }//GEN-LAST:event_jButtonArmActionPerformed

    private void jButtonDisarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDisarmActionPerformed
        // TODO add your handling code here:
        jTextFieldInput.setText(Protocol.getMessage("", Protocol.DISARM));
        processInput();
    }//GEN-LAST:event_jButtonDisarmActionPerformed

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
        // TODO add your handling code here:
        jTextFieldInput.setText("STOP");
        processInput();
    }//GEN-LAST:event_jButtonStopActionPerformed

    public void processInput(){
        String input = jTextFieldInput.getText();
        if(input.equals("START")){
            if(connection != null && !connection.isClosed()){
                JOptionPane.showMessageDialog(this,"Already Connected","Info",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                Thread thr = new Thread(new Runnable(){
                    public void run(){
                        startProcess();
                    }
                });
                thr.start();
            }
        }
        else{
            if(input.equals("STOP")){
                closeConnection();
            }
            else{
                if(!input.equals("")){
                    if(connection != null && connection.isConnected()){
                        sendMessage(input);
                    }
                    else{
                        JOptionPane.showMessageDialog(this,"Connection Closed","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        jTextFieldInput.setText("");
    }
    
    public void sendMessage(String msg){
        try{
            out.print(msg);
            out.flush();
            print(msg, true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void closeConnection(){
        try{
            close_conn = true;
            Thread.sleep(2000);
            try{
                in.close();
                out.close();
                if(!connection.isClosed())
                    connection.close();
                if(!providerSocket.isClosed())
                    providerSocket.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            print("Connection Closed", true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
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
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonArm;
    private javax.swing.JButton jButtonDisarm;
    private javax.swing.JButton jButtonQuery;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldInput;
    private javax.swing.JTextPane jTextPaneOutput;
    // End of variables declaration//GEN-END:variables
}
