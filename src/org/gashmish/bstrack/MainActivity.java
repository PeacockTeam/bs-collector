package org.gashmish.bstrack;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.start);
        button.setOnClickListener(mStartListener);
        button = (Button)findViewById(R.id.stop);
        button.setOnClickListener(mStopListener);
    }

    private OnClickListener mStartListener = new OnClickListener() {
        public void onClick(View v) {
            startService(new Intent(MainActivity.this, LocalService.class));
            
            Toast.makeText(getBaseContext(), "Started", Toast.LENGTH_SHORT).show();
        }
    };

    private OnClickListener mStopListener = new OnClickListener() {
        public void onClick(View v) {
            stopService(new Intent(MainActivity.this, LocalService.class));
            
            Toast.makeText(getBaseContext(), "Stopped", Toast.LENGTH_SHORT).show();
        }
    };
}
