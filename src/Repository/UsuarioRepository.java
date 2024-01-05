/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import BancoDeDados.Conexao;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Bruno
 */
public class UsuarioRepository {

    public UsuarioRepository() {
    }
    
    public void cadastrarUsuario(Usuario usuario){
        String sql = "insert into usuario(nome, senha) values(?, ?)";
        
        Connection conexao = Conexao.obterConexao();
        try {
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getSenha());
            int resultado = ps.executeUpdate();
            if(resultado>0){
                System.out.println("Cadastro realizado com sucesso!! ");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário "+e.getMessage());
        } finally{
            Conexao.fecharConexao();
        }
    }
}
