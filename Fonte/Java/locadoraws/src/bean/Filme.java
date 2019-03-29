/**
 *
 *		Classe DTO filme representando a tabela fisica no banco de dados
 *
 */
package bean;

import java.io.Serializable;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class Filme implements Serializable {

	private static final long serialVersionUID = 973983591984530823L;

	private int id;
	private String titulo;
	private String diretor;
	private int locado;
	
	public Filme() { }
	
	public Filme(int id, String titulo, String diretor) {
		this.id = id;
		this.titulo = titulo;
		this.diretor = diretor;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDiretor() {
		return diretor;
	}
	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

/*
 * 
 *   Mostra se filme esta disponivel ou em locação
 * 	
 */
	public int getLocado() {
		return locado;
	}
	public void setLocado(int locado) {
		this.locado = locado;
	}
	
}
