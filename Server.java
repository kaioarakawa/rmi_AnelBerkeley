package anelDoFabioSockets;

import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class Server implements Hello {
	
	public static ArrayList<Processo> processos;
	public static Object lock;
	public static int Identity = 0;

	public static void main(String args[]) {

		try {
			Server obj = new Server();
			Hello stub =  (Hello) UnicastRemoteObject.exportObject(obj, 0);

			// especifica que o servidor está com publicado com este IP
			System.setProperty("java.rmi.server.hostname", "192.168.1.112");

			// conecta ao rmiregistry
			Registry registry = LocateRegistry.getRegistry();
			// registra o serviço no registry com o nome de "Anel"
			registry.bind("Anel", stub);

			System.err.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void criarProc(int a) throws RemoteException {
		lock = new Object();

		new Thread(() -> {
			for (int i = 0; i < 8; i++) {
				synchronized (lock) {
					Identity++;
					processos.add(new Processo(Identity, false));
					System.out.println("[Processo " + Identity + "] Processo iniciado");
				}
				try {
					Thread.sleep(a);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void realizarRequisicao(int a) throws RemoteException {
		// TODO Auto-generated method stub
		new Thread(() -> {
			for (int i = 0; i < 8; i++) {
				try {
					Thread.sleep(a);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					int random = new Random().nextInt(processos.size());
					Processo p = processos.get(random);

					boolean existeCoordenador = p.realizarRequisicao();

					if (!existeCoordenador) {
						realizaEleicao();
					}
				}
			}
		}).start();
		
	}
	
	private static void realizaEleicao() {
		Processo novoCoordenador = null;
		int Identity = 0;
		for (Processo processo : processos) {
			if (processo.getID() > Identity) {
				novoCoordenador = processo;
			}
		}

		novoCoordenador.setCoordenador(true);
		System.out.println("Coordenador: [Processo " + novoCoordenador.getID() + "]");
	}

	@Override
	public void inativarCoordenador(int a) throws RemoteException {
		// TODO Auto-generated method stub
		new Thread(() -> {
			for (int i = 0; i < 8; i++) {
				try {
					Thread.sleep(a);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					for (Processo p : processos) {
						if (p.isCoordenador()) {
							processos.remove(p);
							System.out.println("Coordenador inativado: [Processo " + p.getID() + "]");
							break;
						}
					}
				}
			}
		}).start();
	}

	@Override
	public void inativarProc(int a) throws RemoteException {
		// TODO Auto-generated method stub
		new Thread(() -> {
			for (int i = 0; i < 8; i++) {
				try {
					Thread.sleep(a);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				synchronized (lock) {
					int random = new Random().nextInt(processos.size());
					Processo p = processos.get(random);
					processos.remove(p);
					System.out.println("Processo inativado: [Processo " + p.getID() + "]");
				}
			}
		}).start();
	}
}
