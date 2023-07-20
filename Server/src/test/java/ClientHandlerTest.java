import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import ru.netology.Client;
import ru.netology.Client2;
import ru.netology.Server;

import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class ClientHandlerTest {
    Client client;
    Client2 client2;
    Server server;

    @BeforeEach
    public void createObjects() {
        server = new Server();
        client = new Client();
        client2 = new Client2();
    }

    @AfterEach
    public void deleteObjects() {
        server = null;
        client = null;
        client2 = null;
    }

    @Test
    public void testSendMsg() {
//TODO тест вывода на экран в классе Client
    }

    @Test
    public void testClose() {
//TODO тест при выходе пользователя
    }

}




