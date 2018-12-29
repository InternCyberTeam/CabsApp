package cabsapp.intern.cyberteam.cabsapp;

import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PlayGamesAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserProfileActivity extends AppCompatActivity {

    public TextView usernameTextView, nameTextView,
            emailTextView, phoneNumberTextView;

    private static final String TAG = "UserProfileActivity";
    private final String KEY_NAME = "name";
    private final String KEY_USERNAME = "username";
    private final String KEY_PHONE = "phone";
    private final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Please login to proceed!", Toast.LENGTH_SHORT).show();
        }

        usernameTextView = findViewById(R.id.username_textview);
        nameTextView = findViewById(R.id.name_textview);
        emailTextView = findViewById(R.id.signup_email_textview);
        phoneNumberTextView = findViewById(R.id.phone_number_textview);

        printUserProfile();
    }

    private void printUserProfile() {
        String name = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_NAME, null);
        String username = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_USERNAME, null);
        String phone = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_PHONE, null);
        String email = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(KEY_EMAIL, null);

        emailTextView.setText(email);
        usernameTextView.setText(username);
        nameTextView.setText(name);
        phoneNumberTextView.setText(phone);

        Toast.makeText(this, "Name : " + name +
                        "\nUsername : " + username +
                        "\nPhone Number : " + phone +
                        "\nEmail ID : " + email,
                Toast.LENGTH_SHORT).show();
    }











