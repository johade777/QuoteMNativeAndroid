package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.johade.quotem.R;
import com.johade.quotem.model.CreateUserResponse;
import com.johade.quotem.model.LoginResponse;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private QuoteMRepository repository;
    private TextInputEditText usernameTextInput;
    private TextInputEditText passwordTextInput;
    private TextInputEditText emailTextInput;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        repository = new QuoteMRepository(this);
        usernameTextInput = findViewById(R.id.registerUsername);
        passwordTextInput = findViewById(R.id.registerPassword);
        emailTextInput = findViewById(R.id.registerEmail);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String username = usernameTextInput.getText().toString().trim();
        String password = passwordTextInput.getText().toString().trim();
        String email = emailTextInput.getText().toString().trim();

        Call<CreateUserResponse> call = repository.createUser(username, password, email);
        call.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                if(response.isSuccessful()) {
                    loginUser();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Create User Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateUserResponse> call, Throwable t) {

            }
        });
    }

    private void loginUser(){
        String username = usernameTextInput.getText().toString().trim();
        String password = passwordTextInput.getText().toString().trim();

        Call<LoginResponse> call = repository.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body().getAuthToken() != null) {
                    repository.setToken(response.body().getAuthToken());
                    loggedIn();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Create User Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void loggedIn(){
        Intent openActivity = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(openActivity);
        finish();
    }
}
