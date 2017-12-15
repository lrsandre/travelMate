package mobil.commerce.travelmate;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import mobil.commerce.travelmate.objects.RouteObject;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9000;

    private ArrayList<RouteObject> routes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isServicesOK()){
            addExampleRoutes();
            init();
        }

    }
    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        ListView routeListView = (ListView) findViewById(R.id.listView_routes);
        String[] routenames = new String[routes.size()];
        for(int i = 0; i < routenames.length; i++) {
            routenames[i] = routes.get(i).getName();
        }

        ListAdapter routesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, routenames);
        routeListView.setAdapter(routesAdapter);

        routeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent3 = new Intent(MainActivity.this, RoutePlaner.class);
                intent3.putExtra("route", routes.get(i));
                startActivity(intent3);
            }
        });

        Button btn_diaryTest = (Button) findViewById(R.id.btn_diaryTest);
        btn_diaryTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TravelDiary.class);
                intent.putExtra("diary", routes.get(0).getDiaryList());
                startActivity(intent);
            }
        });

    }

    private void addExampleRoutes() {
        routes.clear();
        routes.add(new RouteObject("Süd-Amerika"));
        routes.add(new RouteObject("Schweden"));
        routes.add(new RouteObject("Thailand"));

    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // handle click events for action bar items
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.reisetagebuch:
                Intent intent= new Intent(this, TravelDiary.class );
                startActivity(intent);
                return true;

            case R.id.meineRouten:
                Intent intent1= new Intent(this, MyRoutes.class);
                startActivity(intent1);
                return true;

            case R.id.gefährten:
                Intent intent2= new Intent(this, Mates.class);
                startActivity(intent2);
                return true;

            case R.id.routenplanung:
                Intent intent3 = new Intent(this, RoutePlaner.class);
                startActivity(intent3);
                return true;



            default:
                return super.onOptionsItemSelected(item);


        }

    }
}


