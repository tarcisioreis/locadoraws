/**
 *
 *		Classe DTO devolucao representando a tabela fisica no banco de dados
 *
 */
package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class Devolucao implements Serializable {

	private static final long serialVersionUID = 361377319722015128L;

	private int id;
	private int idFilme;
	private Date dataEntrega;
	
	public Devolucao() { }
	
	public Devolucao(int id, int idFilme, Date dataEntrega) {
		this.id = id;
		this.idFilme = idFilme;
		this.dataEntrega = dataEntrega;
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
	
	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
}
