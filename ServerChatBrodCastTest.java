import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ServerChatBrodCastTest extends Thread  {
     ArrayList<Conversation> clients = new ArrayList<Conversation>();
    int numberClient = 0;
    //sysLib.setLocale("fr", "CA", "WIN");

    public static void main (String [] args) throws Exception{
        new ServerChatBrodCastTest().start();
    }
    @Override
    public void run()  {
        System.out.println("Starting server . . .");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true){
                Socket socket = serverSocket.accept();
                numberClient++;
                Conversation conversation = new Conversation(socket,numberClient);
                clients.add(conversation);
                conversation.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true){
        System.out.println("That is so very Good");
        }
    }

    public class Conversation extends Thread{
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

                System.out.println("Connexion of customer "+nbClient);
                String Ip = sock.getRemoteSocketAddress().toString();

                printWriter.println("connexion of customer number "+nbClient+" Ip address "+Ip);
                System.out.println("connexion of customer number "+nbClient+" Ip address "+Ip);

                while(true){
                    String requet = bufferedReader.readLine();/// recuperation de la chaine de caractere envoyé par le client
                    if(requet!=null){
                        //printWriter.println("The client number "+nbClient+ " send "+requet);
                        //System.out.println("The client number "+nbClient+ " send "+requet);
                        int longRequet = requet.length();
                        printWriter.println("Longer :"+longRequet);
                    if (requet.contains("=>")){
                        String[] requetPar = requet.split("=>");
                        if (requetPar.length==2);
                        String message=requetPar[1];
                        int numClient = Integer.parseInt(requetPar[0]);
                        broadCastMessage(message,sock,numClient);
                        printWriter.println("You are send "+message+ " to client N°"+numClient);
                        System.out.println("The customer number "+nbClient+ " send "+message+ " to customer N°"+numClient);

                        //printWriter.println("");
                    }
                    else {
                        broadCastMessage(requet,sock,-1);
                        printWriter.println("You are send "+requet+ " to all other customers");
                        System.out.println("The customer number "+nbClient+ " send "+requet+ " to all other customers");
                    }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //@Override
        public void broadCastMessage(String message, Socket socket, int numCli) throws IOException {
            /*for (int i = 0; i < clients.size(); i++) {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);

            }*/
           /* for (int i = 0; i < clients.size(); i++){
                PrintWriter printWriter = new PrintWriter(sock.getOutputStream(),true);
                printWriter.println(message);
            }*/
            for (Conversation client :clients){
                if(client.sock !=socket){
                    if (client.nbClient ==numCli || numCli==-1 ){
                        PrintWriter printWriter = new PrintWriter(client.sock.getOutputStream(),true);
                        //if(message)
                        printWriter.println("You have received a new message from the customer N°"+nbClient);
                        //.sleep(100);
                       /* try {
                            //TimeUnit.SECONDS.sleep(1);
                            printWriter.print(". ");
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();;
                        }
                                             try {
                            //TimeUnit.SECONDS.sleep(1);
                            printWriter.print(". ");
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();;
                        }try {
                            //TimeUnit.SECONDS.sleep(1);
                            printWriter.print(".");
                            TimeUnit.SECONDS.sleep(8);
                            printWriter.println("");
                        } catch (InterruptedException e) {
                            e.printStackTrace();;
                        }*/

                        printWriter.println(message);
                    }
                }
            }
            System.out.println("Connexion of customer "+nbClient);
        }
    }
}
