package com.pro.sachin.friendzone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_user extends AppCompatActivity implements View.OnClickListener {

    Validation vr=new Validation();
    private Button buttonLogin;

    private EditText editTextemail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.register_user);*/
        setContentView(R.layout.signin_user);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(Login_user.this, MainActivity.class));
            finish();
        }

        progressDialog = new ProgressDialog(this);
        buttonLogin = (Button) findViewById(R.id.signin);

        editTextemail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        textViewSignin = (TextView) findViewById(R.id.status);
        buttonLogin.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }

    private void SignInUser()
    {

        final String email= editTextemail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();


        /*if(!vr.validationRegister(editTextfirstname,editTextlastname,editTextaddress,editTextemail,ediTextphone,editTextPassword,editTextrepassword)) {
            onSignupFailed();
            return;
        }*/


      /*  if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();

            return;
        }*/

        //if validation is ok
        //show progressbar
        buttonLogin.setEnabled(false);
        progressDialog.setMessage("Signing User..");
        progressDialog.show();



        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Toast.makeText(Login_user.this, "Login Successfull",

                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Intent intent = new Intent(Login_user.this,ImageUpload.class);
                            startActivity(intent);
                            finish();

                        }


                        else
                        {
                            Toast.makeText(Login_user.this, "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                            buttonLogin.setEnabled(true);
                        }

                        progressDialog.dismiss();


                        // ...
                    }
                });







    }

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration Failed", Toast.LENGTH_LONG).show();

        buttonLogin.setEnabled(true);
    }
    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Registration Completed", Toast.LENGTH_LONG).show();

        buttonLogin.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view== buttonLogin)
        {
            SignInUser();
        }
        if(view==textViewSignin)
        {
            Intent intent = new Intent(Login_user.this,Register_user.class);
            startActivity(intent);

        }

    }
}
