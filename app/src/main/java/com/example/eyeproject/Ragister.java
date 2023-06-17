package com.example.eyeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Ragister extends AppCompatActivity {
EditText  userNameEdt, passwordEdt, confirmPwdEdt;
    TextView loginTV;
    Button registerBtn;
    FirebaseAuth mAuth;
    ProgressBar loadingPB;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);
        userNameEdt=findViewById(R.id.idname);
        passwordEdt = findViewById(R.id.idpassword);
        loadingPB = findViewById(R.id.idPBLoading);
        confirmPwdEdt = findViewById(R.id.idcp);
        loginTV = findViewById(R.id.idTVLoginUser);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Ragister.this, MainActivity.class);
                startActivity(i);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfonAuth();
            }
        });


    }
    private  void  PerfonAuth() {
        String email = userNameEdt.getText().toString();
        String password = passwordEdt.getText().toString();
        String conformPassword = confirmPwdEdt.getText().toString();

        if (!email.matches(emailpattern)) {
            userNameEdt.setError("Enter correct Email");
        } else if (password.isEmpty() || password.length() < 6) {
            passwordEdt.setError("Enter Proper password");
        } else if (!password.equals(conformPassword)) {
            confirmPwdEdt.setError("Password not match");
        } else {
            progressDialog.setMessage("Please Wait Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUsertonextactivity();
                        Toast.makeText(Ragister.this, "Registaration Successful", Toast.LENGTH_SHORT).show();


                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(Ragister.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUsertonextactivity() {
        Intent intent =new Intent(Ragister.this,Homepage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}