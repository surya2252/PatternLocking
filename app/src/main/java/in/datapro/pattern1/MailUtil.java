package in.datapro.pattern1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil extends AsyncTask<String, Integer, Integer> {

    public int cc=4;
    public int rc=4;
    ProgressDialog progressDialog;
    private StringBuilder all_email;

    public String toemail="";

    public Context context;
    public String pattern="";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context );
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();
//        if (selecteduser_arr != null) {
//            all_email = new StringBuilder();
//            for (int i = 0; i < selecteduser_arr.size(); i++) {
//                if (i == 0) {
//                    all_email.append(selecteduser_arr.get(i));
//                } else {
//                    String temp = "," + selecteduser_arr.get(i);
//                    all_email.append(temp);
//                }
//            }
//        }
    }

    @Override
    protected Integer doInBackground(String... strings) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("csebatch18k@gmail.com", "Csebatch@527");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("csebatch18k@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toemail));
            message.setSubject("Pattern Reminder");
            //message.setText("Dear Priviliged User," +"\n\n Your pattern is "+pattern);

            String pattern2 = "<table border='1'>";
            try {
                Type type = new TypeToken<List<Integer>>() {
                }.getType();
                Gson gson = new Gson();
                List<Integer> arrPackageData = gson.fromJson(pattern, type);


                int k = 0;
                for (int i = 0; i < rc; i++) {
                    pattern2 += "<tr>";
                    for (int j = 0; j < cc; j++) {
                        pattern2 += "<td>";
                        for(int z=0; z<arrPackageData.size(); z++)
                            if (k == arrPackageData.get(z))
                                pattern2 += k ;
                            else
                                pattern2 += "-" ;
                        pattern2 += "</td>";
                        k++;
                    }
                    pattern2 += "</tr>";

                }
                pattern2 += "</table>";
            }
            catch(Exception exp) {}
            String someHtmlMessage="Dear Priviliged User," +"<br/> Your pattern is <br/>"+pattern+"<br/>"+pattern2;
//Log.i("email..", someHtmlMessage);
            message.setContent(someHtmlMessage, "text/plain");
                    //"text/html; charset=utf-8");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        progressDialog.dismiss();
    }
}