//    public void updateProfile() {
//        // [START update_profile]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Jane Q. User")
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                        }
//                    }
//                });
//        // [END update_profile]
//    }
//
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
//                Uri photoUrl = profile.getPhotoUrl();
//            }
//        }
//        // [END get_provider_data]
//    }
//
//    public void updateEmail() {
//        // [START update_email]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateEmail("user@example.com")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User email address updated.");
//                        }
//                    }
//                });
//        // [END update_email]
//    }
//
//    public void updatePassword() {
//        // [START update_password]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String newPassword = "SOME-SECURE-PASSWORD";
//
//        user.updatePassword(newPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User password updated.");
//                        }
//                    }
//                });
//        // [END update_password]
//    }
//
//    public void sendEmailVerification() {
//        // [START send_email_verification]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//        // [END send_email_verification]
//    }
//
//    public void sendEmailVerificationWithContinueUrl() {
//        // [START send_email_verification_with_continue_url]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        FirebaseUser user = auth.getCurrentUser();
//
//        String url = "http://www.example.com/verify?uid=" + user.getUid();
//        ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
//                .setUrl(url)
//                .setIOSBundleId("com.example.ios")
//                // The default for this is populated with the current android package name.
//                .setAndroidPackageName("com.example.android", false, null)
//                .build();
//
//        user.sendEmailVerification(actionCodeSettings)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//
//        // [END send_email_verification_with_continue_url]
//        // [START localize_verification_email]
//        auth.setLanguageCode("fr");
//        // To apply the default app language instead of explicitly setting it.
//        // auth.useAppLanguage();
//        // [END localize_verification_email]
//    }
//
//    public void sendPasswordReset() {
//        // [START send_password_reset]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String emailAddress = "user@example.com";
//
//        auth.sendPasswordResetEmail(emailAddress)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//        // [END send_password_reset]
//    }
//
//    public void deleteUser() {
//        // [START delete_user]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.delete()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User account deleted.");
//                        }
//                    }
//                });
//        // [END delete_user]
//    }
//
//    public void reauthenticate() {
//        // [START reauthenticate]
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        // Get auth credentials from the user for re-authentication. The example below shows
//        // email and password credentials but there are multiple possible providers,
//        // such as GoogleAuthProvider or FacebookAuthProvider.
//        AuthCredential credential = EmailAuthProvider
//                .getCredential("user@example.com", "password1234");
//
//        // Prompt the user to re-provide their sign-in credentials
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Log.d(TAG, "User re-authenticated.");
//                    }
//                });
//        // [END reauthenticate]
//    }
//
//    public void authWithGithub() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        // [START auth_with_github]
//        String token = "<GITHUB-ACCESS-TOKEN>";
//        AuthCredential credential = GithubAuthProvider.getCredential(token);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "signInWithCredential", task.getException());
//                            Toast.makeText(UserProfileActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//                });
//        // [END auth_with_github]
//    }
//
//
//    public void linkAndMerge(AuthCredential credential) {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        // [START auth_link_and_merge]
//        FirebaseUser prevUser = FirebaseAuth.getInstance().getCurrentUser();
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        FirebaseUser currentUser = task.getResult().getUser();
//                        // Merge prevUser and currentUser accounts and data
//                        // ...
//                    }
//                });
//        // [END auth_link_and_merge]
//    }
//
//
//    public void unlink(String providerId) {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        // [START auth_unlink]
//        mAuth.getCurrentUser().unlink(providerId)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Auth provider unlinked from account
//                            // ...
//                        }
//                    }
//                });
//        // [END auth_unlink]
//    }
//
//    public void buildActionCodeSettings() {
//        // [START auth_build_action_code_settings]
//        ActionCodeSettings actionCodeSettings =
//                ActionCodeSettings.newBuilder()
//                        // URL you want to redirect back to. The domain (www.example.com) for this
//                        // URL must be whitelisted in the Firebase Console.
//                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
//                        // This must be true
//                        .setHandleCodeInApp(true)
//                        .setIOSBundleId("com.example.ios")
//                        .setAndroidPackageName(
//                                "com.example.android",
//                                true, /* installIfNotAvailable */
//                                "12"    /* minimumVersion */)
//                        .build();
//        // [END auth_build_action_code_settings]
//    }
//
//    public void sendSignInLink(String email, ActionCodeSettings actionCodeSettings) {
//        // [START auth_send_sign_in_link]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.sendSignInLinkToEmail(email, actionCodeSettings)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//        // [END auth_send_sign_in_link]
//    }
//
//    public void verifySignInLink() {
//        // [START auth_verify_sign_in_link]
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        Intent intent = getIntent();
//        String emailLink = intent.getData().toString();
//
//        // Confirm the link is a sign-in with email link.
//        if (auth.isSignInWithEmailLink(emailLink)) {
//            // Retrieve this from wherever you stored it
//            String email = "someemail@domain.com";
//
//            // The client SDK will parse the code from the link for you.
//            auth.signInWithEmailLink(email, emailLink)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "Successfully signed in with email link!");
//                                AuthResult result = task.getResult();
//                                // You can access the new user via result.getUser()
//                                // Additional user info profile *not* available via:
//                                // result.getAdditionalUserInfo().getProfile() == null
//                                // You can check if the user is new or existing:
//                                // result.getAdditionalUserInfo().isNewUser()
//                            } else {
//                                Log.e(TAG, "Error signing in with email link", task.getException());
//                            }
//                        }
//                    });
//        }
//        // [END auth_verify_sign_in_link]
//    }
//
//    public void linkWithSignInLink(String email, String emailLink) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        // [START auth_link_with_link]
//        // Construct the email link credential from the current URL.
//        AuthCredential credential =
//                EmailAuthProvider.getCredentialWithLink(email, emailLink);
//
//        // Link the credential to the current user.
//        auth.getCurrentUser().linkWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Successfully linked emailLink credential!");
//                            AuthResult result = task.getResult();
//                            // You can access the new user via result.getUser()
//                            // Additional user info profile *not* available via:
//                            // result.getAdditionalUserInfo().getProfile() == null
//                            // You can check if the user is new or existing:
//                            // result.getAdditionalUserInfo().isNewUser()
//                        } else {
//                            Log.e(TAG, "Error linking emailLink credential", task.getException());
//                        }
//                    }
//                });
//        // [END auth_link_with_link]
//    }
//
//    public void reauthWithLink(String email, String emailLink) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        // [START auth_reauth_with_link]
//        // Construct the email link credential from the current URL.
//        AuthCredential credential =
//                EmailAuthProvider.getCredentialWithLink(email, emailLink);
//
//        // Re-authenticate the user with this credential.
//        auth.getCurrentUser().reauthenticateAndRetrieveData(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // User is now successfully reauthenticated
//                        } else {
//                            Log.e(TAG, "Error reauthenticating", task.getException());
//                        }
//                    }
//                });
//        // [END auth_reauth_with_link]
//    }
//
//    public void differentiateLink(String email) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//
//        // [START auth_differentiate_link]
//        auth.fetchSignInMethodsForEmail(email)
//                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
//                        if (task.isSuccessful()) {
//                            SignInMethodQueryResult result = task.getResult();
//                            List<String> signInMethods = result.getSignInMethods();
//                            if (signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
//                                // User can sign in with email/password
//                            } else if (signInMethods.contains(EmailAuthProvider.EMAIL_LINK_SIGN_IN_METHOD)) {
//                                // User can sign in with email/link
//                            }
//                        } else {
//                            Log.e(TAG, "Error getting sign in methods for user", task.getException());
//                        }
//                    }
//                });
//        // [END auth_differentiate_link]
//    }
//
//    public void getGoogleCredentials() {
//        String googleIdToken = "";
//        // [START auth_google_cred]
//        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
//        // [END auth_google_cred]
//    }
//
//
//    public void getFbCredentials() {
//        AccessToken token = AccessToken.getCurrentAccessToken();
//        // [START auth_fb_cred]
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        // [END auth_fb_cred]
//    }
//
//    public void getEmailCredentials() {
//        String email = "";
//        String password = "";
//        // [START auth_email_cred]
//        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
//        // [END auth_email_cred]
//    }
//
//    public void signOut() {
//        // [START auth_sign_out]
//        FirebaseAuth.getInstance().signOut();
//        // [END auth_sign_out]
//    }
//
//    public void testPhoneVerify() {
//        // [START auth_test_phone_verify]
//        String phoneNum = "+16505554567";
//        String testVerificationCode = "123456";
//
//        // Whenever verification is triggered with the whitelisted number,
//        // provided it is not set for auto-retrieval, onCodeSent will be triggered.
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNum, 30L /*timeout*/, TimeUnit.SECONDS,
//                this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    @Override
//                    public void onCodeSent(String verificationId,
//                                           PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                        // Save the verification id somewhere
//                        // ...
//
//                        // The corresponding whitelisted code above should be used to complete sign-in.
//                        UserProfileActivity.this.enableUserManuallyInputCode();
//                    }
//
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                        // Sign in with the credential
//                        // ...
//                    }
//
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//                        // ...
//                    }
//
//                });
//        // [END auth_test_phone_verify]
//    }
//
//    private void enableUserManuallyInputCode() {
//        // No-op
//    }
//
//    public void testPhoneAutoRetrieve() {
//        // [START auth_test_phone_auto]
//        // The test phone number and code should be whitelisted in the console.
//        String phoneNumber = "+16505554567";
//        String smsCode = "123456";
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseAuthSettings firebaseAuthSettings = firebaseAuth.getFirebaseAuthSettings();
//
//        // Configure faking the auto-retrieval with the whitelisted numbers.
//        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(phoneNumber, smsCode);
//
//        PhoneAuthProvider phoneAuthProvider = PhoneAuthProvider.getInstance();
//        phoneAuthProvider.verifyPhoneNumber(
//                phoneNumber,
//                60L,
//                TimeUnit.SECONDS,
//                this, /* activity */
//                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                    @Override
//                    public void onVerificationCompleted(PhoneAuthCredential credential) {
//                        // Instant verification is applied and a credential is directly returned.
//                        // ...
//                    }
//
//                    // [START_EXCLUDE]
//                    @Override
//                    public void onVerificationFailed(FirebaseException e) {
//
//                    }
//                    // [END_EXCLUDE]
//                });
//        // [END auth_test_phone_auto]
//    }
//
//    private void updateUI(@Nullable FirebaseUser user) {
//        // No-op
//    }


}
