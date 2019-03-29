/**
 *
 *		Classe DTO estoque representando a tabela fisica no banco de dados
 *
 */
package bean;

import java.io.Serializable;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class Estoque implements Serializable{

	private static final long serialVersionUID = -3016675658473013446L;

	private int id;
	private int idFilme;
	private int quantidade;

	public Estoque() { }
	
	public Estoque(int id, int idFilme, int quantidade) {
		this.id = id;
		this.idFilme = idFilme;
		this.quantidade = quantidade;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdFilme() {
		return idFilme;
	}
	public void setIdFilme(int idFilme) {
		this.idFilme = idFilme;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
