package edu.ncsu.csc573.project.viewlayer.gui;

import edu.ncsu.csc573.project.commlayer.CommunicationServiceFactory;
import edu.ncsu.csc573.project.commlayer.ICommunicationService;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author krishna
 */
public class connect extends javax.swing.JFrame {
    private ICommunicationService commService;
    private Logger logger;
    
    /** Creates new form Connect and initializes the components*/
    public connect() {
        logger = Logger.getLogger(connect.class);
        initComponents();
        commService = CommunicationServiceFactory.getInstance();
    }

    private void actionToDo() {
        boolean isValidAddress = true;
        String ipAddress = ipaddtext.getText();
        String ipRegEx = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        try {
        isValidAddress = Pattern.matches(ipRegEx, ipAddress);
        } catch(NullPointerException excp){
          inValidIPAddressScreen();
          }
            
            if(!isValidAddress) {
                 inValidIPAddressScreen();
            } else {
                try {
                // initialize communication handler
                 commService.initialize(ipAddress, null);
                this.setVisible(false);
                Login loginFrame = new Login();
                loginFrame.setVisible(true);
                } catch (Exception e) {
                    inValidIPAddressScreen();
                    logger.error("Invalid ipaddress : " + ipAddress, e);
                }
            }
            System.out.println("IP Address is: " + ipAddress);
    }

    private void inValidIPAddressScreen() {
        InValidHostScreen inValidScreen = new InValidHostScreen();
        inValidScreen.setMessage("Invalid address");
        this.setVisible(false);
        inValidScreen.setVisible(true);
        inValidScreen.setLocationRelativeTo(this);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dgooglelabel = new javax.swing.JLabel();
        bsrlabel = new javax.swing.JLabel();
        connectbutton = new javax.swing.JButton();
        ipaddtext = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(475, 310, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        dgooglelabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        dgooglelabel.setText("DGOOGLE");

        bsrlabel.setText("Bootstrap server IP address");

        connectbutton.setText("Connect");
        connectbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectbuttonActionPerformed(evt);
            }
        });

        ipaddtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipaddtextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(bsrlabel)
                        .addGap(27, 27, 27)
                        .addComponent(ipaddtext, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(dgooglelabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(connectbutton)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(dgooglelabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsrlabel)
                    .addComponent(ipaddtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(connectbutton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void ipaddtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipaddtextActionPerformed


}//GEN-LAST:event_ipaddtextActionPerformed
 
private void connectbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectbuttonActionPerformed
        actionToDo();
}//GEN-LAST:event_connectbuttonActionPerformed

private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
// TODO add your handling code here:
    int enterKey = evt.getKeyCode();
    if (enterKey == KeyEvent.VK_ENTER){
    actionToDo();
    }
}//GEN-LAST:event_formKeyPressed






















/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {//System.out.println(javax.swing.UIManager.getInstalledLookAndFeels());
            /*for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }*/
                
         javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
     
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(connect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new connect().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bsrlabel;
    private javax.swing.JButton connectbutton;
    private javax.swing.JLabel dgooglelabel;
    private javax.swing.JFormattedTextField ipaddtext;
    // End of variables declaration//GEN-END:variables
}
