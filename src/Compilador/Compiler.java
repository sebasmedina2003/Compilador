package Compilador;

import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Compiler extends javax.swing.JFrame {
    
    private void init(){
        this.title = "Compilador para Mythos";
        setLocationRelativeTo(null);
        setTitle(title);
        this.directorio = new Directory(this, this.codeEditor, this.title, ".mth");
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                directorio.Exit();
                System.exit(0);
            }
        });
        
        Functions.setLineNumberOnJTextComponent(this.codeEditor, WIDTH, Color.blue);
        
        this.timerKeyRelesed = new Timer(300, ((e) -> {
            this.timerKeyRelesed.stop();
            this.colorAnalysis();
        }));
        
        Functions.insertAsteriskInName(this, this.codeEditor, () -> {
            this.timerKeyRelesed.restart();
        });
        
        this.tokens = new ArrayList<>();
        this.errors = new ArrayList<>();
        this.textColors = new ArrayList<>();
        this.idenProd = new ArrayList<>();
        this.identificadores = new HashMap<>();
        this.palabrasReservadas = new String[]{
            "read", "write", "if", "else", "elif","while", "for",
            "break", "continue", "return", "class", "import", "from",
            "True", "False", "None", "lambda", "try","function", "and",
            "not", "is", "or", "pass", "except", "in"
        };
        
        Functions.setAutocompleterJTextComponent(this.palabrasReservadas, this.codeEditor, ()-> {
            this.timerKeyRelesed.restart();
        });
        
    }
    
    private void colorAnalysis(){
        LexerColor lexer;
        
        try{
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = this.codeEditor.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF-8"));
   
            lexer = new LexerColor(entrada);
             while(true){
                 TextColor textColor = lexer.yylex();
                 
                 if(textColor == null){ break; }
                 this.textColors.add(textColor);
             }
            
        }catch(FileNotFoundException e){
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, e);
        }catch(IOException e){
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, e);
        }
        
        Functions.colorTextPane(this.textColors, this.codeEditor, new Color(40, 40, 40));
    }
    
    private void clearFields(){
        this.terminal.setText("");
        this.tokens.clear();
        this.errors.clear();
        this.idenProd.clear();
        this.identificadores.clear();
    }
    
    private void lexicalAnalysis() throws FileNotFoundException{
        Lexer lexer;
        
        try{
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = this.codeEditor.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF-8"));
   
            lexer = new Lexer(entrada);
             while(true){
                 Token token = lexer.yylex();
                 
                 if(token == null){ break; }
                 this.tokens.add(token);
             }
            
        }catch(FileNotFoundException e){
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, e);
        }catch(IOException e){
            Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void printTokens(){
        Object[] data = new Object[]{};
        String strTokens = "";
        
        for(Token token: this.tokens){
                data = new Object[]{
                token.getLexicalComp(), token.getLexeme(), 
                "[" + token.getLine() + ", " + token.getColumn() + "]"
            };
            strTokens += Arrays.toString(data) + "\n";
        }
        this.terminal.setText(strTokens);
    }
    
    private void syntaticAnalysis(){
        Grammar gramatica = new Grammar(this.tokens, errors);
        gramatica.show();
    }
    
    private void semanticAnalysis(){
    
    }
    
    private void printConsole(){
        int sizeErrors = this.errors.size();
        
        if(sizeErrors > 0){
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            
            for(ErrorLSSL error: errors){
                strErrors += String.valueOf(error) + "\n";
            }
            this.terminal.setText(this.terminal.getText() + "\nCompilacion terminada con errores...\n" + strErrors);
        } else{
            this.terminal.setText(this.terminal.getText() + "\nCompilacion terminada...");
        }
    }
    
    private void compile() throws FileNotFoundException{
        this.clearFields();
        this.lexicalAnalysis();
        this.printTokens();
        this.syntaticAnalysis();
        this.semanticAnalysis();
        this.printConsole();
        this.codeHasBeenCompiled = true;
    }
    
    public Compiler() {
        initComponents();
        this.init();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        terminal = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        codeEditor = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuOpenFile = new javax.swing.JMenuItem();
        MenuCloseFile = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuSaveChanges = new javax.swing.JMenuItem();
        menuSaveFile = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuCompile = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuRunFile = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPILADOR PARA EL LENGUAJE MYTHOS");

        terminal.setColumns(20);
        terminal.setFont(new java.awt.Font("Cascadia Code", 0, 14)); // NOI18N
        terminal.setRows(5);
        jScrollPane2.setViewportView(terminal);

        jLabel2.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Terminal");

        jLabel1.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Unsaved file");

        jScrollPane1.setViewportView(codeEditor);

        jMenu1.setText("File");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenu1.setIconTextGap(12);
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenu1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenu1MouseExited(evt);
            }
        });

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem1.setText("New File");
        jMenuItem1.setToolTipText("");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        menuOpenFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuOpenFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuOpenFile.setText("Open File");
        menuOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOpenFileActionPerformed(evt);
            }
        });
        jMenu1.add(menuOpenFile);

        MenuCloseFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        MenuCloseFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MenuCloseFile.setText("Close File");
        MenuCloseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuCloseFileActionPerformed(evt);
            }
        });
        jMenu1.add(MenuCloseFile);
        jMenu1.add(jSeparator2);

        menuSaveChanges.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSaveChanges.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuSaveChanges.setText("Save Changes");
        menuSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveChangesActionPerformed(evt);
            }
        });
        jMenu1.add(menuSaveChanges);

        menuSaveFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuSaveFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuSaveFile.setText("Save As");
        menuSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSaveFileActionPerformed(evt);
            }
        });
        jMenu1.add(menuSaveFile);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem3.setText("Undo");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem4.setText("Redo");
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator5);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem8.setText("Copy");
        jMenu2.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem9.setText("Cut");
        jMenu2.add(jMenuItem9);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem10.setText("Paste");
        jMenuItem10.setToolTipText("");
        jMenu2.add(jMenuItem10);
        jMenu2.add(jSeparator3);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem7.setText("Find");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Run");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        menuCompile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        menuCompile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuCompile.setText("Compile File");
        menuCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCompileActionPerformed(evt);
            }
        });
        jMenu3.add(menuCompile);
        jMenu3.add(jSeparator4);

        menuRunFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menuRunFile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuRunFile.setText("Run File");
        menuRunFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRunFileActionPerformed(evt);
            }
        });
        jMenu3.add(menuRunFile);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2)))
                .addGap(25, 25, 25))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator6)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(448, 448, 448)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(435, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOpenFileActionPerformed
        if(this.directorio.Open()){
            this.colorAnalysis();
            this.clearFields();
        }
    }//GEN-LAST:event_menuOpenFileActionPerformed

    private void menuSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveChangesActionPerformed
        if(this.directorio.Save()){
            this.clearFields();
        }
    }//GEN-LAST:event_menuSaveChangesActionPerformed

    private void MenuCloseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuCloseFileActionPerformed

    }//GEN-LAST:event_MenuCloseFileActionPerformed

    private void menuSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSaveFileActionPerformed
        if(this.directorio.SaveAs()){
            this.clearFields();
        }
    }//GEN-LAST:event_menuSaveFileActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        System.out.print("Find");
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void menuCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCompileActionPerformed
        if(this.getTitle().contains("*") || this.getTitle().equals(this.title)){
            if(this.directorio.Save()){
                try {
                    this.compile();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else{
            try {
                this.compile();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuCompileActionPerformed

    private void menuRunFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRunFileActionPerformed
        this.menuCompile.doClick();
        if(this.codeHasBeenCompiled && this.errors.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se puede ejecutar el codigo porque tiene uno o mas errores");
        }
    }//GEN-LAST:event_menuRunFileActionPerformed

    private void jMenu1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseEntered
        
    }//GEN-LAST:event_jMenu1MouseEntered

    private void jMenu1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseExited
      
    }//GEN-LAST:event_jMenu1MouseExited

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
            java.util.logging.Logger.getLogger(Compiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compiler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    new Compiler().setVisible(true);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Compiler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuCloseFile;
    private javax.swing.JTextPane codeEditor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JMenuItem menuCompile;
    private javax.swing.JMenuItem menuOpenFile;
    private javax.swing.JMenuItem menuRunFile;
    private javax.swing.JMenuItem menuSaveChanges;
    private javax.swing.JMenuItem menuSaveFile;
    private javax.swing.JTextArea terminal;
    // End of variables declaration//GEN-END:variables
    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textColors;
    private Timer timerKeyRelesed;
    private ArrayList<Production> idenProd;
    private HashMap<String, String> identificadores;
    private String[] palabrasReservadas;
    private boolean codeHasBeenCompiled = false;
}
