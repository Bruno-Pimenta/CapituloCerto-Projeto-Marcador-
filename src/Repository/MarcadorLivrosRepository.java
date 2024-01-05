/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import BancoDeDados.Conexao;
import Model.MarcadorLivros;
import Autenticacao.AutenticarUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        int idGerado = 0;
        String sql = "insert into marcador_livros(nome, numero_de_paginas, pagina_atual,"
                    + "status_atual, nota, anotacoes, autores, usuario_id)"
                    + "values( ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            //nome, numero_de_paginas, pagina_atual, status, nota, anotacoes, autores, categorias
            PreparedStatement st = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, marcador.getNome());
            st.setInt(2, marcador.getNumeroDePaginas());
            st.setInt(3, marcador.getPaginaAtual());
            st.setString(4, marcador.getStatus());
            st.setFloat(5, marcador.getNota());
            st.setString(6, marcador.getAnotacoes());
            st.setString(7, marcador.getAutores());
            st.setInt(8, AutenticarUsuario.getUserId());
            int linhasAfetadas = st.executeUpdate();
            if (linhasAfetadas > 0 && (marcador.getCategorias().equals("")==false)) {
                ResultSet chavesGeradas = st.getGeneratedKeys();
                if (chavesGeradas.next()) {
                    idGerado = chavesGeradas.getInt(1);
                }
            }
            if(!conexao.isClosed()){
                Conexao.fecharConexao();
            }
            if(idGerado>0 ){
                System.out.println("O registro foi realizado com sucesso!");
                associarTags(idGerado, marcador.getCategorias());
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível realizar o registro " + e.getMessage());
        }
    }
    
    private static void associarTags(int id, String tags) throws SQLException{
        Set<String> setTags = new HashSet<>();
        List<Integer> listaTagId = new ArrayList<>();
        String[] tagsSeparadas = null;
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet resultSetTag = null;
        try{
            tagsSeparadas = tags.toLowerCase().replaceAll("\\s*,\\s*", ",").split(",");
            for(String tagsAux: tagsSeparadas){
                setTags.add(tagsAux);
            }
            if(!(setTags.isEmpty())){
                try{
                    conexao = Conexao.obterConexao();
                    String sqlTags = "select id from palavras_chave where palavras_chave.nome like ?";
                    pst = conexao.prepareStatement(sqlTags);
                    
                    for(String tagsAux: setTags){
                        pst.setString(1, tagsAux);
                        resultSetTag = pst.executeQuery();
                        while (resultSetTag.next()) {
                            listaTagId.add(resultSetTag.getInt("id"));
                        }
                    }
                }
                catch (SQLException e) {
                    System.out.println("Não foi possível associar o livro às tags "  + e.getMessage());
                } 
                
                try{
                    if(!listaTagId.isEmpty()){
                        for(Integer idTag: listaTagId){
                            String sqlTags = "insert into marcador_livrospalavras_chave(marcador_livro_id, palavra_chave_id) values(?, ?)";
                            pst = conexao.prepareStatement(sqlTags);
                            pst.setLong(1, id);
                            pst.setLong(2, idTag);
                            pst.executeUpdate();
                        }
                    }
                }
                
                catch(SQLException e) {
                    System.out.println("Não foi possível associar o livro às tags "  + e.getMessage());
                }
            }
        }
        
        catch(NullPointerException e){
            System.out.println("Não foi encontrada tags para inserção " + e.getMessage());
        }
        
        finally{
            if (conexao != null && !conexao.isClosed()) {
            conexao.close();
            }
            if (pst != null && !pst.isClosed()) {
                pst.close();
            }
            if (resultSetTag != null && !resultSetTag.isClosed()) {
                resultSetTag.close();
            }
        }
    }
    
    public List<MarcadorLivros> buscaPorTags(String tags)throws SQLException{
        String[] tagsSeparadas = tags.toLowerCase().replaceAll("\\s*,\\s*", ",").split(",");
        
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet resultSetTag = null;
        List<MarcadorLivros> lista = new ArrayList<>();
        
        String parametro = "";
        
        if(tagsSeparadas.length==1){
            parametro = "?"; 
        }
        else if(tagsSeparadas.length>1){
            for(String ts : tagsSeparadas){
                parametro += "," + "?";
            }
            parametro = parametro.substring(1);
        }
        
        String sql = "select ml.id as id, ml.nome as nome, ml.numero_de_paginas as numero_de_paginas," +
            " ml.pagina_atual as pagina_atual, ml.status_atual as status_atual, ml.nota as nota, ml.anotacoes as anotacoes," +
            " ml.autores as autores, GROUP_CONCAT(t.nome) as tags" +
            " from marcador_livros as ml inner join marcador_livrospalavras_chave as mlpc on ml.id = mlpc.marcador_livro_id" +
            " inner join palavras_chave as t on t.id = mlpc.palavra_chave_id WHERE t.nome IN (" + parametro + ") AND usuario_id=?" +
            " GROUP BY ml.id, ml.nome, ml.numero_de_paginas, ml.pagina_atual, ml.status_atual, ml.nota, ml.anotacoes, ml.autores";
        
        try{
            conexao = Conexao.obterConexao();
            pst = conexao.prepareStatement(sql);
            for(int i=0; i<tagsSeparadas.length;i++){
                pst.setString(i+1, tagsSeparadas[i]);
            }
            pst.setInt((tagsSeparadas.length)+1,AutenticarUsuario.getUserId());
            resultSetTag = pst.executeQuery();
            
            while(resultSetTag.next()){
                int id = resultSetTag.getInt("id");
                String nome = resultSetTag.getString("nome");
                int numeroDePaginas = resultSetTag.getInt("numero_de_paginas");
                int paginaAtual = resultSetTag.getInt("pagina_atual");
                String status = resultSetTag.getString("status_atual");
                Float nota = resultSetTag.getFloat("nota");
                String anotacoes = resultSetTag.getString("anotacoes");
                String autores = resultSetTag.getString("autores");
                String categorias = resultSetTag.getString("tags");
                
                MarcadorLivros marcadorLivros = new MarcadorLivros(id, nome, numeroDePaginas, paginaAtual, status, nota,
                anotacoes, autores, categorias);
                lista.add(marcadorLivros);
            }
        }
        catch (SQLException e){
            System.out.println("Erro na consulta por categoria" + e.getMessage());
        }
        finally{
            if(conexao != null && !conexao.isClosed()){
                conexao.close();
            }
            if(pst != null && !pst.isClosed()){
                pst.close();
            }
            if(resultSetTag != null && !resultSetTag.isClosed()){
                resultSetTag.close();
            }
        }
        return lista;
    }
    
    public List<MarcadorLivros> buscarTodos()throws SQLException{
                
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet resultSetTag = null;
        List<MarcadorLivros> lista = new ArrayList<>();
        
        
        String sql = "select ml.id as id, ml.nome as nome, ml.numero_de_paginas as numero_de_paginas," +
            " ml.pagina_atual as pagina_atual, ml.status_atual as status_atual, ml.nota as nota, ml.anotacoes as anotacoes," +
            " ml.autores as autores, GROUP_CONCAT(t.nome) as tags" +
            " from marcador_livros as ml inner join marcador_livrospalavras_chave as mlpc on ml.id = mlpc.marcador_livro_id" +
            " inner join palavras_chave as t on t.id = mlpc.palavra_chave_id WHERE usuario_id = ?" +
            " GROUP BY ml.id, ml.nome, ml.numero_de_paginas, ml.pagina_atual, ml.status_atual, ml.nota, ml.anotacoes, ml.autores";
        
        try{
            conexao = Conexao.obterConexao();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, AutenticarUsuario.getUserId());
            resultSetTag = pst.executeQuery();
            
            while(resultSetTag.next()){
                int id = resultSetTag.getInt("id");
                String nome = resultSetTag.getString("nome");
                int numeroDePaginas = resultSetTag.getInt("numero_de_paginas");
                int paginaAtual = resultSetTag.getInt("pagina_atual");
                String status = resultSetTag.getString("status_atual");
                Float nota = resultSetTag.getFloat("nota");
                String anotacoes = resultSetTag.getString("anotacoes");
                String autores = resultSetTag.getString("autores");
                String categorias = resultSetTag.getString("tags");
                
                MarcadorLivros marcadorLivros = new MarcadorLivros(id, nome, numeroDePaginas, paginaAtual, status, nota,
                anotacoes, autores, categorias);
                lista.add(marcadorLivros);
            }
        }
        catch (SQLException e){
            System.out.println("Erro na consulta por categoria" + e.getMessage());
        }
        finally{
            if(conexao != null && !conexao.isClosed()){
                conexao.close();
            }
            if(pst != null && !pst.isClosed()){
                pst.close();
            }
            if(resultSetTag != null && !resultSetTag.isClosed()){
                resultSetTag.close();
            }
        }
        return lista;
    }
    
    public void excluir(int idMarcador) throws SQLException{
        MarcadorLivros marcadorLivros = buscarPorId(idMarcador);
        if(marcadorLivros!=null){
            excluirTags(idMarcador);
            Connection conexao = null;
            PreparedStatement ps = null;

            try{
                String sql = "delete from marcador_livros where id = ? and usuario_id=?";
                conexao = Conexao.obterConexao();
                ps = conexao.prepareStatement(sql);
                ps.setLong(1, idMarcador);
                ps.setInt(2, AutenticarUsuario.getUserId());
                ps.executeUpdate();
            }

            catch(SQLException e){
                System.out.println("Erro ao excluir marcador de livro" + e.getMessage());
            }

            finally{
                if(conexao!=null && !conexao.isClosed()){
                    conexao.close();
                }
                if(ps!=null && !ps.isClosed()){
                    ps.close();
                }
            }
        }
        else{
            System.out.println("Não existe marcador associado a este identificador");
        }
    }
            
    private static void excluirTags(int id) throws SQLException{
        Connection conexao = null;
        PreparedStatement ps = null;
                
        try{
            String sql = "delete from marcador_livrospalavras_chave where marcador_livro_id = ?";
            conexao = Conexao.obterConexao();
            ps = conexao.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        }
        
        catch(SQLException e){
            System.out.println("Erro ao excluir as tags" + e.getMessage());
        }
        
        finally{
            if(conexao!=null && !conexao.isClosed()){
                conexao.close();
            }
            if(ps!=null && !ps.isClosed()){
                ps.close();
            }
        }
    }
    
    
    public void atualizarPagina(int idMarcador, int paginaAtual) throws SQLException{
        Connection conexao = null;
        PreparedStatement ps = null;
        int retorno;
        String status;
        MarcadorLivros marcadorLivro;
        try {
            marcadorLivro = buscarPorId(idMarcador);
            float resultado = (float)(paginaAtual/marcadorLivro.getNumeroDePaginas());
            if(resultado==0){
               status="Leitura não inicializada"; 
            }
            else if(resultado>0&&resultado<1){
               status="Leitura em andamento"; 
            }
            else{
                status="Concluída";
            }
        } catch (SQLException e) {
            System.out.println("Marcador não encontrado");
            return;
        }
        
        try{
            String sql = "update marcador_livros set pagina_atual = ?, status_atual = ? "
                    + "where id = ? and usuario_id=?";
            conexao = Conexao.obterConexao();
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, paginaAtual);
            ps.setString(2, status);
            ps.setInt(3, idMarcador);
            ps.setInt(4, AutenticarUsuario.getUserId());
            retorno = ps.executeUpdate();
            if(retorno>0){
                System.out.println("Atualização feita com sucesso!");
            }
        }
        
        catch(SQLException e){
            System.out.println("Erro ao atualizar o marcador de livro" + e.getMessage());
        }
        
        finally{
            if(conexao!=null && !conexao.isClosed()){
                conexao.close();
            }
            if(ps!=null && !ps.isClosed()){
                ps.close();
            }
        }
    }
    
    public MarcadorLivros buscarPorId(int idMarcador)throws SQLException{
                
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet resultSetTag = null;
        
        String sql = "select ml.id as id, ml.nome as nome, ml.numero_de_paginas as numero_de_paginas," +
            " ml.pagina_atual as pagina_atual, ml.status_atual as status_atual, ml.nota as nota, ml.anotacoes as anotacoes," +
            " ml.autores as autores, GROUP_CONCAT(t.nome) as tags" +
            " from marcador_livros as ml inner join marcador_livrospalavras_chave as mlpc on ml.id = mlpc.marcador_livro_id" +
            " inner join palavras_chave as t on t.id = mlpc.palavra_chave_id" +
            " WHERE ml.id=? AND ml.usuario_id=?" +               
            " GROUP BY ml.id, ml.nome, ml.numero_de_paginas, ml.pagina_atual, ml.status_atual, ml.nota, ml.anotacoes, ml.autores";
        
        try{
            conexao = Conexao.obterConexao();
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, idMarcador);
            pst.setInt(2, AutenticarUsuario.getUserId());
            resultSetTag = pst.executeQuery();
            
            while(resultSetTag.next()){
                int id = resultSetTag.getInt("id");
                String nome = resultSetTag.getString("nome");
                int numeroDePaginas = resultSetTag.getInt("numero_de_paginas");
                int paginaAtual = resultSetTag.getInt("pagina_atual");
                String status = resultSetTag.getString("status_atual");
                Float nota = resultSetTag.getFloat("nota");
                String anotacoes = resultSetTag.getString("anotacoes");
                String autores = resultSetTag.getString("autores");
                String categorias = resultSetTag.getString("tags");
                
                MarcadorLivros marcadorLivros = new MarcadorLivros(id, nome, numeroDePaginas, paginaAtual, status, nota,
                anotacoes, autores, categorias);
                return marcadorLivros;
            }
        }
        catch (SQLException e){
            System.out.println("Erro na consulta por id" + e.getMessage());
        }
        finally{
            if(conexao != null && !conexao.isClosed()){
                conexao.close();
            }
            if(pst != null && !pst.isClosed()){
                pst.close();
            }
            if(resultSetTag != null && !resultSetTag.isClosed()){
                resultSetTag.close();
            }
        }
        return null;
    }
    
    public void atualizarAvaliacao(int id, Float nota) throws SQLException{
        Connection conexao = null;
        PreparedStatement ps = null;
        int retorno=0;
              
        try{
            String sql = "update marcador_livros set nota = ? "
                    + "where id = ? and usuario_id=?";
            conexao = Conexao.obterConexao();
            ps = conexao.prepareStatement(sql);
            ps.setFloat(1, (float) (Math.round(nota * 100.0) / 100.0));
            ps.setInt(2, id);
            ps.setInt(3, AutenticarUsuario.getUserId());
            retorno = ps.executeUpdate();
            if(retorno>0){
                System.out.println("Atualização feita com sucesso!");
            }
        }
        
        catch(SQLException e){
            System.out.println("Erro ao atualizar o marcador de livro" + e.getMessage());
        }
        
        finally{
            if(conexao!=null && !conexao.isClosed()){
                conexao.close();
            }
            if(ps!=null && !ps.isClosed()){
                ps.close();
            }
        }
    }
    
    public void atualizarAnotacao(int id, String anotacao) throws SQLException{
        Connection conexao = null;
        PreparedStatement ps = null;
        int retorno=0;
              
        try{
            String sql = "update marcador_livros set anotacoes = ? "
                    + "where id = ? and usuario_id=?";
            conexao = Conexao.obterConexao();
            ps = conexao.prepareStatement(sql);
            ps.setString(1, anotacao);
            ps.setInt(2, id);
            ps.setInt(3, AutenticarUsuario.getUserId());
            retorno = ps.executeUpdate();
            if(retorno>0){
                System.out.println("Atualização feita com sucesso!");
            }
        }
        
        catch(SQLException e){
            System.out.println("Erro ao atualizar o marcador de livro" + e.getMessage());
        }
        
        finally{
            if(conexao!=null && !conexao.isClosed()){
                conexao.close();
            }
            if(ps!=null && !ps.isClosed()){
                ps.close();
            }
        }
    }
    
    public List<MarcadorLivros> buscaPorAutor(String autorBuscado)throws SQLException{
                
        Connection conexao = null;
        PreparedStatement pst = null;
        ResultSet resultSetTag = null;
        List<MarcadorLivros> lista = new ArrayList<>();
                
        String sql = "select ml.id as id, ml.nome as nome, ml.numero_de_paginas as numero_de_paginas," +
            " ml.pagina_atual as pagina_atual, ml.status_atual as status_atual, ml.nota as nota, ml.anotacoes as anotacoes," +
            " ml.autores as autores, GROUP_CONCAT(t.nome) as tags" +
            " from marcador_livros as ml inner join marcador_livrospalavras_chave as mlpc on ml.id = mlpc.marcador_livro_id" +
            " inner join palavras_chave as t on t.id = mlpc.palavra_chave_id WHERE ml.autores LIKE ? AND usuario_id=?" +
            " GROUP BY ml.id, ml.nome, ml.numero_de_paginas, ml.pagina_atual, ml.status_atual, ml.nota, ml.anotacoes, ml.autores";
        
        try{
            conexao = Conexao.obterConexao();
            pst = conexao.prepareStatement(sql);
            pst.setString(1,autorBuscado);
            pst.setInt(2,AutenticarUsuario.getUserId());
            resultSetTag = pst.executeQuery();
            
            while(resultSetTag.next()){
                int id = resultSetTag.getInt("id");
                String nome = resultSetTag.getString("nome");
                int numeroDePaginas = resultSetTag.getInt("numero_de_paginas");
                int paginaAtual = resultSetTag.getInt("pagina_atual");
                String status = resultSetTag.getString("status_atual");
                Float nota = resultSetTag.getFloat("nota");
                String anotacoes = resultSetTag.getString("anotacoes");
                String autores = resultSetTag.getString("autores");
                String categorias = resultSetTag.getString("tags");
                
                MarcadorLivros marcadorLivros = new MarcadorLivros(id, nome, numeroDePaginas, paginaAtual, status, nota,
                anotacoes, autores, categorias);
                lista.add(marcadorLivros);
            }
        }
        catch (SQLException e){
            System.out.println("Erro na consulta por categoria" + e.getMessage());
        }
        finally{
            if(conexao != null && !conexao.isClosed()){
                conexao.close();
            }
            if(pst != null && !pst.isClosed()){
                pst.close();
            }
            if(resultSetTag != null && !resultSetTag.isClosed()){
                resultSetTag.close();
            }
        }
        return lista;
    }
}
