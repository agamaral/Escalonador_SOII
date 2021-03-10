public class Processo implements Comparable<Processo>{

	private int tempoDeChegada;
	private int duracaoDoProcesso;
	private int tempoDeEntrada;
	private int id; //id do processo
	private boolean ehAPrimeiraVez; //primiera vez q o processo entra na fila?

	public Processo(){
		this.tempoDeChegada = 0;
		this.duracaoDoProcesso = 0;
		this.tempoDeEntrada = 0;
		this.id = 0;
		this.ehAPrimeiraVez = true;
	}

	public void setTempoDeChegada(int tempoDeChegada){
		this.tempoDeChegada = tempoDeChegada;
	}

	public void setDuracaoDoProcesso(int duracaoDoProcesso){
		this.duracaoDoProcesso = duracaoDoProcesso;
	}

	public void setTempoDeEntrada(int tempoDeReentrada){
		this.tempoDeEntrada = tempoDeReentrada;
	}

	public int getTempoDeEntrada(){
		return this.tempoDeEntrada;
	}	

	public int getTempoDeChegada(){
		return this.tempoDeChegada;
	}
	public int getDuracaoDoProcesso(){
		return this.duracaoDoProcesso;
	}

	public void setID(int id){
		this.id = id;
	}

	public int getID(){
		return this.id;
	}

	public void setehAPrimeiraVez(boolean status){
		this.ehAPrimeiraVez = status;
	}

	public boolean getehAPrimeiraVez(){
		return this.ehAPrimeiraVez;
	}

	public String toString(){
		return ( this.tempoDeChegada + " "+ this.duracaoDoProcesso);
	}

	public boolean equals(Processo lvalue){
		return this.getDuracaoDoProcesso() == lvalue.getDuracaoDoProcesso();
	}

	public int compareTo(Processo lvalue){
		if(equals(lvalue))
			return 0;
		if(this.duracaoDoProcesso > lvalue.getDuracaoDoProcesso())
			return 1;
		else
			return -1;
	}
}