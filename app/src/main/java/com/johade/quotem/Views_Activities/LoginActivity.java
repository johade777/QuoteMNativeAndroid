package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.johade.quotem.R;
import com.johade.quotem.model.LoginResponse;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private QuoteMRepository repository;
    private TextInputEditText usernameEdit;
    private TextInputEditText passwordEdit;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = new QuoteMRepository(this);
        usernameEdit = findViewById(R.id.loginUsername);
        passwordEdit = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        loginButton.setOnClickListener(view -> login());
        registerButton.setOnClickListener(view -> register());
    }

    private void login(){
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        Call<LoginResponse> call = repository.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body().getAuthToken() != null) {
                    repository.setToken(response.body().getAuthToken());
                    loggedIn();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed Login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void register(){
        Intent openActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(openActivity);
    }

    private void loggedIn(){
        Intent openActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(openActivity);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
