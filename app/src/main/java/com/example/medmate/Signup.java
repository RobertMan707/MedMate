package com.example.medmate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Signup extends AppCompatActivity {

    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout regName, regUsername, regEmail, regPassword, regConfirmpassword;
    Button regBtn, regToLoginBtn;

    DatabaseReference reference;
    FirebaseDatabase rootnode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regToLoginBtn = findViewById(R.id.login_signup_button);
        image = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_text);
        sloganText = findViewById(R.id.slogan_name);

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regConfirmpassword = findViewById(R.id.reg_confirm_passowrd);
        regBtn = findViewById(R.id.reg_btn);

        regToLoginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Login.class);
            Pair[] pairs = new Pair[] {
                    new Pair<>(image, "logo_image"),
                    new Pair<>(logoText, "logo_text"),
                    new Pair<>(sloganText, "signInText"),
                    new Pair<>(regEmail, "email_tran"),
                    new Pair<>(regPassword, "password_tran"),
                    new Pair<>(regToLoginBtn, "login_signup_tran")
            };

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);
            startActivity(intent, options.toBundle());
        });

        regBtn.setOnClickListener(v -> {
            registerUser();
        });
    }

    private void registerUser() {
        if (!validateName() | !validateUsername() | !validateEmail() | !validatePassword() | !validateConfirmPassword()) {
            return;
        }

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, password);

        reference.child(username).setValue(helperClass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String verificationCode = generateVerificationCode();
                        sendVerificationEmail(email, verificationCode);

                        Intent intent = new Intent(Signup.this, EmailVerification.class);
                        intent.putExtra("email", email);
                        intent.putExtra("verificationCode", verificationCode);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private String generateVerificationCode() {
        Random rand = new Random();
        int verificationCode = rand.nextInt(999999);
        return String.format("%06d", 100000 + verificationCode);
    }

    private void sendVerificationEmail(String email, String verificationCode) {
        String host = "smtp.gmail.com";
        String from = "med.and.mate@gmail.com";
        String password = "gyln cuxd vnun cpdg";

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Email Verification");
            message.setText("Your verification code is: " + verificationCode);

            new Thread(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Boolean validateName() {
        String val = regName.getEditText() != null ? regName.getEditText().getText().toString().trim() : "";
        if (val.isEmpty()) {
            regName.setError("Field can't be empty");
            return false;
        } else {
            regName.setError(null);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText() != null ? regUsername.getEditText().getText().toString().trim() : "";
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regUsername.setError("Field can't be empty");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            regUsername.setError("Invalid username. No spaces allowed.");
            return false;
        } else {
            regUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText() != null ? regEmail.getEditText().getText().toString().trim() : "";
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Field can't be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText() != null ? regPassword.getEditText().getText().toString().trim() : "";
        String passwordVal = "^(?=.*[a-zA-Z])(?=.*[@#$%^&+=.]).{4,}$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

    private Boolean validateConfirmPassword() {
        String password = regPassword.getEditText() != null ? regPassword.getEditText().getText().toString().trim() : "";
        String confirmPassword = regConfirmpassword.getEditText() != null ? regConfirmpassword.getEditText().getText().toString().trim() : "";

        if (confirmPassword.isEmpty()) {
            regConfirmpassword.setError("Field cannot be empty");
            return false;
        } else if (!confirmPassword.equals(password)) {
            regConfirmpassword.setError("Passwords do not match");
            return false;
        } else {
            regConfirmpassword.setError(null);
            return true;
        }
    }
}
