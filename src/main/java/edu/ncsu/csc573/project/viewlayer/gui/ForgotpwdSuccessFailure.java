/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ForgotpwdSuccessFailure.java
 *
 * Created on Oct 30, 2011, 4:54:16 PM
 */
package edu.ncsu.csc573.project.viewlayer.gui;

/**
 *
 * @author krishna
 */
public class ForgotpwdSuccessFailure extends javax.swing.JFrame {

    /** Creates new form ForgotpwdSuccessFailure */
    public ForgotpwdSuccessFailure() {
        initComponents();
    }
    public void SuccessFrameTrue(){
        displayText.setText("Password mailed to the registered Email account");
        loginTryagain.setText("Login");
    }
    public void SuccessFrameFalse(){
        displayText.setText("Username does not exist in the database");
        loginTryagain.setText("Try again");
    }
/*    public void LoginFrameFalse(){
        displayText.setText("Incorrect Password");
        loginTryagain.setText("Try again");
    } */
    /** Tihis method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayText = new javax.swing.JLabel();
        loginTryagain = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(475, 310, 0, 0));
        setResizable(false);

        displayText.setText("Error Text");

        loginTryagain.setText("Login");
        loginTryagain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginTryagainActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginTryagain)
                    .addComponent(displayText))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(displayText)
                .addGap(18, 18, 18)
                .addComponent(loginTryagain)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void loginTryagainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginTryagainActionPerformed
// TODO add your handling code here:
    String loginortryagain = loginTryagain.getText();
    if ("login".equals(loginortryagain))
    {
     this.setVisible(false); 
     Login newLogin = new Login();
     newLogin.setVisible(true);
    }
     else {
    this.setVisible(false);
}
}//GEN-LAST:event_loginTryagainActionPerformed

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
            java.util.logging.Logger.getLogger(ForgotpwdSuccessFailure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForgotpwdSuccessFailure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForgotpwdSuccessFailure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForgotpwdSuccessFailure.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ForgotpwdSuccessFailure().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel displayText;
    private javax.swing.JButton loginTryagain;
    // End of variables declaration//GEN-END:variables
}
