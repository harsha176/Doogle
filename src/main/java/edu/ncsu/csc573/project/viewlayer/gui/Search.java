/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Search.java
 *
 * Created on Oct 26, 2011, 1:55:55 AM
 */
package edu.ncsu.csc573.project.viewlayer.gui;

import edu.ncsu.csc573.project.commlayer.CommunicationServiceFactory;
import edu.ncsu.csc573.project.common.ByteOperationUtil;
import edu.ncsu.csc573.project.common.messages.EnumOperationType;
import edu.ncsu.csc573.project.common.messages.EnumParamsType;
import edu.ncsu.csc573.project.common.messages.IParameter;
import edu.ncsu.csc573.project.common.messages.IRequest;
import edu.ncsu.csc573.project.common.messages.IResponse;
import edu.ncsu.csc573.project.common.messages.LogoutRequestMessage;
import edu.ncsu.csc573.project.common.messages.Parameter;
import edu.ncsu.csc573.project.common.messages.PublishRequestMessage;
import edu.ncsu.csc573.project.common.messages.SearchRequestMessage;
import edu.ncsu.csc573.project.common.messages.SearchResponseMessage;
import edu.ncsu.csc573.project.common.schema.MatchMetricFileParamType;
import edu.ncsu.csc573.project.controllayer.Session;
import edu.ncsu.csc573.project.controllayer.hashspacemanagement.DigestAdaptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author krishna
 */
public class Search extends javax.swing.JFrame {

    /** Creates new form Search */
    public Search() {

        initComponents();
        // dispUsername.setText(Login.Username); 


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        searchText = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Options = new javax.swing.JMenu();
        Settings = new javax.swing.JMenuItem();
        Publish = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        Logout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(450, 250, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        searchText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextActionPerformed(evt);
            }
        });

        search.setText("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        Options.setText("Options");

        Settings.setText("Settings");
        Settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsActionPerformed(evt);
            }
        });
        Options.add(Settings);

        Publish.setText("Publish");
        Publish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PublishActionPerformed(evt);
            }
        });
        Options.add(Publish);

        jMenuItem1.setText("Unpublish");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        Options.add(jMenuItem1);

        Logout.setText("Logout");
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });
        Options.add(Logout);

        jMenuBar1.add(Options);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchText, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search)
                        .addGap(80, 80, 80)))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(search)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_searchTextActionPerformed

private void publishlinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_publishlinkActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_publishlinkActionPerformed

private void settingslinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingslinkActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_settingslinkActionPerformed

private void logoutlinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutlinkActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_logoutlinkActionPerformed

private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed

    try {
        IRequest searchRequest = new SearchRequestMessage();
        IParameter searchParams = new Parameter();
        searchParams.add(EnumParamsType.USERNAME, Session.getInstance().getUsername());
        searchParams.add(EnumParamsType.SEARCHKEY, ByteOperationUtil.convertBytesToString(DigestAdaptor.getInstance().getDigest(searchText.getText())));
        searchRequest.createRequest(EnumOperationType.SEARCH, searchParams);
        IResponse response = CommunicationServiceFactory.getInstance().executeRequest(searchRequest);
        
        
        /*PublishSearchParameter searchResults = (PublishSearchParameter)response.getParameter();
        searchResults.resetCounter();*/
        SearchResults newResults = new SearchResults();
        SearchResponseMessage srm = (SearchResponseMessage) response;
        List<MatchMetricFileParamType> unSortedResults = srm.getFiles();
        MatchMetricFileParamType[] arrResults = unSortedResults.toArray(new MatchMetricFileParamType[0]);
        
        Arrays.sort(arrResults, new MatchFactorComparator());
		
        newResults.setMessage(Arrays.asList(arrResults));
        this.setVisible(false);
        newResults.setVisible(true);
        newResults.setLocationRelativeTo(this);
        newResults.setTitle("Hello " + Session.getInstance().getUsername() + " , Welcome!!");
        
        
    } catch (IOException ex) {
        PublishFrame Searchfail = new PublishFrame();
        Searchfail.setMessage("Failed to create search request");
        Searchfail.setVisible(true);
        Searchfail.setLocationRelativeTo(this);
        Logger.getLogger(Search.class.getName()).error("Failed to create search request", ex);
    } catch (Exception e) {
        PublishFrame Searchfailure = new PublishFrame();
        Searchfailure.setMessage("Failed to send request to boot strap server");
        Searchfailure.setVisible(true);
        Searchfailure.setLocationRelativeTo(this);
        Logger.getLogger(Search.class.getName()).error("Failed to send request to boot strap server", e);
    }
}//GEN-LAST:event_searchActionPerformed

private void PublishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PublishActionPerformed
    try {
        Logger logger = Logger.getLogger(Search.class);
        IRequest pubRequest = PublishRequestMessage.getPublishRequest();
        IResponse response = CommunicationServiceFactory.getInstance().executeRequest(pubRequest);
        logger.info("Status of response is  : " + response.getStatus().getErrorId().toString());
        logger.info("Message is " + response.getMessage());
        PublishFrame Publish = new PublishFrame();
        Publish.setVisible(true);
        Publish.setLocationRelativeTo(this);
    } catch (IOException ex) {
        PublishFrame Pubfail = new PublishFrame();
        Pubfail.setMessage("Failed to create publish request");
        Pubfail.setVisible(true);
        Pubfail.setLocationRelativeTo(this);
        Logger.getLogger(Search.class.getName()).error("Failed to create publish request", ex);
    } catch (Exception e) {
        PublishFrame Publishfail = new PublishFrame();
        Publishfail.setMessage("Failed to create search request");
        Publishfail.setVisible(true);
        Publishfail.setLocationRelativeTo(this);
        Logger.getLogger(Search.class.getName()).error("Failed to send request to boot strap server", e);
    }
}//GEN-LAST:event_PublishActionPerformed

private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
// TODO add your handling code here:
    Logger logger = Logger.getLogger(Search.class);
    try {
        IRequest logoutRequest = new LogoutRequestMessage();
        IParameter Logoutparams = new Parameter();
        logger.debug(Session.getInstance().getUsername());
        Logoutparams.add(EnumParamsType.USERNAME, Session.getInstance().getUsername());
        logoutRequest.createRequest(EnumOperationType.LOGOUT, Logoutparams);
        IResponse response = CommunicationServiceFactory.getInstance().executeRequest(logoutRequest);
        loggedOut logout = new loggedOut();
        this.setVisible(false);
        logout.setVisible(true);
    } catch (Exception e) {
        logger.error("User not logged in", e);
    }
}//GEN-LAST:event_LogoutActionPerformed

private void SettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsActionPerformed
// TODO add your handling code here:
    SettingsFrame Settings = new SettingsFrame();
    //this.setVisible(false);
    Settings.setVisible(true);
}//GEN-LAST:event_SettingsActionPerformed

private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
// TODO add your handling code here:

    Unpublish remove = new Unpublish();
    remove.setVisible(true);
    remove.setLocationRelativeTo(this);

}//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Search.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new Search().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Logout;
    private javax.swing.JMenu Options;
    private javax.swing.JMenuItem Publish;
    private javax.swing.JMenuItem Settings;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JButton search;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables
}

class MatchFactorComparator implements Comparator<MatchMetricFileParamType> {
	public int compare(MatchMetricFileParamType o1, MatchMetricFileParamType o2) {
		if(o1.getMatchMetric() > o2.getMatchMetric()) {
			return 1;
		} else if(o1.getMatchMetric() > o2.getMatchMetric()){
			return -1;
		} else {
			return 0;
		}
	}	
}
