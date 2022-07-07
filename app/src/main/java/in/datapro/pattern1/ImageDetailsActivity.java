package in.datapro.pattern1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        ImageView imageView = (ImageView) findViewById(R.id.imgdet);

        if(getIntent() != null){
            String path = getIntent().getStringExtra("image");
            Log.e("viewimage", path);
            Glide.with(this)
                    .load(path)
                    .into(imageView);
        }

    }
}