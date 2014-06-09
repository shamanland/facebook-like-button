package com.shamanland.facebook.likebutton.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListLikeActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.activity_list_like);

        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(new LikeAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListLikeActivity.this.onItemClick((String) adapterView.getAdapter().getItem(i));
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void onItemClick(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);

        OpenUrlDialogFragment fragment = new OpenUrlDialogFragment();
        fragment.setArguments(args);
        fragment.show(getSupportFragmentManager(), "open.url");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
