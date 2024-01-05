/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Bruno
 */
public class MarcadorLivros extends Marcador{
    private int numeroDePaginas;
    private int paginaAtual;

    public MarcadorLivros(int id, String nome, int numeroDePaginas, int paginaAtual, String status, float nota, String anotacoes, String autores, String categorias) {
        super(id, nome, status, nota, anotacoes, autores, categorias);
        this.numeroDePaginas = numeroDePaginas;
        this.paginaAtual = paginaAtual;
    }
    
    public MarcadorLivros(String nome, int numeroDePaginas, int paginaAtual, String status, float nota, String anotacoes, String autores, String categorias) {
        super(nome, status, nota, anotacoes, autores, categorias);
        this.numeroDePaginas = numeroDePaginas;
        this.paginaAtual = paginaAtual;
    }
    
    public int getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public void setNumeroDePaginas(int numeroDePaginas) {
        this.numeroDePaginas = numeroDePaginas;
    }

    public int getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(int paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    @Override
    public String toString() {
        return """
               Marcador de Livro: { 
                Id: """+super.getId()+
                "\n Nome: "+super.getNome()+
                "\n Número de páginas: "+getNumeroDePaginas()+
                "\n Página atual: "+getPaginaAtual()+
                "\n Status: "+super.getStatus()+
                "\n Nota: "+super.getNota()+
                "\n Categorias: "+super.getCategorias()+
                "\n Autores: "+super.getAutores()+
                "\n Anotações "+super.getAnotacoes()+
                "\n}\n";        
    }
}
