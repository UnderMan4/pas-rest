package p.lodz.pl.pas;



public class RegexList {

    public final static String USERNAME_PATTERN = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    public final static String JOB_NAME_PATTERN = "^(?=.{4,20}$)(?![_. ])(?!.*[_. ]{2})[a-zA-Z0-9._ ]+(?<![_. ])$";
    public final static String LOGIN_PATTERN = "^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    public final static String SURNAME_PATTERN = "^(?=.{2,20}$)(?![ ])(?!.*[ ]{2})[a-zA-Z ]+(?<![ ])$";
    public final static String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public final static String UUID_PATTERN = "([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})";

}
