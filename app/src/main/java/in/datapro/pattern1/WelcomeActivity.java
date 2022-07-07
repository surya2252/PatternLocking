package in.datapro.pattern1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }


    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public boolean validate(String un) {
        if (isValidEmailId(un.trim())) {
            Toast.makeText(getApplicationContext(), "Valid Email Address.", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void register(View view) {


        String un = ((EditText) findViewById(R.id.etUsername)).getText().toString();
        if(!validate(un)) return ;
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String json = sharedPreferences.getString(un, "");
        if (!json.isEmpty()) {
            Toast.makeText(WelcomeActivity.this, "user already exists", Toast.LENGTH_LONG).show();
            //success = false;
            return;
        }

        Intent intent = new Intent(WelcomeActivity.this,
                MainActivity.class);
        intent.putExtra("username", un);
        intent.putExtra("operation", "register");
        startActivity(intent);
        finish();
    }

    public void login(View view) {
        String un=((EditText) findViewById(R.id.etUsername)).getText().toString();
        if(!validate(un)) return ;
        Intent intent = new Intent(WelcomeActivity.this,
                MainActivity.class);
        intent.putExtra("username", un);
        intent.putExtra("operation", "login");
        startActivity(intent);
        finish();
    }
}