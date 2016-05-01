package ordenacao;

import java.util.List;



/**
 * @author adrianopatrick@gmail.com
 * @since 30 de mar de 2016
 */
public class Tarefa {
	
	/***/
	private Integer idTarefa;
	private Integer qtdaDependencias;
	private List<Tarefa> listaDependencias;
	
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public Integer getQtdaDependencias() {
		return qtdaDependencias;
	}
	public void setQtdaDependencias(Integer qtdaDependencias) {
		this.qtdaDependencias = qtdaDependencias;
	}
	public List<Tarefa> getListaDependencias() {
		return listaDependencias;
	}
	public void setListaDependencias(List<Tarefa> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}
	
}