package p.lodz.pl.pas.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@SignatureValidatorFilter
public class SignatureValidator implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        String header = containerRequestContext.getHeaderString("If-Match");
        if (header == null || header.isEmpty()) {
            containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
        } else if (!SignatureVerifier.validateEntitySignature(header)) {
            containerRequestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
        }
    }
}
