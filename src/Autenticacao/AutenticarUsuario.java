/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Autenticacao;

import BancoDeDados.Conexao;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
/**
 *
 * @author Bruno
 */
public class AutenticarUsuario {
    
    private static int userId = 0;
    
    public static String retornaHash(String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());
        return hash;
    }

    public static int getUserId() {
        return userId;
    }

    public static void resetUserId() {
        AutenticarUsuario.userId = 0;
    }
    
    
    
    public static void realizarLogin() throws SQLException{
        String nome, senha;
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome de usuário");
        nome = sc.nextLine();
        System.out.println("Digite a senha");
        senha = sc.nextLine();
        int idUser = AutenticarUsuario.verificarLogin(nome, senha);
        if(idUser>0){
            userId = idUser;
            System.out.println("Seja bem vindo(a) "+nome+"!!!");
        }
    }
    
    private static int verificarLogin(String usuarioNome, String password) throws SQLException{
        String sql = "Select id, nome, senha from usuario where nome =?";
        Connection conexao = Conexao.obterConexao();
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, usuarioNome);
        ResultSet rs = ps.executeQuery();
        int idUsuario=0;
        
        try {
            while(rs.next()){
                String hash=rs.getString("senha");
                if (BCrypt.checkpw(password, hash)) {
                    idUsuario=rs.getInt("id");
                    return idUsuario;
                } 
            }
        } catch (SQLException e) {
            System.out.println("Problema na verificação da senha "+ e.getMessage());
        } finally{
            Conexao.fecharConexao();
        }
        System.out.println("Senha incorreta!");
        return idUsuario;
    }
}
