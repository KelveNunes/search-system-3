package views;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.utils.Criptografar;
import model.utils.Load;

public class LoginController {
	
	@FXML
	private TextField txtUsuario;
	@FXML
	private TextField txtSenha;
	@FXML
	private Button btEntrar;
	
	Load lv = new Load();
	
	@FXML
	public void onBtEntrarAcction() throws NoSuchAlgorithmException {
		
		String usuario = txtUsuario.getText();
		String senha = txtSenha.getText();
		
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT * FROM USUARIOS WHERE USUARIO = ? AND SENHA = ?");
			pst.setString(1, usuario);
			String senhaEnc = Criptografar.cripografar(senha, "SHA1");
			pst.setString(2, senhaEnc);
			
			rs = pst.executeQuery();

			if (rs.next()) {
				if(rs.getInt("nivel") == 2) {
					lv.loadview("/views/Administrador.fxml");					
				}else {
					lv.loadview("/views/Busca.fxml");
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//@FXML 
	// void 
	
	@FXML 
	public void onLabelRecSenhaMouseClicked() {
		lv.loadview("/views/RecSenha.fxml");
	}
	
	

}
