package performance.cleaner.codebreaker.batteryperformance.Activities;

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


public class Rate_me extends Activity
{
    private RatingBar rate_me;
    private TextView later;
    private TextView rate_now;

    float rate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_me);

        Typeface canaro = Typeface.createFromAsset(getAssets(),"commercial/Canaro-LightDEMO.otf");

        rate_me = (RatingBar)findViewById(R.id.rate_me);
        later = (TextView)findViewById(R.id.later);
        rate_now = (TextView)findViewById(R.id.rate_now);

        rate_me.setNumStars(5);

        TextView Love_this = (TextView)findViewById(R.id.love_this);
        TextView Please = (TextView)findViewById(R.id.please);

        Love_this.setTypeface(canaro);
        Please.setTypeface(canaro);
        rate_now.setTypeface(canaro);
        later.setTypeface(canaro);


        DisplayMetrics rm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(rm);

        int width = rm.widthPixels;
        int height = rm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.35));

        onclicklistners();

    }

    public void onclicklistners()
    {
        rate_me.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
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

        rate_now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (rate == 5)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=performance.cleaner.codebreaker.batteryperformance"));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Rate_me.this, "Rated successfully",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
