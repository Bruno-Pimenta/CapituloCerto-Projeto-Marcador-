/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Bruno
 */
public class MarcadorSeries extends Marcador{
    private int numeroDeEpisodios;
    private int episodioAtual;

    public MarcadorSeries(String nome, int numeroDeEpisodios, int episodioAtual, String status, int nota, String anotacoes, String autores, String categorias) {
        super(nome, status, nota, anotacoes, autores, categorias);
        this.numeroDeEpisodios = numeroDeEpisodios;
        this.episodioAtual = episodioAtual;
    }
    
    public MarcadorSeries(int id, String nome, int numeroDeEpisodios, int episodioAtual, String status, int nota, String anotacoes, String autores, String categorias) {
        super(id, nome, status, nota, anotacoes, autores, categorias);
        this.numeroDeEpisodios = numeroDeEpisodios;
        this.episodioAtual = episodioAtual;
    }
       
    public int getNumeroDeEpisodios() {
        return numeroDeEpisodios;
    }

    public void setNumeroDeEpisodios(int numeroDeEpisodios) {
        this.numeroDeEpisodios = numeroDeEpisodios;
    }

    public int getEpisodioAtual() {
        return episodioAtual;
    }

    public void setEpisodioAtual(int episodioAtual) {
        this.episodioAtual = episodioAtual;
    }

    @Override
    public String toString() {
        return "Marcador de Livro: { "+
                "\n Id :"+super.getId()+
                "\n Nome :"+super.getNome()+
                "\n Número de páginas: "+getNumeroDeEpisodios()+
                "\n Página atual: "+getEpisodioAtual()+
                "\n Status: "+super.getStatus()+
                "\n Nota: "+super.getNota()+
                "\n Categorias: "+super.getCategorias()+
                "\n Autores: "+super.getAutores()+
                "\n Anotações "+super.getAnotacoes()+
                "\n}\n";  
    }
}
