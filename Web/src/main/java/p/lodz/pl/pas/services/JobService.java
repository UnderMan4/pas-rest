//package p.lodz.pl.pas.services;
//
//import com.google.gson.Gson;
//import p.lodz.pl.pas.model.Job;
//import sample.web.jsf.jsfexamples.beans.NavigationBean;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.lang.reflect.Type;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class JobService {
//    private static final Logger logger = Logger.getLogger(NavigationBean.class.getName());
//
//    public ArrayList<Job> getAllJobs() {
//        URL url = null;
//        ArrayList<Job> jobList = null;
//        HttpURLConnection con = null;
//        try {
//            url = new URL(Const.MAIN_URL + "api/user/list");
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            int status = con.getResponseCode();
//            Gson gson = new Gson();
//            Reader reader = new InputStreamReader(con.getInputStream());
//            jobList = gson.fromJson(reader, (Type) Job.class);
//            logger.log(Level.INFO, jobList.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (con != null) con.disconnect();
//
//        }
//        return jobList;
//    }
//}
