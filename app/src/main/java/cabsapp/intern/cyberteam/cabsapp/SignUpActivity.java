package cabsapp.intern.cyberteam.cabsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName, inputUserName, inputPhoneNumber;
    private ImageView img;
    private Button btnSignUp;
    private TextView login;
    private FirebaseAuth auth;
    private ProgressDialog mProgressDialog;

    private FirebaseAnalytics mFirebaseAnalytics;
    private StorageReference mStorage;
    private FirebaseStorage mFirebaseStorage;

    private final String TAG = "SignUpActivity";
    private final String KEY_NAME = "name";
    private final String KEY_USERNAME = "username";
    private final String KEY_PHONE = "phone";
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD = "password";
    private final String KEY_UID = "uid";

    private static final int RC_PHOTO_PICKER =  2;

    private FirebaseUser mCurrentUser;//
//    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toast.makeText(SignUpActivity.this, "Sign up to proceed!", Toast.LENGTH_SHORT).show();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCanceledOnTouchOutside(false);

        login = (TextView) findViewById(R.id.login);
        btnSignUp = (Button) findViewById(R.id.signup_button);
        inputEmail = (EditText) findViewById(R.id.signup_email_edittext);
        inputPassword = (EditText) findViewById(R.id.signup_password_edittext);
        inputName = (EditText) findViewById(R.id.name_edittext);
        inputUserName = (EditText) findViewById(R.id.username_edittext);
        inputPhoneNumber = (EditText) findViewById(R.id.phone_number_edittext);
        img = (ImageView) findViewById(R.id.profile_pic);

        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorage = mFirebaseStorage.getReference().child("profile_photos");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginAction = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginAction);
            }
        });

        //Image View to set profile pic
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i=new Intent(Intent.ACTION_PICK);
//                i.setType("image/*");
//                startActivityForResult(i, request_code);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String name = inputName.getText().toString().trim();
                String username = inputUserName.getText().toString().trim();
                String phoneNumber = inputPhoneNumber.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                mProgressDialog.setMessage("Signing you up...");
                mProgressDialog.show();
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //progressBar.setVisibility(View.GONE);
                                mProgressDialog.dismiss();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Something Went wrong. Reasons might include : Account already exists/No internet connectivity/or maybe, Something else!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();
//                                    updateProfile(name, phoneNumber, username);
                                    setUserProfile(name, username, phoneNumber, email);
                                    getUserProfile();
//                                    getProviderData();
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

                //Adding details to online database
//                mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
//                DatabaseReference newSUser = mDatabase.push();
//                newStudent.child(KEY_EMAIL).setValue(email);
//                newStudent.child(KEY_PASSWORD).setValue(password);
//                newStudent.child(KEY_NAME).setValue(name);
//                newStudent.child(KEY_USERNAME).setValue(username);
//                newStudent.child(KEY_PHONE).setValue(phoneNumber);
//                newStudent.child(KEY_UID).setValue(mCurrentUser.getUid());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();

            StorageReference filepath = mStorage.child("Images").child(selectedImageUri.getLastPathSegment());

            filepath.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    img.setImageResource(taskSnapshot.hashCode());
                    img.setImageURI(downloadUrl);
                    Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
//                    newStudent.child("image").setValue(downloadUrl);
                }
            });
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
        mProgressDialog.dismiss();
    }


//    public void getProviderData() {
//        // [START get_provider_data]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            for (UserInfo profile : user.getProviderData()) {
//                // Id of the provider (ex: google.com)
//                String providerId = profile.getProviderId();
//
//                // UID specific to the provider
//                String uid = profile.getUid();
//
//                // Name, email address, and profile photo Url
//                String name = profile.getDisplayName();
//                String email = profile.getEmail();
//                Toast.makeText(this, "Email : " + email + "\nName : " + name, Toast.LENGTH_SHORT).show();
////                Uri photoUrl = profile.getPhotoUrl();
//            }
//        }
//    }

//    void updateProfile(String name, String phoneNumber, String username) {
//        // [START update_profile]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName(name)
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                            for (UserInfo profile : user.getProviderData()) {
//
//                                String providerId = profile.getProviderId();
//                                String uid = profile.getUid();
//                                String name = profile.getDisplayName();
//                                String email = profile.getEmail();
//                                String phoneNumber = profile.getPhoneNumber();
//                                Toast.makeText(getApplicationContext(),
//                                        "Email : " + email +
//                                                "\nName : " + name +
//                                                "\nPhone Number : " + phoneNumber +
//                                                "\nProvider ID : " + providerId +
//                                                "\nUID : " + uid,
//                                        Toast.LENGTH_SHORT)
//                                        .show();
//                            }
//                        }
//                    }
//                });
//    }


    private void setUserProfile(String name, String username, String phoneNumber, String email) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(KEY_NAME, name)
                .putString(KEY_USERNAME, username)
                .putString(KEY_PHONE, phoneNumber)
                .putString(KEY_EMAIL, email)
                .apply();

        // [START user_property]
        mFirebaseAnalytics.setUserProperty(KEY_NAME, name);
        mFirebaseAnalytics.setUserProperty(KEY_USERNAME, username);
        mFirebaseAnalytics.setUserProperty(KEY_PHONE, phoneNumber);
        mFirebaseAnalytics.setUserProperty(KEY_EMAIL, email);
        // [END user_property]
    }

    private void getUserProfile() {
        String name = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_NAME, null);
        String username = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_USERNAME, null);
        String phone = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_PHONE, null);
        String email = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_EMAIL, null);

        Toast.makeText(this, "Name : " + name +
                "\nUsername : " + username +
                "\nPhone Number : " + phone +
                "\nEmail ID : " + email,
                Toast.LENGTH_SHORT).show();
    }

}

