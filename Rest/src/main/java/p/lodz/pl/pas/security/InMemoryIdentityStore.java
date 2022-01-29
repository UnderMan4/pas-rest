package p.lodz.pl.pas.security;

import p.lodz.pl.pas.exceptions.ItemNotFoundException;
import p.lodz.pl.pas.manager.UserManager;
import p.lodz.pl.pas.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class InMemoryIdentityStore implements IdentityStore {

    @Inject
    UserManager userManager;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential usernamePasswordCredential) {
            try {
                User user = userManager.findUserByLoginPasswordActive(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
                return (null != user ? new CredentialValidationResult(user.getLogin(), new HashSet<>(List.of(user.getUserAccessLevel().name()))) : CredentialValidationResult.NOT_VALIDATED_RESULT);
            } catch (ItemNotFoundException e) {
                return CredentialValidationResult.NOT_VALIDATED_RESULT;
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public int priority() {
        return IdentityStore.super.priority();
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return IdentityStore.super.validationTypes();
    }
}
