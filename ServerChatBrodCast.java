import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerChatBrodCast extends Thread  {
    ArrayList clients = new ArrayList<Socket>();
    int numberClient = 0;
    public static void main (String [] args) throws Exception{
        new ServerChatBrodCast().start();
    }
    @Override
    public void run()  {
        System.out.println("Starting server . . .");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true){
                Socket socket = serverSocket.accept();
                clients.add(socket);
                numberClient++;
                Conversation conversation = new Conversation(socket,numberClient);
                conversation.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //int i =0;
        //i++;
        while (true){
        System.out.println("That is so very Good");
        }
    }

    private class Conversation extends Thread{
        private Socket sock;
        private int nbClient;
        public Conversation(Socket socket, int nbClient){
            super();
            this.sock = socket;
            this.nbClient = nbClient;
        }
        @Override
        public void run() {
            try {
                //InputStream inputStream = null; // recuperation des octet
                InputStream inputStream = sock.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // recuperation des caracteres
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); // recuperration des chaines de caracteres

                OutputStream outputStream = sock.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream,true);

                System.out.println("Connexion of client "+nbClient);
                String Ip = sock.getRemoteSocketAddress().toString();

                printWriter.println("connexion of client number "+nbClient+" Ip address "+Ip);

                while(true){
                    String requet = bufferedReader.readLine();/// recuperation de la chaine de caractere envoyé par le client
                    if(requet!=null){
                        printWriter.println("The client number "+nbClient+ " send "+requet);
                        System.out.println("The client number "+nbClient+ " send "+requet);
                        int longRequet = requet.length();
                        printWriter.println("Longer :"+longRequet);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //@Override
        private void broadCastMessage(String message, Socket socket, int numCli) throws IOException {
            for (int i = 0; i < clients.size(); i++) {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            }

            System.out.println("Connexion of client "+nbClient);
        }
    }
}
