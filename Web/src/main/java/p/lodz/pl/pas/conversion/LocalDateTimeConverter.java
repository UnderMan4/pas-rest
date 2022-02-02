package p.lodz.pl.pas.conversion;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@FacesConverter("p.lodz.pl.pas.LocalDateTimeConverter")
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public LocalDateTime getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        // return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd.MM.yyyy")).atTime(12, 0);

        } catch (DateTimeParseException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date should be in form dd.mm.yyyy"));
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
