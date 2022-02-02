package p.lodz.pl.pas.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.Date;


@ApplicationScoped
public class JWTAuthenticator {

    private static final String SECRET = """
            e67d2e714ababe2b115c1cd257d1159fe811f951b46b7ad006a8bb5e27e1eaec
            f5ca81385b906dc2b8364f7a7667eacbe6de637521b71ef3df882523201c9406
            dad8340fbd4e760c797018ed32d08d50f0daf956c4dcb8af47c7c23cc7fc8b40
            c54dfb28de1b15206d6561322f764087dd2c71621eb45673dc11ea506a853448
            66706291d4a241f109a1ff4b36b1073be5df4fa59d9539ae405c13cb0bc3cf51
            da3339d94bc341ec774ef2b590d76587df2ed28312626ad0726bdae236d2a438
            7f07d78d2bfc7a864889646f1706a0a4b82fcd483940a45702023e5354c64d53
            b8a338ba8294aa1e86482bf7d693b33ce15ae090c88f0c437f6f65804ce2479b
            bc3d75eb733fef2bd818350440a8cdd2af80712cb1bc479bc39a0e72a1997e4c
            1a00d78480c148aa6e9e5d960a0d93ae8ee578000d1dcce9ed9fd6455e866334""";
    private static final long JWS_TIMEOUT_MS = 30 * 60 * 1000;
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);
    private static final String ISSUER = "PAS_2022";

    public static String generateJWT(CredentialValidationResult credentialValidationResult) {

        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("auth", String.join(",", credentialValidationResult.getCallerGroups()))
                .withSubject(credentialValidationResult.getCallerPrincipal().getName())
                .withExpiresAt(new Date(new Date().getTime() + JWS_TIMEOUT_MS))
                .sign(algorithm);
    }

    public static DecodedJWT validateJWT(String jwtSerializedToken) throws JWTVerificationException {

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build(); //Reusable verifier instance

        // https://github.com/auth0/java-jwt#time-validation
        // When verifying a token the time validation occurs automatically, resulting in a JWTVerificationException being
        // throw when the values are invalid. If any of the previous fields are missing they won't be considered in this validation.
        try {
            return verifier.verify(jwtSerializedToken);
        } catch (JWTVerificationException e) {
            throw e;
        }
    }
}


