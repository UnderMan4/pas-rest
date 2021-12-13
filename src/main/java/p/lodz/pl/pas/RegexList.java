package p.lodz.pl.pas;


import java.util.regex.Pattern;

public class RegexList {

    public static Pattern USERNAME = Pattern.compile("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    public static Pattern JOB_NAME = Pattern.compile("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");
    public static Pattern JOB_DESCRIPTION = Pattern.compile("[a-zA-Z0-9._ ]");
}
