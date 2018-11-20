package com.example.maximebritto.addressbook.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.maximebritto.addressbook.R;
import com.example.maximebritto.addressbook.data.AddressBookApi;
import com.example.maximebritto.addressbook.data.Group;
import com.example.maximebritto.addressbook.data.VolleyDownloader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupListActivity extends AppCompatActivity {

    private RecyclerView ui_groupListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ui_groupListRecyclerView = (RecyclerView)findViewById(R.id.grouplist_recyler_view);
        ui_groupListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ui_groupListRecyclerView.setAdapter(new GroupListAdapter());
    }

    class GroupListAdapter extends RecyclerView.Adapter<GroupCardHolder> {
        private List<Group> _groupList;

        public GroupListAdapter() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            AddressBookApi service = retrofit.create(AddressBookApi.class);
            Map<String, String> data = new HashMap<>();
            data.put("q", "created:>2017-10-22");
            data.put("sort", "stars");
            data.put("order", "desc");
            service.getGroupList(data).enqueue(new Callback<List<Group>>() {
                @Override
                public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                    Log.i("retrofit","Download ok");
                    _groupList = response.body();
                    notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Group>> call, Throwable t) {

                }
            });
        }

        @Override
        public GroupCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cell = LayoutInflater.from(GroupListActivity.this).inflate(R.layout.group_cell,parent,false);
            GroupCardHolder holder = new GroupCardHolder(cell);
            return holder;
        }

        @Override
        public void onBindViewHolder(GroupCardHolder holder, int position) {
            holder.layoutForGroup(_groupList.get(position));
        }

        @Override
        public int getItemCount() {
            int itemCount = 0;
            if (_groupList != null) {
                itemCount = _groupList.size();
            }
            return itemCount;
        }
    }

    class GroupCardHolder extends RecyclerView.ViewHolder {

        private final TextView ui_groupTitle;
        private final NetworkImageView ui_groupImageView;
        private final TextView ui_repositoryName;
        private final TextView ui_description;
        private final TextView ui_numberStars;

        public GroupCardHolder(View cell) {
            super(cell);
            ui_groupTitle = (TextView) cell.findViewById(R.id.group_title);
            ui_groupImageView = (NetworkImageView) cell.findViewById(R.id.group_image);
            ui_repositoryName=(TextView) cell.findViewById(R.id.repo_name);
            ui_description=(TextView) cell.findViewById(R.id.repo_description);
            ui_numberStars =(TextView) cell.findViewById(R.id.number_stars);
        }

        public void layoutForGroup(Group group) {
            ui_groupTitle.setText(group.getOwner().getLogin());
            ui_groupImageView.setImageUrl(group.getOwner().getImage(), VolleyDownloader.getInstance(getApplicationContext()).getImageLoader());
            ui_repositoryName.setText(group.getRepositoryName());
            ui_description.setText(group.getDescription());
            ui_numberStars.setText(group.getNumberStars().toString());
        }
    }

}
