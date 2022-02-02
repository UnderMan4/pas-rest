package p.lodz.pl.pas.conversion;

import com.google.gson.*;
import p.lodz.pl.pas.model.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UserDeserializer implements JsonDeserializer<User> {

    final String userTypeElementName = "accessLevel";
    final Map<String, Class<? extends User>> userTypeRegistry;

    public UserDeserializer() {
        userTypeRegistry = new HashMap<>();
        userTypeRegistry.put("NormalUser", NormalUser.class);
        userTypeRegistry.put("Admin", Admin.class);
        userTypeRegistry.put("ResourceAdministrator", ResourceAdministrator.class);
        userTypeRegistry.put("UserAdministrator", UserAdministrator.class);

    }

    @Override
    public User deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject userObject = jsonElement.getAsJsonObject();
        JsonElement userTypeElement = userObject.get(userTypeElementName);
        String userTypeName = userTypeElement.getAsString();
        Class<? extends User> userTypeClass = userTypeRegistry.get(userTypeName);
        return new Gson().fromJson(userObject, userTypeClass);
        // return new Gson().fromJson(jsonElement.getAsJsonObject(), userTypeRegistry.get(jsonElement.getAsJsonObject().get(userTypeElementName).getAsString()));
    }
}
