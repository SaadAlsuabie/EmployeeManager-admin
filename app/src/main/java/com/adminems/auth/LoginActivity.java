package com.adminems.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.adminems.MainActivity;
import com.adminems.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLoginUser;
    private TextView reg_text;
    private EditText et_username, et_password;
    private String example_username = "admin@ems.com";
    private String example_password = "admin123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnLoginUser = findViewById(R.id.btnLogin);
        et_username = findViewById(R.id.etUsername);
        et_password = findViewById(R.id.etPassword);

        et_username.setText(example_username);
        et_password.setText(example_password);

        reg_text = findViewById(R.id.register_text);

        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().equals(example_username) & et_password.getText().toString().equals(example_password)){
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "INVALID lOGIN", Toast.LENGTH_SHORT).show();
                }

            }
        });
        reg_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "You cannot create an account now. Please visit the IT team for assistance.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}