package p.lodz.pl.pas.conversion;

import com.google.gson.*;
import p.lodz.pl.pas.model.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new Gson();
        JsonElement jsonElement = jsonSerializationContext.serialize(user);
        jsonElement.getAsJsonObject().addProperty("UserType", user.getUserAccessLevel().toString());
        return null;
    }
}