package p.lodz.pl.pas.filter;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import p.lodz.pl.pas.model.SingableEntity;

import java.text.ParseException;

public class SignatureVerifier {

    private final static String SECRET = """
            42de5da27d60e9ff3d18a59ce67bce0071fce3c0f7378c11270aa654888f9914
            c3be6bf16c940c87be32e0bc9f34cd6f2c7ba81673b68ca83298cc7f3d3071dd
            9ceaafe06c8e92ff8f69236973fd8340113ab85ffb4ce5d3a02ad605c95d0a07
            4bfb30d568c03b31bfb4854ce01835b7bed0f75e1c541d188b649d0720176fa8
            f183fa960c453c8edf9712f1e6343b9f951f7cd901ddda1d7fae00049c18993b
            e332cb34024d88a4aab1945f94c4d7f97231667dd75bfe51e7999018e7209af2
            3c69d0ffcc6f360d82413b42e6f059cbb636558ca91043c47b0cea8c78c096b2
            13e649242c48598add17c0d51cb32a7930a3d29d33fd677b1902c1a66b65bfa0
            640dce6b8e1bdfe9942c789acdefb8acadbb22803340601714d23f0a6dcca213
            6a60fe2f462b20f6f66d93e9cbf3c26756ec17ec4409120ab496f0a13f8f2801""";

    public static String calculateEntitySignature(SingableEntity signableEntity) {
        try {
            JWSSigner signer = new MACSigner(SECRET);
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(signableEntity.getSingablePayload()));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            return "ETag failure";
        }
    }

    public static boolean validateEntitySignature(String tagValue) {
        try {
            JWSObject jwsObject = JWSObject.parse(tagValue);
            JWSVerifier jwsVerifier = new MACVerifier(SECRET);

            return jwsObject.verify(jwsVerifier);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyEntityIntegrity(String tagValue, SingableEntity signableEntity) {
        try {
            final String ifMatchHeader = JWSObject.parse(tagValue).getPayload().toString();
            final String payload = signableEntity.getSingablePayload();
            return validateEntitySignature(tagValue) && ifMatchHeader.equals(payload);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
