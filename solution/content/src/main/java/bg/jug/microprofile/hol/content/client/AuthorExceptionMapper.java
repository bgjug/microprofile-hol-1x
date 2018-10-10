package bg.jug.microprofile.hol.content.client;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by Dmitry Alexandrov.
 */
@Provider
public class AuthorExceptionMapper implements
        ResponseExceptionMapper<RuntimeException> {

    @Override
    public boolean handles(int statusCode, MultivaluedMap<String, Object> headers) {
        return statusCode == 404  // Not Found
                || statusCode == 409; // Conflict
    }

    @Override
    public RuntimeException toThrowable(Response response) {
        switch(response.getStatus()) {
            case 404: return new NullPointerException();
            case 409: return new RuntimeException();
        }
        return null;
    }

}
