package p.lodz.pl.pas.conversion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import p.lodz.pl.pas.model.*;

import java.time.LocalDateTime;

public class GsonLocalDateTime {
    public static Gson getGsonSerializer(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .registerTypeAdapter(NormalUser.class, new UserSerializer())
                .registerTypeAdapter(Admin.class, new UserSerializer())
                .registerTypeAdapter(ResourceAdministrator.class, new UserSerializer())
                .registerTypeAdapter(UserAdministrator.class, new UserSerializer())
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create();
    }
}
