package com.johade.quotem.Views_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.johade.quotem.R;
import com.johade.quotem.model.CreateUserResponse;
import com.johade.quotem.service.QuoteMRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private QuoteMRepository repository;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        repository = new QuoteMRepository(this);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        Call<CreateUserResponse> call = repository.createUser("johadeTest3", "12345", "fake@a.com");
        call.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
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
}
