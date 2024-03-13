package vn.edu.stu.quanlylaptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin;
    String userStateFile = "UserState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        invalidateOptionsMenu();
        addControls();
        addEvents();
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuAbout){

        }
        else if (item.getItemId()==R.id.mnuExit){

        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyLogin();
            }
        });
    }
    private void xulyLogin() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (username.equalsIgnoreCase("admin") && password.equals("admin")) {
            Toast.makeText(
                    this,
                    "Login succeeded",
                    Toast.LENGTH_LONG
            ).show();
            SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.apply();
            Intent intent = new Intent(MainActivity.this, Menu.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(
                    this,
                    "Login failed",
                    Toast.LENGTH_LONG
            ).show();
            SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("username");
            editor.remove("password");
            editor.apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(userStateFile, MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");
        etUsername.setText(username);
        etPassword.setText(password);

    }
}