/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import civitas.*;
import javax.swing.JOptionPane;
/**
 *
 * @author david
 */
public class CivitasView extends javax.swing.JFrame {

   private CivitasJuego juego;
  private JugadorPanel jugadorPanel;
    GestionarDialog gestionarD = new GestionarDialog(this);
            
    
    void  setCivitasJuego(CivitasJuego game){
        this.juego = game;
        setVisible(true);
        
        jugadorPanel = new JugadorPanel();
        contenedorVistaJugador.add(jugadorPanel);
        
        this.repaint();
        this.revalidate();
    }
    
    public int getPropiedad(){
        return gestionarD.getPropiedad();
    }
    
    public int getGestion(){
        return gestionarD.getGestion();
    }
    
    void actualizarVista(){
        label_ranking.setVisible(false);
        ranking_text.setVisible(false);
        
        jugadorPanel.setJugador(juego.getJugadorActual());
        casilla_texto.setText(juego.getCasillaActual().toString());
        gestionarD.setVisible(false);
        
        if (juego.finalDelJuego()){
            label_ranking.setVisible(true);
            
            String ranking = "";
            for (Jugador i : this.juego.ranking()){
                ranking = i.toString()+"\n";
            }
            
            ranking_text.setText(ranking);
            ranking_text.setVisible(true);
        }
    }
    
    public CivitasView() {
        initComponents();
    }
    
    public Respuestas comprar(){
        int opcion = JOptionPane.showConfirmDialog(null,"¿Quieres comprar la calle actual?","Compra",JOptionPane.YES_NO_OPTION);
        Respuestas r;
        if (opcion == 0){
            r = Respuestas.SI;
        }
        else
            r = Respuestas.NO;
        
        return r;
    }
    
    public void gestionar(){
        gestionarD.gestionar(juego.getJugadorActual());
        gestionarD.pack();
        gestionarD.repaint();
        gestionarD.revalidate();
          setVisible(true);
         
                            
    }
    
    public void mostrarSiguienteOperacion(OperacionesJuego op){
        operacionJuego.setText(op.toString());
        this.actualizarVista();
    }
    
    public void mostrarEventos(){
        DiarioDialog diarioD = new DiarioDialog(this);
    }
    
    SalidasCarcel salirCarcel(){
        String [] opciones = {"opcion A", "opcion B"};
        int respuesta = JOptionPane.showOptionDialog(null, "¿Cómo quieres salir de la cárcel?", "Salir de la cárcel", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,opciones, opciones[0]);
        if(respuesta==0){
            return SalidasCarcel.TIRANDO;
        }
        else
            return SalidasCarcel.PAGANDO;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        contenedorVistaJugador = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        operacionJuego = new javax.swing.JTextField();
        label_ranking = new javax.swing.JLabel();
        ranking_text = new javax.swing.JTextField();
        label_casilla = new javax.swing.JLabel();
        casilla_texto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Civitas Souls");
        jLabel1.setEnabled(false);

        jLabel2.setText("Operacion:");
        jLabel2.setEnabled(false);

        operacionJuego.setText("jTextField1");
        operacionJuego.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        operacionJuego.setEnabled(false);
        operacionJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operacionJuegoActionPerformed(evt);
            }
        });

        label_ranking.setText("Ranking: ");
        label_ranking.setEnabled(false);

        ranking_text.setText("jTextField1");
        ranking_text.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ranking_text.setEnabled(false);

        label_casilla.setText("Casilla: ");
        label_casilla.setEnabled(false);

        casilla_texto.setText("jTextField1");
        casilla_texto.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        casilla_texto.setEnabled(false);
        casilla_texto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casilla_textoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(341, 341, 341)
                .addComponent(jLabel1)
                .addContainerGap(355, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(label_ranking)
                    .addComponent(label_casilla))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ranking_text, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(operacionJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(casilla_texto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contenedorVistaJugador, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(operacionJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_casilla)
                            .addComponent(casilla_texto, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ranking_text, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_ranking))))
                .addContainerGap())
        );

        operacionJuego.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void operacionJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operacionJuegoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_operacionJuegoActionPerformed

    private void casilla_textoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casilla_textoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_casilla_textoActionPerformed

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
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField casilla_texto;
    private javax.swing.JPanel contenedorVistaJugador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label_casilla;
    private javax.swing.JLabel label_ranking;
    private javax.swing.JTextField operacionJuego;
    private javax.swing.JTextField ranking_text;
    // End of variables declaration//GEN-END:variables
}