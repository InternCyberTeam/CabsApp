package cabsapp.intern.cyberteam.cabsapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class IntroActivity extends MaterialIntroActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(IntroActivity.this, MainActivity.class));
            finish();
        }

        //set if the last slide should disapear with alpha hiding effect
        //enableLastSlideAlphaExitTransition(true);

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.blue)
                .buttonsColor(R.color.blueButton)
                .image(R.drawable.awsb_ic_arrow_back_white_24dp)
                .title("Organize your Time with us")
                .description("Wanna try?")
                .build()
        );

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.red)
                .buttonsColor(R.color.redButton)
                .image(R.drawable.awsb_ic_arrow_back_white_24dp)
                .title("Join us!")
                .description("You would not be disappointed?")
                .build()
        );

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.green)
                        .buttonsColor(R.color.greenButton)
                        .image(R.drawable.awsb_ic_arrow_back_white_24dp)
                        .title("Go on!")
                        .description("Login now!")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent loginAction = new Intent(IntroActivity.this, LoginActivity.class);
                        startActivity(loginAction);
                    }
                }, "Join us!"));
    }
}