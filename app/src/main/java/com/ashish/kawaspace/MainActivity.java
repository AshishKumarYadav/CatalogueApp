package com.ashish.kawaspace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.kawaspace.adapter.RecycleViewAdapter;
import com.ashish.kawaspace.model.Result;
import com.ashish.kawaspace.model.UserInfoModel;
import com.ashish.kawaspace.network.ApiClient;
import com.ashish.kawaspace.network.ApiInterface;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//Created by Ashish Kumar Yadav on 22/10/2021
public class MainActivity extends AppCompatActivity implements RecycleViewAdapter.onSelectedCardListener {

    String TAG=MainActivity.class.getSimpleName();
    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigation_drawer;
    ImageView nextBtn,prevBtn,profileImage;
    RecyclerView recyclerView;
    RecycleViewAdapter recycleViewAdapter;
    List<Result> resultList;
    TextView userName,userAddress,userTimeZone,userGender;
    int selectedPosition=-1;
    private LinearLayoutManager mLinearLayoutManager;
    ActionBar actionBar;
    AppBarLayout appBarLayout;
    LinearLayout errorLayout;
    RelativeLayout mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getPeopleInfo();

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            errorLayout.setVisibility(View.GONE);
            mainView.setVisibility(View.VISIBLE);

        } else {

            errorLayout.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);

        }
    }
    void initView(){

        resultList=new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recycler_view);
        nextBtn=findViewById(R.id.next_btn);
        prevBtn=findViewById(R.id.prev_btn);
        profileImage=findViewById(R.id.user_image);
        profileImage.setClipToOutline(true);
        userAddress=findViewById(R.id.user_address);
        userGender=findViewById(R.id.user_gender);
        userTimeZone=findViewById(R.id.user_tz);
        userName=findViewById(R.id.user_name);
        mainView=findViewById(R.id.mainView);
        errorLayout=findViewById(R.id.errorViewLayout);

        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarLayout=findViewById(R.id.appBarLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigation_drawer = (NavigationView) findViewById(R.id.nav_drawer);
        navigation_drawer.setSelected(false);

        drawerLayout.setScrimColor(Color.TRANSPARENT);


        appBarLayout.setEnabled(false);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.white));

        actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle(Html.fromHtml("<font color='#000'>KawaSpace</font>"));

       // drawerLayout.openDrawer(Gravity.RIGHT);
        navigation_drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id) {
                    case R.id.product:
                        Toast.makeText(MainActivity.this,"Product Clicked",Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.pricing:
                        Toast.makeText(MainActivity.this,"Pricing Clicked",Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.download:
                        Toast.makeText(MainActivity.this,"Download Clicked",Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        return true;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            }
        });


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"ADAPTER_POS "+selectedPosition);
                if (selectedPosition==recycleViewAdapter.getItemCount()-1){

                    selectedPosition=0;
                    mLinearLayoutManager.scrollToPosition(selectedPosition);
                    mLinearLayoutManager.setSmoothScrollbarEnabled(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.findViewHolderForAdapterPosition(selectedPosition).itemView.performClick();

                        }
                    },500);

                }else {

                    recyclerView.findViewHolderForAdapterPosition(selectedPosition + 1).itemView.performClick();
                    mLinearLayoutManager.scrollToPosition(selectedPosition+1);
                }
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"ADAPTER_POS1 "+selectedPosition);
                if (selectedPosition==0){
                    selectedPosition=recycleViewAdapter.getItemCount()-1;
                    mLinearLayoutManager.scrollToPosition(selectedPosition); // yourList is the ArrayList that you are passing to your RecyclerView Adapter.
                    Log.d(TAG,"ADAPTER_POS3 "+selectedPosition);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            recyclerView.findViewHolderForAdapterPosition(selectedPosition).itemView.performClick();

                        }
                    },500);

                }else {
                    recyclerView.findViewHolderForAdapterPosition(selectedPosition - 1).itemView.performClick();
                    mLinearLayoutManager.scrollToPosition(selectedPosition-1);
                }

            }
        });

    }

    void getPeopleInfo(){


        final ApiInterface requestInterface = ApiClient.getClient();
        Call<UserInfoModel> call = requestInterface.getProfile();
        call.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(Call<UserInfoModel> call, Response<UserInfoModel> response) {

                if (response.isSuccessful()){

                    resultList=response.body().getResults();
                    setAdapter(resultList);


                }else {

                    Toast.makeText(MainActivity.this,"Something went wrong!",Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onFailure(Call<UserInfoModel> call, Throwable t) {

                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    void setAdapter(List<Result> results){

         recycleViewAdapter = new RecycleViewAdapter(MainActivity.this,results,this);
         recyclerView.setAdapter(recycleViewAdapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onItemSelected(int position,Result result) {
        selectedPosition=position;

        Picasso.get().load(result.getPicture().getLarge()).into(profileImage);

        userName.setText(result.getName().getTitle()+" "+result.getName().getFirst()+" "+result.getName().getLast());
        userAddress.setText(result.getLocation().getStreet().getNumber()+", "+result.getLocation().getStreet().getName()+", "+result.getLocation().getCity()+", "+result.getLocation().getCountry()+", "+result.getLocation().getPostcode());
        userTimeZone.setText(result.getLocation().getTimezone().getOffset()+" "+result.getLocation().getTimezone().getDescription());
        userGender.setText(result.getGender().substring(0, 1).toUpperCase()+result.getGender().substring(1).toLowerCase());

    }
}