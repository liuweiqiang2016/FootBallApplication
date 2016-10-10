package myapp.alex.com.footballapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private  HolderFragment fragment;
    private Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                if (null != fragment) {
                    if (fragment.mWebView.canGoBack()) {

                        fragment.mWebView.goBack();

                    }else {
                        Toast.makeText(MainActivity.this, getString(R.string.main_toast), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MainActivity.this, getString(R.string.main_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);//设置菜单图标恢复本来的颜色
        navigationView.setCheckedItem(R.id.nav_innews);//设置默认选择第一个menu子项
        initView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();修改为回到Home界面
            Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
            mHomeIntent.addCategory(Intent.CATEGORY_HOME);
            mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(mHomeIntent);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String title=item.getTitle().toString();

        if (id == R.id.nav_innews) {
            setFragment(AppUtils.IN_NEWS,title);
        } else if (id == R.id.nav_ennews) {

            setFragment(AppUtils.EN_NEWS,title);

        } else if (id == R.id.nav_spnews) {

            setFragment(AppUtils.SP_NEWS,title);

        } else if (id == R.id.nav_genews) {

            setFragment(AppUtils.GE_NEWS,title);

        } else if (id == R.id.nav_itnews) {
            setFragment(AppUtils.IT_NEWS,title);

        } else if (id == R.id.nav_cnnews) {
            setFragment(AppUtils.CN_NEWS,title);

        } else if (id == R.id.nav_ftbbs) {
            setFragment(AppUtils.FC_BBS,title);

        } else if (id == R.id.nav_clubbbs) {
            setFragment(AppUtils.CLUB_BSS,title);

        } else if (id == R.id.nav_bxjbbs) {
            setFragment(AppUtils.BXJ_BBS,title);

        }else if (id == R.id.nav_about) {

            aboutAPP(title);

        }else if (id == R.id.nav_exit) {

            exitAPP();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //根据点击，生成一个对应Fragment
    private void setFragment(String url,String title) {
        toolbar.setTitle(title);
//        fragment=new HolderFragment(url);
        fragment=HolderFragment.newInstance(url);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        drawer.closeDrawers();
    }

    private void initView(){

//        fragment=new HolderFragment(AppUtils.IN_NEWS);
        fragment=HolderFragment.newInstance(AppUtils.IN_NEWS);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    private void aboutAPP(String title){

        AboutFragment fragment=new AboutFragment();
        toolbar.setTitle(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        drawer.closeDrawers();

    };


    private void exitAPP(){
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    };

    @Override
    protected void onDestroy() {
        Log.e( "onDestroy: ","销毁");
        super.onDestroy();
    }
}
