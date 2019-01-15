package performance.cleaner.codebreaker.batteryperformance.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import performance.cleaner.codebreaker.batteryperformance.R;


public class RateIt extends Activity
{
    private RatingBar rateMe;
    private TextView later;
    private TextView rateNow;
    float rate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_me);

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");

        rateMe = (RatingBar)findViewById(R.id.rate_me);
        later = (TextView)findViewById(R.id.later);
        rateNow = (TextView)findViewById(R.id.rate_now);

        rateMe.setNumStars(5);

        TextView Love_this = (TextView)findViewById(R.id.love_this);
        TextView Please = (TextView)findViewById(R.id.please);

        Love_this.setTypeface(canaro);
        Please.setTypeface(canaro);
        rateNow.setTypeface(canaro);
        later.setTypeface(canaro);


        DisplayMetrics rm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(rm);

        int width = rm.widthPixels;
        int height = rm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.35));

        onClick();

    }

    public void onClick()
    {
        rateMe.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate = rating;
            }
        });

        later.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               finish();
            }
        });

        rateNow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (rate == 5)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RateIt.this, "Rated successfully",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
