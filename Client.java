package anelDoFabioSockets;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private Client() {}
	private final static int timeCreate = 3000;
	private final static int timeReq = 2500;
	private final static int timeManagerDisable = 1000;
	private final static int TimeProcDisable = 8000;

    public static void main(String[] args) {
    	
        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Scanner input = new Scanner(System.in);
            String op;
            String a = "coordenador";
			String response = "";
			Hello stub = (Hello) registry.lookup("AnelCoordenador");
            stub.criarProc(timeCreate);
            stub.realizarRequisicao(timeReq);
            stub.inativarCoordenador(timeManagerDisable);
            stub.inativarProc(TimeProcDisable);
          
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
