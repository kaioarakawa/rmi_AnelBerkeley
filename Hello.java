package anelDoFabioSockets;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
	void criarProc(int a) throws RemoteException;
	void realizarRequisicao(int a) throws RemoteException;
	void inativarCoordenador(int a) throws RemoteException;
	void inativarProc(int a) throws RemoteException;
}
