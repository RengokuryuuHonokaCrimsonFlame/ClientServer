package ch.heigvd.res.examples;


import java.io.*;

import java.net.Socket;

import java.util.logging.Level;

import java.util.logging.Logger;


/**
 * This is not really an HTTP client, but rather a very simple program that
 * <p>
 * establishes a TCP connection with a real HTTP server. Once connected, the
 * <p>
 * client sends "garbage" to the server (the client does not send a proper
 * <p>
 * HTTP request that the server would understand). The client then reads the
 * <p>
 * response sent back by the server and logs it onto the console.
 *
 * @author Olivier Liechti
 */

public class Client {

    final static Logger LOG = Logger.getLogger(Client.class.getName());

    Socket clientSocket;

    BufferedReader in;

    PrintWriter out;

    boolean connected = false;

    String userName;

    public Client(String ip,int port) {
        try {
            clientSocket = new Socket(ip, port);
            connected = true;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());

            //Etablissement de la connection
            out.println("hello");
            out.flush();
            if (in.readLine().compareTo("hello") == 0) {
                LOG.log(Level.INFO, "Bien connecté au serveur");
            } else {
                connected = false;
                LOG.log(Level.INFO, "Il a pas dit hello");
            }

        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Connection problem in client used by {1}: {0}", new Object[]{e.getMessage(), userName});
            connected = false;
        } finally {
//            cleanup();
        }
    }

    /**
     * This inner class implements the Runnable interface, so that the run()
     * <p>
     * method can execute on its own thread. This method reads data sent from the
     * <p>
     * server, line by line, until the connection is closed or lost.
     */


    public double compute(String operateur, double x, double y) {
        double resultat = 0;
        try {


            out.println(operateur + "," + x + "," + y);
            out.flush();
            String sres;
            if ((sres = in.readLine()) != null) {
                if (sres.contains("resultat = ")) {
                    LOG.log(Level.INFO, "Bonne réponse du serveur");
                    resultat = Double.parseDouble(sres.substring(11));
                } else {
                    LOG.log(Level.INFO, "Mauvaise réponse du serveur");
                }

            }


        } catch (IOException e) {

            LOG.log(Level.SEVERE, "Connection problem in client used by {1}: {0}", new Object[]{e.getMessage(), userName});

            connected = false;

        } finally {

            cleanup();

        }

        return resultat;

    }




    public void disconnect() {

        LOG.log(Level.INFO, "{0} has requested to be disconnected.", userName);

        connected = false;

        out.println("bye");

        cleanup();

    }


    private void cleanup() {


        try {

            if (in != null) {

                in.close();

            }

        } catch (IOException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        }


        if (out != null) {

            out.close();

        }


        try {

            if (clientSocket != null) {

                clientSocket.close();

            }

        } catch (IOException ex) {

            LOG.log(Level.SEVERE, ex.getMessage(), ex);

        }

    }

    public static void main(String... args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

        Client cl = new Client("172.0.0.1",2323);
         cl.compute("sum", 1, 2.5);
    }

}