package com.hatsynk.headballclone.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hatsynk.headballclone.R;

public class AvatarSelectionActivity extends Activity {

    // Add all your available avatar drawable IDs here
    private final int[] avatarResources = {
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3,
            R.drawable.avatar_4
            // Add more here if you have them
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_selection);

        GridView gridView = findViewById(R.id.avatarGrid);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1. Get the selected image ID
                int selectedAvatarId = avatarResources[position];

                // 2. Save it to SharedPreferences
                SharedPreferences prefs = getSharedPreferences("GameSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("PLAYER_AVATAR_ID", selectedAvatarId);
                editor.apply();

                Toast.makeText(AvatarSelectionActivity.this, "Avatar Selected!", Toast.LENGTH_SHORT).show();
                finish(); // Close screen and go back to menu
            }
        });
    }

    // Simple adapter to show images in the GridView
    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() { return avatarResources.length; }
        public Object getItem(int position) { return null; }
        public long getItemId(int position) { return 0; }

        // Create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(250, 250)); // Set image size in grid
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(avatarResources[position]);
            return imageView;
        }
    }
}