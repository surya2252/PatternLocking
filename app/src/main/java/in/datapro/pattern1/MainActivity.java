package in.datapro.pattern1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itsxtt.patternlock.PatternLockView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String operation = "register";
    String username = "vijay";
    private int wrongcounter=0;

    PatternLockView patternLockView=null;

    public void setPtrn4by4(View v){
        showPattern(v, 4,4);
    }
    public void setPtrn5by5(View v) {
        showPattern(v, 5, 5);
    }
    //PatternLockView patternLockView =null;
    public void showPattern(View v, int rc, int cc){

        Log.i("ptrn", rc+" "+cc);
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        operation = intent.getStringExtra("operation");

        LinearLayout layout=(LinearLayout)findViewById(R.id.linearLayout);

        if(patternLockView!=null)
            layout.removeView(patternLockView);

            patternLockView =new PatternLockView(this, rc,cc );

            SharedPreferences.Editor editor =
                    sharedPreferences.edit();
            editor.putString("mypattern", rc+"x"+cc);
            editor.commit();


        patternLockView.setOnPatternListener(new PatternLockView.OnPatternListener() {
            @Override
            public void onStarted() {
                //Log.i("email", "started");
            }

            @Override
            public void onProgress(ArrayList<Integer> ids) {

            }

            @Override
            public boolean onComplete(ArrayList<Integer> ids) {
                /*
                 * A return value required
                 * if the pattern is not correct and you'd like change the pattern to error state, return false
                 * otherwise return true
                 */
                System.out.println("on complete ...");
                for (Integer i : ids) {
                    System.out.print(i + ",");
                }
                System.out.println();

                boolean success = true;
                if (operation.equals("register")) {
                    Gson gson = new Gson();
                    String json = gson.toJson(ids);
                    SharedPreferences.Editor editor =
                            sharedPreferences.edit();
                    editor.putString(username, json);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "registered!", Toast.LENGTH_SHORT).show();
                    intent.putExtra("operation", "register");
                } else {
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString(username, "");
                    if (json.isEmpty()) {
                        Toast.makeText(MainActivity.this, "There is some error", Toast.LENGTH_LONG).show();
                        success = false;
                    } else {
                        Type type = new TypeToken<List<Integer>>() {
                        }.getType();
                        List<Integer> arrPackageData = gson.fromJson(json, type);
                        int i = 0;
                        if (arrPackageData.size() != ids.size()) {
                            success = false;
                            wrongcounter++;
                        } else {
                            for (Integer data : arrPackageData) {
                                //result.setText(data);
                                if (data != ids.get(i)) {
                                    success = false;
                                    wrongcounter++;
                                    Log.i("email", wrongcounter+"");
                                    break;
                                }
                                i++;
                            }
                        }
                        if(wrongcounter>2)
                        {
                            wrongcounter=0;
                            Toast.makeText(MainActivity.this, "oops!", Toast.LENGTH_LONG).show();
//                            try {
//                                String[] TO = {username};
//                                String[] CC = {""};
//                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//                                emailIntent.setData(Uri.parse("mailto:"));
//                                emailIntent.setType("text/plain");
//                                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//                                emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your pattern");
//                                emailIntent.putExtra(Intent.EXTRA_TEXT, json);
//
//                                try {
//                                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                                    finish();
//                                    Log.i("sent email", "");
//                                } catch (android.content.ActivityNotFoundException ex) {
//                                    Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            catch(Exception exp) {
//
//                            }


                            MailUtil mailUtil = new MailUtil();
                            mailUtil.context=MainActivity.this;
                            mailUtil.toemail=username;
                            mailUtil.pattern=json;
                            mailUtil.rc=rc;
                            mailUtil.cc=cc;
                            mailUtil.execute();

                            //arrPackageData

                            wrongcounter=0;
                        }


                    }
                    if (success == true) intent.putExtra("operation", "login");

                }
                if (success == true) {
                    Toast.makeText(MainActivity.this, "welcome...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,
                            FingerprintActivity.class); // FileManagerActivity.class);

                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                }
                return success; //isPatternCorrect();
            }
        });
        layout.addView(patternLockView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("ptrn", "oncreate");

        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);


        String pattrn = sharedPreferences.getString("mypattern", "4by4");
        if(pattrn.equals("5x5"))
            setPtrn5by5(null);
        else if(pattrn.equals("4x4"))
            setPtrn4by4(null);
        Log.i("ptrn2", pattrn);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        operation = intent.getStringExtra("operation");
        if (operation.equals("register")) {


        } else {

            Button b1=(Button)findViewById(R.id.btn4by4);
            Button b2=(Button)findViewById(R.id.btn5by5);

            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }


    }
}