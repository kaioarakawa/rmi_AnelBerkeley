package anelDoFabioSockets;

public class Processo {

	private final int ID;
	private boolean coordenador;

	public Processo(int ID, boolean coordenador) {
		super();
		this.ID = ID;
		this.coordenador = coordenador;
	}

	public int getID() {
		return ID;
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean isCoordenador) {
		this.coordenador = isCoordenador;
	}


	public boolean realizarRequisicao() {
		for (Processo p : Server.processos) {
			if (p.isCoordenador()) {
				return p.requisicao();
			}
		}

		System.out.println("[Processo " + this.ID + "] Coodenador não encontrado");
		return false;
	}

	private boolean requisicao() {
		System.out.println("[Processo " + this.ID + "] Coordenador encontrado");
		return true;
	}

}
