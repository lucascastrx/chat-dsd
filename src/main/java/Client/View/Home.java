package Client.View;

import Client.Controller.ActionObserver;
import Client.Controller.ChatObserved;
import Client.Controller.Controller;
import Client.Controller.Observer;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Home extends javax.swing.JFrame implements Observer {
    
    private ActionObserver controller;

    public Home() {
        initComponents();
        this.controller = Controller.getInstance();
        this.controller.addObserver(this);
        this.controller.updateHomeScreen();
        this.controller.startListener();
        this.setVisible(true);
        
        setLocationRelativeTo(null);
    
        this.onlineContacts.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt){
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 1) {
                    int index = list.locationToIndex(evt.getPoint());
                    controller.clickedList(onlineContacts.getModel().getElementAt(index));
                }
            }
        });
        
    }

    public JLabel getLbName() {
        return lbName;
    }

    public JList<String> getOfflineContacts() {
        return offlineContacts;
    }

    public JList<String> getOnlineContacts() {
        return onlineContacts;
    }

    

    public JPanel getJpChat() {
        return jpChat;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jpChat = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        btnSend = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        imgStatus = new javax.swing.JLabel();
        lbContactName = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taMessage = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        onlineContacts = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        offlineContacts = new javax.swing.JList<>();
        jMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spyke App");
        setBackground(new java.awt.Color(51, 51, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo-100.png"))); // NOI18N

        lbName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbName.setForeground(new java.awt.Color(2, 175, 239));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbName.setText("Nome");

        jLabel4.setForeground(new java.awt.Color(2, 175, 239));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Contatos");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Online");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("v1.0");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/online.png"))); // NOI18N

        jpChat.setBackground(new java.awt.Color(255, 255, 255));
        jpChat.setForeground(new java.awt.Color(255, 255, 255));

        taChat.setEditable(false);
        taChat.setColumns(20);
        taChat.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        taChat.setRows(22);
        taChat.setBorder(null);
        taChat.setFocusable(false);
        taChat.setOpaque(false);
        jScrollPane2.setViewportView(taChat);

        btnSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/send-button.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/contact.png"))); // NOI18N

        lbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStatus.setText("Online");

        imgStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/online.png"))); // NOI18N

        lbContactName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbContactName.setForeground(new java.awt.Color(102, 102, 102));
        lbContactName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbContactName.setText("Nome");

        taMessage.setColumns(20);
        taMessage.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        taMessage.setRows(2);
        taMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        taMessage.setOpaque(false);
        jScrollPane3.setViewportView(taMessage);

        javax.swing.GroupLayout jpChatLayout = new javax.swing.GroupLayout(jpChat);
        jpChat.setLayout(jpChatLayout);
        jpChatLayout.setHorizontalGroup(
            jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpChatLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpChatLayout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend))
                    .addGroup(jpChatLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpChatLayout.createSequentialGroup()
                                .addComponent(imgStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jpChatLayout.setVerticalGroup(
            jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpChatLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpChatLayout.createSequentialGroup()
                        .addComponent(lbContactName, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(imgStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSend)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        onlineContacts.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        onlineContacts.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(onlineContacts);

        jTabbedPane1.addTab("Online", jScrollPane1);

        offlineContacts.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        offlineContacts.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(offlineContacts);

        jTabbedPane1.addTab("Offline", jScrollPane4);

        jMenu1.setText("File");
        jMenu.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu.add(jMenu2);

        setJMenuBar(jMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addComponent(jTabbedPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addGap(17, 17, 17))
            .addComponent(jpChat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Home().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnSend;
    private javax.swing.JLabel imgStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jpChat;
    private javax.swing.JLabel lbContactName;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JList<String> offlineContacts;
    private javax.swing.JList<String> onlineContacts;
    private javax.swing.JTextArea taChat;
    private javax.swing.JTextArea taMessage;
    // End of variables declaration//GEN-END:variables

    
    
    @Override
    public void updateName(String name) {
        this.lbName.setText(name);
    }

    @Override
    public void updateOnlineContacts(DefaultListModel list) {
        this.onlineContacts.setModel(list);
    }

    @Override
    public void updateOfflineContacts(DefaultListModel list) {
        this.offlineContacts.setModel(list);
    }

    @Override
    public void addMessageScreen(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }

    @Override
    public void addChat(JPanel jp, String id) {
        this.jpChat.add(jp, id);
    }

    @Override
    public void setChatLayout(CardLayout cl) {
        this.jpChat.setLayout(cl);
    }

    @Override
    public void showChat(CardLayout cl, String id) {
        cl.show(jpChat, id);
    }
} 
