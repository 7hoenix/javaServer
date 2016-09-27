package CobSpecApp;

import HTTPServer.Handlers.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

import java.io.File;

public class ETagHandlerTest extends TestCase {
    public void test_it_returns_the_proper_etag() throws Exception {
        Request request = new Request("/patch-content.txt", "GET");
        Handler handler = new ETagHandler(new File("otherPublic"));

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("011E2DAD1697B147817D980208E9248F87279A66".toUpperCase(), response.getHeader("ETag"));
    }
}
