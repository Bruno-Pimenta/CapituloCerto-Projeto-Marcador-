/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import BancoDeDados.Conexao;
import Model.MarcadorLivros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException; 

/**
 *
 * @author Bruno
 */
public class MarcadorLivrosRepository {
    private static MarcadorLivrosRepository instancia;

    public MarcadorLivrosRepository() {
    }
    
    public static MarcadorLivrosRepository pegaInstancia(){
        if(instancia==null){
            instancia = new MarcadorLivrosRepository();
        }
        return instancia;
    }
    
    public void insereMarcadorLivros(MarcadorLivros marcador){
        Connection conexao = Conexao.obterConexao();
        String sql = "insert into marcador_livros(nome, numero_de_paginas, pagina_atual,"
                    + "status_atual, nota, anotacoes, autores, categorias)"
                    + "values( ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            //nome, numero_de_paginas, pagina_atual, status, nota, anotacoes, autores, categorias
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, marcador.getNome());
            st.setInt(2, marcador.getNumeroDePaginas());
            st.setInt(3, marcador.getPaginaAtual());
            st.setString(4, marcador.getStatus());
            st.setInt(5, marcador.getNota());
            st.setString(6, marcador.getAnotacoes());
            st.setString(7, marcador.getAutores());
            st.setString(8, marcador.getCategorias());
            st.executeUpdate();
            } catch (SQLException e) {
            System.out.println("Não foi possível realizar o registro " + e.getMessage());
        }
        finally{
            Conexao.fecharConexao();
        }
    }
}
