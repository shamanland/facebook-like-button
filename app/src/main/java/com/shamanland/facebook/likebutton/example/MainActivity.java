package com.shamanland.facebook.likebutton.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        View.OnClickListener urlClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProjectPageClicked(((TextView) view.findViewById(android.R.id.text1)).getText().toString());
            }
        };

        findViewById(R.id.project_page).setOnClickListener(urlClickListener);
        findViewById(R.id.sources).setOnClickListener(urlClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(Menu.NONE, android.R.id.list, Menu.NONE, R.string.list);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.list:
                startActivity(new Intent(this, ListLikeActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onProjectPageClicked(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);

        OpenUrlDialogFragment fragment = new OpenUrlDialogFragment();
        fragment.setArguments(args);
        fragment.show(getSupportFragmentManager(), "open.url");
    }
}
