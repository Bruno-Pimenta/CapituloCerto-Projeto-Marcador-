/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Bruno
 */
public class Conexao {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/marcador_de_livros";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    
    public static Connection obterConexao(){
        try {
            if(connection == null || (connection != null && connection.isClosed())){
                connection = DriverManager.getConnection(URL, USUARIO,SENHA);
                return connection;
            }
                
        } catch (SQLException e) {
            System.out.println("Erro ao estabelecer conexão "+ e.getMessage());
        }
        return null;
    }
    
    public static void fecharConexao(){
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão "+ e.getMessage());
        }
    }
    
}
