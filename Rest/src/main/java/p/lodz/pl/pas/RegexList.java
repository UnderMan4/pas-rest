package p.lodz.pl.pas;


import java.util.regex.Pattern;

public class RegexList {

    public static Pattern USERNAME = Pattern.compile("^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    public static Pattern JOB_NAME = Pattern.compile("^(?=.{4,20}$)(?![_. ])(?!.*[_. ]{2})[a-zA-Z0-9._ ]+(?<![_. ])$");

    // Regex for description ex. Job description
    public static Pattern DESCRIPTION = Pattern.compile("[a-zA-Z0-9._ ]");
    public static Pattern Login = Pattern.compile("^(?=.{4,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");

    // Regex for names and surnames
    public static Pattern Surname = Pattern.compile("^(?=.{2,20}$)(?![ ])(?!.*[ ]{2})[a-zA-Z ]+(?<![ ])$");

    // regex for UUID
    public static Pattern UUID = Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");

}
