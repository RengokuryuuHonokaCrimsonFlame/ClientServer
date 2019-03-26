package ch.heigvd.res.exemple.test;


import ch.heigvd.res.examples.Client;

import java.util.logging.Logger;


import examples.MultiThreadedServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest{

    private static final Logger LOG = Logger.getLogger(ClientTest.class.getName());

    @BeforeAll
    public static void testServer(){
        MultiThreadedServer s1 = new MultiThreadedServer(2323);
        s1.serveClients();
    }


    @Test
    public void testAdd(){
        Client cl = new Client("127.0.0.1", 2323);
        assertEquals(cl.compute("add", 1, 2.5),3.5);
        cl.disconnect();
    }
    @Test
    public void testMull(){
        Client cl = new Client("127.0.0.1", 2323);
        assertEquals(cl.compute("mull", 1, 2.5),2.5);
        cl.disconnect();
    }

    @Test
    public void testsub(){
        Client cl = new Client("127.0.0.1", 2323);
        assertEquals(cl.compute("sub", 1, 452.5),-451.5);
        cl.disconnect();
    }

    @Test
    public void testdiv(){
        Client cl = new Client("127.0.0.1", 2323);
        assertEquals(cl.compute("div", 28, 5),5.6);
        cl.disconnect();
    }


}