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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_user extends AppCompatActivity implements View.OnClickListener {

     Validation vr=new Validation();
    private Button buttonRegister;
    private EditText editTextfirstname;
    private EditText editTextlastname;
    private EditText editTextemail;
    private EditText ediTextphone;
    private EditText editTextaddress;

    private EditText editTextrepassword;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.register_user);*/
       setContentView(R.layout.register_user);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog=new ProgressDialog(this);
        buttonRegister =(Button)findViewById(R.id.register);
        editTextfirstname=(EditText)findViewById(R.id.first_name);
        editTextlastname=(EditText)findViewById(R.id.last_name);
        editTextemail =(EditText)findViewById(R.id.email);
        editTextaddress=(EditText)findViewById(R.id.address);
        ediTextphone=(EditText)findViewById(R.id.phone);
        editTextPassword=(EditText)findViewById(R.id.password);
        editTextrepassword  =(EditText)findViewById(R.id.repassword);
        textViewSignin=(TextView)findViewById(R.id.status);
        buttonRegister.setBackgroundResource(R.drawable.signup_tabindicator);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);




    }
    private void registerUser()
    {
        final String firstname=editTextfirstname.getText().toString().trim();
        final String lastname=editTextlastname.getText().toString().trim();
        final String address=editTextaddress.getText().toString().trim();
        final String phone=ediTextphone.getText().toString().trim();
        final String email= editTextemail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String repassword=editTextrepassword.getText().toString().trim();

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
        buttonRegister.setEnabled(false);
        progressDialog.setMessage("Registering User..");
        progressDialog.show();



        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Toast.makeText(Register_user.this, "Registration Successfull",

                                    Toast.LENGTH_SHORT).show();
                            String useruid=firebaseAuth.getCurrentUser().getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference(firstname+":"+useruid);
                            databaseReference.child("User_Id").setValue(useruid);
                            databaseReference.child("first_name").setValue(firstname);
                            databaseReference.child("Last_name").setValue(lastname);
                            databaseReference.child("Email").setValue(email);
                            databaseReference.child("Address").setValue(address);
                            databaseReference.child("phone_no").setValue(phone);
                            startActivity(new Intent(getApplicationContext(), ImageUpload.class));
                           // Intent intent = new Intent(Register_user.this,MainActivity.class);
                           // startActivity(intent);
                            finish();

                        }


                        else
                        {
                            Toast.makeText(Register_user.this, "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();


                        // ...
                    }
                });







}

    private void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration Failed", Toast.LENGTH_LONG).show();

        buttonRegister.setEnabled(true);
    }
    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Registration Completed", Toast.LENGTH_LONG).show();

        buttonRegister.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view== buttonRegister)
        {
            registerUser();
        }
        if(view==textViewSignin)
        {
            Intent intent = new Intent(Register_user.this,Login_user.class);
            startActivity(intent);
        }

    }
}
