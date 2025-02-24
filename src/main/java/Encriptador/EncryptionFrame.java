
package Encriptador;

import Interfaces.LoginFrame;

public class EncryptionFrame   {
    public EncryptionFrame() {
      //  setTitle("Encriptación de Datos");
   //     setSize(300, 150);
  //      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //      setLocationRelativeTo(null);

        if (CheckEncryptionStatus.isEncryptionComplete()) {
            
            new LoginFrame().setVisible(true);
        }else{
            EncryptionService.encryptPasswords();
            new LoginFrame().setVisible(true);

        }
        
        
        /*
        JButton encryptButton = new JButton("Encriptar Datos");
encryptButton.addActionListener(e -> {
    EncryptionService.encryptPasswords();
    if (CheckEncryptionStatus.isEncryptionComplete()) {
        dispose();
        new LoginFrame().setVisible(true);
    } else {
        JOptionPane.showMessageDialog(this, "Error: Encriptación incompleta");
    }
});

        add(encryptButton);
*/
    }
}