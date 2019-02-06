package com.monolabs.benja.examengigigo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialogx;
    RequestLocations requestLocations= new RequestLocations();
    JSONArray array_json=null;
    Locations locations;
    private List<Locations> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    ArrayList<Locations> array_locations=new ArrayList<Locations>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String apiKey=getResources().getString(R.string.googlekey);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Adapter(array_locations);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        String posturl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=19.4325987,-99.1419372&radius=1000&types=food&key=AIzaSyDEcz1WoPFetu4diWRd-LwkARpAkTjMqUQ";
        new ConsumirWsLugares().execute(posturl);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position)
            {

                Locations locations = array_locations.get(position);
                String [] var= locations.getOordenadas().split(",");
                String [] varlat=var[0].split(":");
                String lat=varlat[1];
                String [] varlong=var[1].split(":");
                String longitud=varlong[1];
                String nombrelugar=locations.getLugar().toString();
                String ciudad=locations.getCiudad().toString();
               // String pais=locations.getPais().toString();


                Toast.makeText(getApplicationContext(), locations.getLugar() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("latitud" ,lat);
                intent.putExtra("longitud",longitud);
                intent.putExtra("nombrelugar",nombrelugar);
                intent.putExtra("ciudad",ciudad);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConsumirWsLugares extends AsyncTask<String, Integer, JSONObject> {


        @Override
        protected void onPreExecute() {

            pDialogx = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
            pDialogx.setMessage("Cargando ...");
            //pDialogx.setTitle("Cargando alertas...");
            pDialogx.setIndeterminate(false);
            pDialogx.setCancelable(false);
            pDialogx.show();

            super.onPreExecute();


        }


        protected JSONObject doInBackground(String... params) {

           JSONObject json = null;


//            JSONObject json2 = null;
//
//            try {
//                json2 =new JSONObject
//                (
//
//                        "{\n" +
//                                "   \"html_attributions\" : [],\n" +
//                                "   \"results\" : [\n" +
//                                "      {\n" +
//                                "         \"geometry\" : {\n" +
//                                "            \"location\" : {\n" +
//                                "               \"lat\" : -33.870775,\n" +
//                                "               \"lng\" : 151.199025\n" +
//                                "            }\n" +
//                                "         },\n" +
//                                "         \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png\",\n" +
//                                "         \"id\" : \"21a0b251c9b8392186142c798263e289fe45b4aa\",\n" +
//                                "         \"name\" : \"Rhythmboat Cruises\",\n" +
//                                "         \"opening_hours\" : {\n" +
//                                "            \"open_now\" : true\n" +
//                                "         },\n" +
//                                "         \"photos\" : [\n" +
//                                "            {\n" +
//                                "               \"height\" : 270,\n" +
//                                "               \"html_attributions\" : [],\n" +
//                                "               \"photo_reference\" : \"CnRnAAAAF-LjFR1ZV93eawe1cU_3QNMCNmaGkowY7CnOf-kcNmPhNnPEG9W979jOuJJ1sGr75rhD5hqKzjD8vbMbSsRnq_Ni3ZIGfY6hKWmsOf3qHKJInkm4h55lzvLAXJVc-Rr4kI9O1tmIblblUpg2oqoq8RIQRMQJhFsTr5s9haxQ07EQHxoUO0ICubVFGYfJiMUPor1GnIWb5i8\",\n" +
//                                "               \"width\" : 519\n" +
//                                "            }\n" +
//                                "         ],\n" +
//                                "         \"place_id\" : \"ChIJyWEHuEmuEmsRm9hTkapTCrk\",\n" +
//                                "         \"scope\" : \"GOOGLE\",\n" +
//                                "         \"alt_ids\" : [\n" +
//                                "            {\n" +
//                                "               \"place_id\" : \"D9iJyWEHuEmuEmsRm9hTkapTCrk\",\n" +
//                                "               \"scope\" : \"APP\"\n" +
//                                "            }\n" +
//                                "         ],\n" +
//                                "         \"reference\" : \"CoQBdQAAAFSiijw5-cAV68xdf2O18pKIZ0seJh03u9h9wk_lEdG-cP1dWvp_QGS4SNCBMk_fB06YRsfMrNkINtPez22p5lRIlj5ty_HmcNwcl6GZXbD2RdXsVfLYlQwnZQcnu7ihkjZp_2gk1-fWXql3GQ8-1BEGwgCxG-eaSnIJIBPuIpihEhAY1WYdxPvOWsPnb2-nGb6QGhTipN0lgaLpQTnkcMeAIEvCsSa0Ww\",\n" +
//                                "         \"types\" : [ \"travel_agency\", \"restaurant\", \"food\", \"establishment\" ],\n" +
//                                "         \"vicinity\" : \"Pyrmont Bay Wharf Darling Dr, Sydney\"\n" +
//                                "      },\n" +
//                                "      {\n" +
//                                "         \"geometry\" : {\n" +
//                                "            \"location\" : {\n" +
//                                "               \"lat\" : -33.866891,\n" +
//                                "               \"lng\" : 151.200814\n" +
//                                "            }\n" +
//                                "         },\n" +
//                                "         \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
//                                "         \"id\" : \"45a27fd8d56c56dc62afc9b49e1d850440d5c403\",\n" +
//                                "         \"name\" : \"Private Charter Sydney Habour Cruise\",\n" +
//                                "         \"photos\" : [\n" +
//                                "            {\n" +
//                                "               \"height\" : 426,\n" +
//                                "               \"html_attributions\" : [],\n" +
//                                "               \"photo_reference\" : \"CnRnAAAAL3n0Zu3U6fseyPl8URGKD49aGB2Wka7CKDZfamoGX2ZTLMBYgTUshjr-MXc0_O2BbvlUAZWtQTBHUVZ-5Sxb1-P-VX2Fx0sZF87q-9vUt19VDwQQmAX_mjQe7UWmU5lJGCOXSgxp2fu1b5VR_PF31RIQTKZLfqm8TA1eynnN4M1XShoU8adzJCcOWK0er14h8SqOIDZctvU\",\n" +
//                                "               \"width\" : 640\n" +
//                                "            }\n" +
//                                "         ],\n" +
//                                "         \"place_id\" : \"ChIJqwS6fjiuEmsRJAMiOY9MSms\",\n" +
//                                "         \"scope\" : \"GOOGLE\",\n" +
//                                "         \"reference\" : \"CpQBhgAAAFN27qR_t5oSDKPUzjQIeQa3lrRpFTm5alW3ZYbMFm8k10ETbISfK9S1nwcJVfrP-bjra7NSPuhaRulxoonSPQklDyB-xGvcJncq6qDXIUQ3hlI-bx4AxYckAOX74LkupHq7bcaREgrSBE-U6GbA1C3U7I-HnweO4IPtztSEcgW09y03v1hgHzL8xSDElmkQtRIQzLbyBfj3e0FhJzABXjM2QBoUE2EnL-DzWrzpgmMEulUBLGrtu2Y\",\n" +
//                                "         \"types\" : [ \"restaurant\", \"food\", \"establishment\" ],\n" +
//                                "         \"vicinity\" : \"Australia\"\n" +
//                                "      },\n" +
//                                "      {\n" +
//                                "         \"geometry\" : {\n" +
//                                "            \"location\" : {\n" +
//                                "               \"lat\" : -33.870943,\n" +
//                                "               \"lng\" : 151.190311\n" +
//                                "            }\n" +
//                                "         },\n" +
//                                "         \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
//                                "         \"id\" : \"30bee58f819b6c47bd24151802f25ecf11df8943\",\n" +
//                                "         \"name\" : \"Bucks Party Cruise\",\n" +
//                                "         \"opening_hours\" : {\n" +
//                                "            \"open_now\" : true\n" +
//                                "         },\n" +
//                                "         \"photos\" : [\n" +
//                                "            {\n" +
//                                "               \"height\" : 600,\n" +
//                                "               \"html_attributions\" : [],\n" +
//                                "               \"photo_reference\" : \"CnRnAAAA48AX5MsHIMiuipON_Lgh97hPiYDFkxx_vnaZQMOcvcQwYN92o33t5RwjRpOue5R47AjfMltntoz71hto40zqo7vFyxhDuuqhAChKGRQ5mdO5jv5CKWlzi182PICiOb37PiBtiFt7lSLe1SedoyrD-xIQD8xqSOaejWejYHCN4Ye2XBoUT3q2IXJQpMkmffJiBNftv8QSwF4\",\n" +
//                                "               \"width\" : 800\n" +
//                                "            }\n" +
//                                "         ],\n" +
//                                "         \"place_id\" : \"ChIJLfySpTOuEmsRsc_JfJtljdc\",\n" +
//                                "         \"scope\" : \"GOOGLE\",\n" +
//                                "         \"reference\" : \"CoQBdQAAANQSThnTekt-UokiTiX3oUFT6YDfdQJIG0ljlQnkLfWefcKmjxax0xmUpWjmpWdOsScl9zSyBNImmrTO9AE9DnWTdQ2hY7n-OOU4UgCfX7U0TE1Vf7jyODRISbK-u86TBJij0b2i7oUWq2bGr0cQSj8CV97U5q8SJR3AFDYi3ogqEhCMXjNLR1k8fiXTkG2BxGJmGhTqwE8C4grdjvJ0w5UsAVoOH7v8HQ\",\n" +
//                                "         \"types\" : [ \"restaurant\", \"food\", \"establishment\" ],\n" +
//                                "         \"vicinity\" : \"37 Bank St, Pyrmont\"\n" +
//                                "      },\n" +
//                                "      {\n" +
//                                "         \"geometry\" : {\n" +
//                                "            \"location\" : {\n" +
//                                "               \"lat\" : -33.867591,\n" +
//                                "               \"lng\" : 151.201196\n" +
//                                "            }\n" +
//                                "         },\n" +
//                                "         \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/travel_agent-71.png\",\n" +
//                                "         \"id\" : \"a97f9fb468bcd26b68a23072a55af82d4b325e0d\",\n" +
//                                "         \"name\" : \"Australian Cruise Group\",\n" +
//                                "         \"opening_hours\" : {\n" +
//                                "            \"open_now\" : true\n" +
//                                "         },\n" +
//                                "         \"photos\" : [\n" +
//                                "            {\n" +
//                                "               \"height\" : 242,\n" +
//                                "               \"html_attributions\" : [],\n" +
//                                "               \"photo_reference\" : \"CnRnAAAABjeoPQ7NUU3pDitV4Vs0BgP1FLhf_iCgStUZUr4ZuNqQnc5k43jbvjKC2hTGM8SrmdJYyOyxRO3D2yutoJwVC4Vp_dzckkjG35L6LfMm5sjrOr6uyOtr2PNCp1xQylx6vhdcpW8yZjBZCvVsjNajLBIQ-z4ttAMIc8EjEZV7LsoFgRoU6OrqxvKCnkJGb9F16W57iIV4LuM\",\n" +
//                                "               \"width\" : 200\n" +
//                                "            }\n" +
//                                "         ],\n" +
//                                "         \"place_id\" : \"ChIJrTLr-GyuEmsRBfy61i59si0\",\n" +
//                                "         \"scope\" : \"GOOGLE\",\n" +
//                                "         \"reference\" : \"CoQBeQAAAFvf12y8veSQMdIMmAXQmus1zqkgKQ-O2KEX0Kr47rIRTy6HNsyosVl0CjvEBulIu_cujrSOgICdcxNioFDHtAxXBhqeR-8xXtm52Bp0lVwnO3LzLFY3jeo8WrsyIwNE1kQlGuWA4xklpOknHJuRXSQJVheRlYijOHSgsBQ35mOcEhC5IpbpqCMe82yR136087wZGhSziPEbooYkHLn9e5njOTuBprcfVw\",\n" +
//                                "         \"types\" : [ \"travel_agency\", \"restaurant\", \"food\", \"establishment\" ],\n" +
//                                "         \"vicinity\" : \"32 The Promenade, King Street Wharf 5, Sydney\"\n" +
//                                "      }\n" +
//                                "   ],\n" +
//                                "   \"status\" : \"OK\"\n" +
//                                "}"
//                );
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            try {
               json= json();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return  json;
        }

        protected void onPostExecute(JSONObject json) {

          pDialogx.dismiss();
            try
            {
                    array_json = json.getJSONArray("results");


                    for (int i = 0; i < array_json.length(); i++)
                    {
                        locations = new Locations();
                        JSONObject datos = array_json.getJSONObject(i);
                        String coordenadas=datos.getJSONObject("geometry").getString("location").toString();
                        String nombrelugar=datos.getString("name").toString();
                        String ciudad=datos.getString("vicinity").toString();

                        locations.setOordenadas(coordenadas.toString());
                        locations.setLugar(nombrelugar.toString());
                        locations.setCiudad(ciudad);

                        array_locations.add(locations);


                    }

                array_locations.size();
                mAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.toString();

                pDialogx.dismiss();
            }
            catch (Exception e) {
                e.toString();

                pDialogx.dismiss();
                Toast.makeText(MainActivity.this,"No hay alertas por mostrar...",Toast.LENGTH_SHORT).show();
            }


        }
    }

    public   JSONObject  json() throws JSONException {

        JSONObject json=null;
        try {


            json = new JSONObject(

                    "{\n" +
                            "   \"html_attributions\" : [],\n" +
                            "   \"next_page_token\" : \"CtQETAIAAGBPXi2vH3mnkHHFirAPIUjQacieclTFDzq7dS8hqtzMFd_yG0zhwPsY0nof7cPFHAEmGN6-yyOZzQAQIZLzUiW-mZQqgbDlSGTrURQHDQfiZdcZQxj8TEHDcAJMGybJg76U2aoD_mWHTeROaFWQr8bzlBMGlHHjlH9IAnGo6blU2127MUHGSP28gwkCXjak7N-gww1tLcNWg2mPN9TvtyWl3b2-V3k8l5XlWDqVmum6SVOxUJ_rFs6ojCDHz3q9IB91nYoGxpiwEE7isUvD4s5RjIOttN7GOY4ZYGF4ox5n--g_qDn7fZkjpqybdrfxIEuTz7P697QwAfV-Jhn_rxP2AtoBCp0RcC_sp2vIvm5LPYF6M5K4-aeB605F_MpG86f1W7894CYgBGm8t4UYow2zxqTzYleQ8VN2IFlBHokN9Iw-CKY-Bo4z-RvbmUE-zEgaZCIGCxNPZhe1tFfqMqhc2w-Y-srAYLce1Q8zQVdQ6gDoBHMPbJ426ds1iG0q8J-v0dDrhAqLMeU_VeeZyuZ7lJ-6ZvMwsZsLJUiApx0dgl16nyL_w3jizEUxFXoHoz54radVqesjFfTz9iP02vLJtGiATUsD3LQ7LBNPSPIXaQebb693IFr5UTPsATtLBZiEJNcqMIohodBNbU_KF_0HMPeahsftq0ZDjLQ5pmc8NtxZ94gHMAnO5qB7r6My8PYeMtnYN7emnfdGEAL3CH_oZknNw_l3flDozywMCsw6PeyL0CwpdSbeC-zBbZt6H3z5tPsb0wrLSKcY7wDX64sSEEMDF05F-1BEaQgCy9yqM-AaFOMMzQ15xyPdLZlnc8SzdWpMs9_c\",\n" +
                            "   \"results\" : [\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4326077,\n" +
                            "               \"lng\" : -99.133208\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.5927571,\n" +
                            "                  \"lng\" : -98.9604482\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.1887101,\n" +
                            "                  \"lng\" : -99.3267771\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\",\n" +
                            "         \"id\" : \"480f80745b1f911f3aed6a7301a1b75271d6cab4\",\n" +
                            "         \"name\" : \"Ciudad de México\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 694,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/108466859078860513320/photos\\\"\\u003eUtkarsh keshari\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAA4u6bWdP--7h1SKdKF6FDAtkkfl03NlW_g43gJ8cq4agcWacOGFTxC2OuipB5YeSV1JFfUB11Q5svmIvgP_-uIJ141RxjunYhFpBORD-PvKkzjoxxeMgGtqDt7HCOU1EfEhBlCMnXRgJ2nq42UV8vMkgnGhQIkjhIDfOljrquinksesV0YgvuRQ\",\n" +
                            "               \"width\" : 1078\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJB3UJ2yYAzoURQeheJnYQBlQ\",\n" +
                            "         \"reference\" : \"ChIJB3UJ2yYAzoURQeheJnYQBlQ\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"locality\", \"political\" ],\n" +
                            "         \"vicinity\" : \"Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4331599,\n" +
                            "               \"lng\" : -99.1423307\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4344430802915,\n" +
                            "                  \"lng\" : -99.1409934697085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4317451197085,\n" +
                            "                  \"lng\" : -99.14369143029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"3c756a596d0234b380eb27030cd4985dbf6d3176\",\n" +
                            "         \"name\" : \"Hotel Marlowe\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1571,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/106782956164128610182/photos\\\"\\u003eHotel Marlowe\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAYmgra9AqhFnoT8281YosXP35cpb73O5fcaBCupAeTpIm0_ljKC3QbWg4KP2JAIu5tmM_2kVUgqtJ1cxodKIgIPd04oJ2zRCkxkEqcUzHuAQB_T7rw2Cd1e1KR6AFHlMMEhBSVK3TTDbZ5ia-s9KKdEGJGhQ4G6IK27uL5juCq_kDLRcLB7xZyg\",\n" +
                            "               \"width\" : 1572\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJDQRjKyv50YURw0bUFdn6sZg\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM5+73 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM5+73\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.2,\n" +
                            "         \"reference\" : \"ChIJDQRjKyv50YURw0bUFdn6sZg\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 1157,\n" +
                            "         \"vicinity\" : \"Avenida Independencia 17, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.433888,\n" +
                            "               \"lng\" : -99.137855\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4351460302915,\n" +
                            "                  \"lng\" : -99.1365204197085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4324480697085,\n" +
                            "                  \"lng\" : -99.13921838029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"dc29918852dc80bbcd2fd32df29f8cf23fec2d9d\",\n" +
                            "         \"name\" : \"Hotel Ritz\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 3259,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/108261801381770772582/photos\\\"\\u003eHotel Ritz\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAUzNCh2cz1i12r4kqFitrh60I98BT7Pf75C22I-_uOCUlImQrjDoSg6tkL5ng33xihRiMORBzt7w213qC8FrqkFJ0-5IQ3kGc63dg6aQXPH-GmlSnA8lRNwhmnmeCIauaEhDIDkAAcCeSxSH1Bn3WrdQ8GhTlXem2ZxKbQsM_Iy9XYx73eF5EEQ\",\n" +
                            "               \"width\" : 4907\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJC98k0Cz50YURN1_YgwTly-I\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM6+HV Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM6+HV\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.2,\n" +
                            "         \"reference\" : \"ChIJC98k0Cz50YURN1_YgwTly-I\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 1374,\n" +
                            "         \"vicinity\" : \"30, Avenida Francisco I. Madero, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4345003,\n" +
                            "               \"lng\" : -99.14675090000001\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4359738802915,\n" +
                            "                  \"lng\" : -99.1456377197085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4332759197085,\n" +
                            "                  \"lng\" : -99.14833568029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"069dfe85ad13306411a312e47c12e01540d983ba\",\n" +
                            "         \"name\" : \"Fiesta Inn Centro Histórico\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 3840,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/102816773273168406937/photos\\\"\\u003eFiesta Inn Centro Histórico\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAbf5bl8j3aKPk3CTnGrrGxlfWerGckXViwpgpv-lbULnTaGIeXZC0X9tOQBgkTgyktnZ-eIB4mo4d-Q7rJG1JZAjWE2bYmDMqHxVgiWQ-xjEOB6hQcoNNsI27tvpimJcJEhCJvfj3hvd3c7Yv_LXC5aafGhQwhgflR2tdJk0EwRvGTNpNYvPXhA\",\n" +
                            "               \"width\" : 3853\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJ815uctX40YUREyhhjRQ0zFc\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM3+R7 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM3+R7\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.5,\n" +
                            "         \"reference\" : \"ChIJ815uctX40YUREyhhjRQ0zFc\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 949,\n" +
                            "         \"vicinity\" : \"Avenida Juárez 76, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4316337,\n" +
                            "               \"lng\" : -99.13531019999999\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4329928802915,\n" +
                            "                  \"lng\" : -99.13403066970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4302949197085,\n" +
                            "                  \"lng\" : -99.1367286302915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"67091f80d05e207bd9bc529f02d5597eccde115d\",\n" +
                            "         \"name\" : \"Hotel NH Mexico City Centro Histórico\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1473,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105942881659506521585/photos\\\"\\u003eHotel NH Mexico City Centro Histórico\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAA41R_jcBOWOfbwyuvU5f7htGMhHQ0Vgvfw8oTz-qAuVPU4J3xGwMx3C5i2xY53o1qdHjScO1Yj3I0-ZNZ6nuCNnHdpuFI1jPY66khV_w66UZsZn-wKldmT2mXt8jEK0C8EhBZGMysp5dA5FHCVmkzpXNUGhQrrYTOXtIvBWUgEwJDnv4nvK4d4Q\",\n" +
                            "               \"width\" : 2048\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJT-3gltL-0YURqaJ9bHFgp-o\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVJ7+MV Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVJ7+MV\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.4,\n" +
                            "         \"reference\" : \"ChIJT-3gltL-0YURqaJ9bHFgp-o\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 1488,\n" +
                            "         \"vicinity\" : \"Calle de la Palma 42, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.434211,\n" +
                            "               \"lng\" : -99.1449614\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4355552802915,\n" +
                            "                  \"lng\" : -99.14358146970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4328573197085,\n" +
                            "                  \"lng\" : -99.1462794302915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"1f67ae33ab22c43ddc3f622baa8d52f8f10bea00\",\n" +
                            "         \"name\" : \"Hotel San Francisco Centro Histórico\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 869,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/100186823919235784370/photos\\\"\\u003eHotel San Francisco Centro Histórico\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAqO5YSUycdpIfFzKG_1WkNFWNycalx9QVlwb6004OXlb_CfrPzHMFwuk-XkGWe_LMfwa8AiKuhQFpfRgQsNjGJprU-aLhcg47L4Fx52GVjBwQTukJe1v13PFvk5FjvVhbEhADxPbjzSzIdB8f7N3SExQ-GhRgnzi5GBUEI_rtIybp3QO42BBnHA\",\n" +
                            "               \"width\" : 1502\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJBdL7GSj60YURqtLROaT9m0U\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM4+M2 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM4+M2\"\n" +
                            "         },\n" +
                            "         \"rating\" : 3.7,\n" +
                            "         \"reference\" : \"ChIJBdL7GSj60YURqtLROaT9m0U\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 1509,\n" +
                            "         \"vicinity\" : \"Luis Moya 11, Cuauhtémoc, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4320832,\n" +
                            "               \"lng\" : -99.1345917\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4333897302915,\n" +
                            "                  \"lng\" : -99.13342471970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4306917697085,\n" +
                            "                  \"lng\" : -99.13612268029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"e96e035ea6e6f7da93d4834781242f4d5af6ca2a\",\n" +
                            "         \"name\" : \"Gran Hotel Ciudad de México\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 2362,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/103679786293097838208/photos\\\"\\u003eGran Hotel Ciudad de México\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAW-7X_8jGZW5WdV04Xsu6Df2FVkkYzlnza2F_TIS3mWWLj2Nr7bP2B1UHqxj44XhI-BdElviead0LZ924HKou9an2Ky7bzDCS7iUg_zL6ejG0JLUoBzpDByuK7SVTHWmSEhBpDhikuFqYZN5QTJSO9kXBGhSD4NP52wPCqCh7c6M585QS64yktA\",\n" +
                            "               \"width\" : 1827\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJQRMdGVcBzoURTPsEkZCr5tY\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVJ8+R5 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVJ8+R5\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.6,\n" +
                            "         \"reference\" : \"ChIJQRMdGVcBzoURTPsEkZCr5tY\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 3608,\n" +
                            "         \"vicinity\" : \"16 de Septiembre 82, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.432976,\n" +
                            "               \"lng\" : -99.13439099999999\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4344342302915,\n" +
                            "                  \"lng\" : -99.13302631970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.43173626970849,\n" +
                            "                  \"lng\" : -99.1357242802915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"322e1f2b061d3d4db7af8d971e1301d257f891b8\",\n" +
                            "         \"name\" : \"Best Western Hotel Majestic\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 3024,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105203357866181151471/photos\\\"\\u003eBest Western Hotel Majestic\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAaqk0vow8imOOmB4kV0GmwlZsi85_O-ZHI6O0gxDd63BMY_3NAeDv2E4XVrB-2TSjqpSqulJuD2JhP34ex4uYBNuQ_eJUwBReZ5T5-tXeF2NZTBVtAb3TnNtEh9q4LZQlEhDKhpsZlxQ9A-z-3V2dErxsGhRfpfc24vodqatZ3KbFdb7qP6Myjg\",\n" +
                            "               \"width\" : 4032\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJfetDFAz30YURj-LOq8vtIXU\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM8+56 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM8+56\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.1,\n" +
                            "         \"reference\" : \"ChIJfetDFAz30YURj-LOq8vtIXU\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 729,\n" +
                            "         \"vicinity\" : \"Avenida Francisco I. Madero 73, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4300369,\n" +
                            "               \"lng\" : -99.13450189999999\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4314295802915,\n" +
                            "                  \"lng\" : -99.1331724197085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4287316197085,\n" +
                            "                  \"lng\" : -99.13587038029152\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"c6dd54521e4c6545edcf1de1e5112c58e67bc76e\",\n" +
                            "         \"name\" : \"Hampton Inn & Suites Mexico City - Centro Historico\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 853,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/112670215792862638973/photos\\\"\\u003eHampton Inn &amp; Suites Mexico City - Centro Historico\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAP5adEK6DoR5JAFVMmTBLPetSAIXfW-JEn5zICYWt3wNw0qwABOfX3DmyxBetFJrSO5KIkkx5hG7TMQ5NVQlM-SJG9EJQMcpZVgm3p2js7krSDn7HJk4dS8o9BTMcGKtLEhDQWdIzUPhWjhmfw5QZzkbIGhQI1MdfGSRgo7T73TgHZbkwte9bgQ\",\n" +
                            "               \"width\" : 1280\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJUa70P4D40YURJKbiQycyPfM\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVJ8+25 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVJ8+25\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.5,\n" +
                            "         \"reference\" : \"ChIJUa70P4D40YURJKbiQycyPfM\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 811,\n" +
                            "         \"vicinity\" : \"5 de Febrero No. 24, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4345819,\n" +
                            "               \"lng\" : -99.14630080000001\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4359955802915,\n" +
                            "                  \"lng\" : -99.14491876970851\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4332976197085,\n" +
                            "                  \"lng\" : -99.14761673029152\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"c08f410950384309cacd1b7f2becad8ba91c5855\",\n" +
                            "         \"name\" : \"Hilton Mexico City Reforma\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1342,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105560608813767917753/photos\\\"\\u003eHilton Mexico City Reforma\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAHQuy3m-2A7LyKe60xPVMHWc-p3qF_rbs9rRp-v3oHW-M-p81U-eC3aiqYs7F1TQJmoZ3ZtVobEl6vFZrB2ByKRo9uNET8qNJNQ4_hkAdT8nEjq0SQJ4ePBPz8gT8sGb2EhBOP4jz-4br5I3lER2jSKo-GhTZyJHo8Tsc1gKp9AuZ3WYGRE0lFw\",\n" +
                            "               \"width\" : 1343\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJpXG3CjXKOoQRiUtJrZVnNsw\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM3+RF Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM3+RF\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.6,\n" +
                            "         \"reference\" : \"ChIJpXG3CjXKOoQRiUtJrZVnNsw\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 7752,\n" +
                            "         \"vicinity\" : \"Avenida Juárez 70, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.436971,\n" +
                            "               \"lng\" : -99.135426\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4383715302915,\n" +
                            "                  \"lng\" : -99.1340690697085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4356735697085,\n" +
                            "                  \"lng\" : -99.13676703029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"9d89ac411a07312676164ff9c770c3ed278725ef\",\n" +
                            "         \"name\" : \"Hotel Habana\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1840,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/113200919382666308005/photos\\\"\\u003ePere Font\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAA1d55iOaeeGEHFfWo82k4ujkZaJIhW-TIL6JyoRDZbNCiw__T_-Uu9nlmicxBVCdTV32YiwO46MBWLjeKQXVHtcb8nH9b1PXTjMjR_Xkaz5QqDUrJEKxHI3PybMNCfxXqEhB6oJBvpNuIp-2loUYUf_0jGhR8a06uiuUBWAl4J9RJPoSXyE2EvQ\",\n" +
                            "               \"width\" : 3264\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJHUMfYOcc0oURovVgIQbN0P0\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVP7+QR Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVP7+QR\"\n" +
                            "         },\n" +
                            "         \"rating\" : 3.9,\n" +
                            "         \"reference\" : \"ChIJHUMfYOcc0oURovVgIQbN0P0\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 393,\n" +
                            "         \"vicinity\" : \"Calle Republica de Cuba 77, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.427768,\n" +
                            "               \"lng\" : -99.1353032\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4290827302915,\n" +
                            "                  \"lng\" : -99.1339590697085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4263847697085,\n" +
                            "                  \"lng\" : -99.1366570302915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"4610ebd24bc7db469f6d4d56e200421ce8eef4ca\",\n" +
                            "         \"name\" : \"Hostal Regina Centro Histórico Ciudad de México \\\"YOUTH PARTY”\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 853,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/115271006288873112382/photos\\\"\\u003eHostal Regina Centro Histórico Ciudad de México &quot;YOUTH HOSTEL&quot;\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAABVvvRuTE7sy-cuBPZvoHM9yyJg95hLaBWy7kqjV1adU0ej4QJ-5SYfSLVafsHyWLom0Gmp6ZkShofrQS-vH6E8olzdMkuec0Z3Eu4H2DQwGUVWQDAtyt0TusgZY4M_3UEhCU-cytCxBaBSzPieHyiDo0GhSGtzh2Gzl98HaJDBpqUpjWNQyibw\",\n" +
                            "               \"width\" : 1280\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJ04epHNL-0YURPB6OWeF8eis\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVH7+4V Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVH7+4V\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.4,\n" +
                            "         \"reference\" : \"ChIJ04epHNL-0YURPB6OWeF8eis\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 903,\n" +
                            "         \"vicinity\" : \"Calle Regina 58, Centro Histórico, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4362348,\n" +
                            "               \"lng\" : -99.147667\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4376503302915,\n" +
                            "                  \"lng\" : -99.1464202197085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4349523697085,\n" +
                            "                  \"lng\" : -99.14911818029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"dfcc7c29b6df2adbb417d5defeb31ec6d2d17251\",\n" +
                            "         \"name\" : \"Hotel Fontán Reforma\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1836,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/110153737322523925246/photos\\\"\\u003eMari M. R.\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAORENZIjuxCkmFnLpXCicLPyPVbTHeZFA_FF1QbDcTzXytm-errOFy989NgWjKc45baA5gOoFe5a8Y3u7W5D5miUsayGCRvc5McOEgQRruRqcZlHbNG4WT7B-kGGp6ZEwEhDAWzvUGUlXmi1NlJmFyx4NGhTJMXGH3PBA2pjeI_dX5L6NfMRlOQ\",\n" +
                            "               \"width\" : 3264\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJ1VyGauz80YURnXg5vzbgpoU\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVP2+FW Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVP2+FW\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.1,\n" +
                            "         \"reference\" : \"ChIJ1VyGauz80YURnXg5vzbgpoU\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 2891,\n" +
                            "         \"vicinity\" : \"Avenida Paseo de la Reforma 24, Cuauhtémoc, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.438832,\n" +
                            "               \"lng\" : -99.13963\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4400589802915,\n" +
                            "                  \"lng\" : -99.1383006697085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.43736101970849,\n" +
                            "                  \"lng\" : -99.14099863029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"48898ee5b480db85bc03c381b9bc2c2b172a3513\",\n" +
                            "         \"name\" : \"HOTEL DILIGENCIAS\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1878,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/100107928028953446054/photos\\\"\\u003eHOTEL DILIGENCIAS\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAZ0UoqQQHRBnypYAQBqoWLRjmh6OHHDtmxwON9X4Xzj73xczRcZ3OHfUe6Hcv5IgDw4ChfvWWqJljWbsiUC7EfHnc47gf999mrtzpgb09i_titsblGTgX66Os7kaC1-psEhCs_oO_U0YAqn-gm7RbVtXaGhTQZjHZ2d61F5U2J-hjwLHo16ak4g\",\n" +
                            "               \"width\" : 2500\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJt5t7NS350YURDL5EqAqohLg\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVQ6+G4 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVQ6+G4\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4,\n" +
                            "         \"reference\" : \"ChIJt5t7NS350YURDL5EqAqohLg\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 1104,\n" +
                            "         \"vicinity\" : \"Belisario Domínguez 6, Centro\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.435231,\n" +
                            "               \"lng\" : -99.137964\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4365896302915,\n" +
                            "                  \"lng\" : -99.13668096970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.43389166970849,\n" +
                            "                  \"lng\" : -99.1393789302915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"2dbf518b7554074f027ac9ebe9e118afd50f5fa7\",\n" +
                            "         \"name\" : \"Chillout Flats Bed & Breakfast\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 3456,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/104223084530385725016/photos\\\"\\u003eChillout Flats Bed &amp; Breakfast\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAATq1GYSjsk2R7fe-uLZuAh0otXI06aJKVHQ2ot6gif0_MVQl8MIm2OiL1YSBt1y9ni4QTU9fvsUCa-ZXHK_RMkUOjYSUxNK1w2TDZvutrILYHMEx36X6qeqEU6YE67dpFEhDbAyIk9OIKNT_3aklYLnApGhRNKTf958uRKWbBkoh5Lew33sb6FA\",\n" +
                            "               \"width\" : 4608\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJswN65Sz50YURsw_vTBZNJvY\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVP6+3R Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVP6+3R\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.5,\n" +
                            "         \"reference\" : \"ChIJswN65Sz50YURsw_vTBZNJvY\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 48,\n" +
                            "         \"vicinity\" : \"Calle de Bolívar 8, Centro, Cuauhtémoc\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4342947,\n" +
                            "               \"lng\" : -99.1482515\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4356551302915,\n" +
                            "                  \"lng\" : -99.14695861970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4329571697085,\n" +
                            "                  \"lng\" : -99.14965658029149\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"88d99563e8701b05b70d9c6c37b8b3afa0998fd8\",\n" +
                            "         \"name\" : \"Hotel Ambassador\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1440,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/107963763573932584410/photos\\\"\\u003eLUIS MORA\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAXsByy3D1AUo5UXSy-NNyEpNKTMHdxfOC3EiDLCYkZ62thnf7HHGWQzWfkzIUWEI8EaMgUBPALqU8DzKJD25ky1cyqd1FB8Kqip5U6nvFvBU_dJhyzUVGqccNFHMFsdjUEhCfLqhgZ9gKAM6a8Ng2dUnVGhQPmo-kufNewtdSf6XaHou1KY2dZQ\",\n" +
                            "               \"width\" : 2560\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJlRc_HtX40YURh31Ld7kxtYc\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM2+PM Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM2+PM\"\n" +
                            "         },\n" +
                            "         \"rating\" : 3.4,\n" +
                            "         \"reference\" : \"ChIJlRc_HtX40YURh31Ld7kxtYc\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 319,\n" +
                            "         \"vicinity\" : \"Humboldt 38, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4329837,\n" +
                            "               \"lng\" : -99.14539859999999\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4343089802915,\n" +
                            "                  \"lng\" : -99.1439127197085\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4316110197085,\n" +
                            "                  \"lng\" : -99.1466106802915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"e77bae75eac609f3d2bf57b975e60f7a83957ba8\",\n" +
                            "         \"name\" : \"Hotel Metropol\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 750,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/109411485609818012219/photos\\\"\\u003eHotel Metropol\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAA7L32fEBOm4ba5cI9njkl0RQe8pYRFZ0eikTOym8H6m9s8MiV581f5cW8fsorWydv1MTIZB-esdlxjfusp2VKTr3t1XxDW_CnRQpFHemNKV9Hz5Umdc_AIiPswHEF7SQfEhDlJpF7LmqjvUelq_Kaiie5GhRHcaS8W_aERjgliO5SmS07zgPBlw\",\n" +
                            "               \"width\" : 1000\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJkchlrCr50YURsHnsKCBs_hg\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVM3+5R Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVM3+5R\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.1,\n" +
                            "         \"reference\" : \"ChIJkchlrCr50YURsHnsKCBs_hg\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 1368,\n" +
                            "         \"vicinity\" : \"Luis Moya 39, Centro\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4303032,\n" +
                            "               \"lng\" : -99.13900489999999\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4316414302915,\n" +
                            "                  \"lng\" : -99.13757721970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4289434697085,\n" +
                            "                  \"lng\" : -99.14027518029151\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"541c0112b2b03884fa41a2613d6a2a97a6258fa4\",\n" +
                            "         \"name\" : \"Hotel la Casa de la Luna\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 375,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/112190963746580258273/photos\\\"\\u003eHotel la Casa de la Luna\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAKsK18vsQt5pneANJv7nsepqTM1Loab0vG45d9wAn3orjiXBbzBl1pjXeIizKNAqEg-DqHR8z6QnfGNU9NRQC4ri3CkxKLL46XvQ9szUQuk3C57Wv_IW6WTkXcqVYnMMEEhCq5HSV5NaW3W-AKmks9SCqGhQK0pNlGil7rHd6XhxkX7XiPSNzaw\",\n" +
                            "               \"width\" : 952\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJewylddP-0YURQf5guIUjfv4\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVJ6+49 Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVJ6+49\"\n" +
                            "         },\n" +
                            "         \"rating\" : 3.9,\n" +
                            "         \"reference\" : \"ChIJewylddP-0YURQf5guIUjfv4\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 226,\n" +
                            "         \"vicinity\" : \"Calle Bolivar 57, Cuauhtémoc, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4356082,\n" +
                            "               \"lng\" : -99.1339108\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4369535802915,\n" +
                            "                  \"lng\" : -99.13253246970849\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.4342556197085,\n" +
                            "                  \"lng\" : -99.1352304302915\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png\",\n" +
                            "         \"id\" : \"0f8d8aaa32e1118e35e89c3b0c86412e13b0b1ff\",\n" +
                            "         \"name\" : \"México City Hostel\",\n" +
                            "         \"opening_hours\" : {\n" +
                            "            \"open_now\" : true\n" +
                            "         },\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 1920,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/112053604095243903854/photos\\\"\\u003eMaría Camila González\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAAZHf6lt_UKVDY1rkPQpgpNpaZ6mhVNjlYvM3woOJXPOdfvGy4MUWaevy44hn7O6BVblPQwwtOCu4ccSjXv6RfgI_h0z5HQ81_zz7Oo5TcRMQAUSzPRG5_uwbAFopqNZIMEhBUlaBTC7Aiqtxeyrpx3UEXGhSIBD3XonvsaG8qH3h-QkWz_rDZFA\",\n" +
                            "               \"width\" : 3840\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJEwaxmjL50YURQTcndsW1Tsw\",\n" +
                            "         \"plus_code\" : {\n" +
                            "            \"compound_code\" : \"CVP8+6C Ciudad de México, México\",\n" +
                            "            \"global_code\" : \"76F2CVP8+6C\"\n" +
                            "         },\n" +
                            "         \"rating\" : 4.3,\n" +
                            "         \"reference\" : \"ChIJEwaxmjL50YURQTcndsW1Tsw\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"lodging\", \"point_of_interest\", \"establishment\" ],\n" +
                            "         \"user_ratings_total\" : 636,\n" +
                            "         \"vicinity\" : \"República de Brasil 8, Centro, Ciudad de México\"\n" +
                            "      },\n" +
                            "      {\n" +
                            "         \"geometry\" : {\n" +
                            "            \"location\" : {\n" +
                            "               \"lat\" : 19.4302678,\n" +
                            "               \"lng\" : -99.1373136\n" +
                            "            },\n" +
                            "            \"viewport\" : {\n" +
                            "               \"northeast\" : {\n" +
                            "                  \"lat\" : 19.4439193,\n" +
                            "                  \"lng\" : -99.1231621\n" +
                            "               },\n" +
                            "               \"southwest\" : {\n" +
                            "                  \"lat\" : 19.422177,\n" +
                            "                  \"lng\" : -99.15365799999999\n" +
                            "               }\n" +
                            "            }\n" +
                            "         },\n" +
                            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\",\n" +
                            "         \"id\" : \"53f7b6d1be3997f76a45dbd049dac71d9fc529d5\",\n" +
                            "         \"name\" : \"Centro\",\n" +
                            "         \"photos\" : [\n" +
                            "            {\n" +
                            "               \"height\" : 3000,\n" +
                            "               \"html_attributions\" : [\n" +
                            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/102083889664863225178/photos\\\"\\u003eJuan Pablo Toral\\u003c/a\\u003e\"\n" +
                            "               ],\n" +
                            "               \"photo_reference\" : \"CmRaAAAA5KMMhWcp7jJRkDM0-5BUdOTrfpbyAeQX3d6EPOihJ-cj3eVbYlq6oUMoV39W8mIS6dyoStc0DJX1dp_IQBm-wzaCvhXZZorD2pMRXTG67_vQt1h1sm8qOKwNMpsT9QSdEhC1aTw5s-lDk6jeE3oG0rqSGhS9u5aNJsDQ0--Dkk-21jgDkQTFVQ\",\n" +
                            "               \"width\" : 4000\n" +
                            "            }\n" +
                            "         ],\n" +
                            "         \"place_id\" : \"ChIJ8bh9yyz50YURlL2cFjXnr98\",\n" +
                            "         \"reference\" : \"ChIJ8bh9yyz50YURlL2cFjXnr98\",\n" +
                            "         \"scope\" : \"GOOGLE\",\n" +
                            "         \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ],\n" +
                            "         \"vicinity\" : \"Centro\"\n" +
                            "      }\n" +
                            "   ],\n" +
                            "   \"status\" : \"OK\"\n" +
                            "}"

            );
        }catch (JSONException e)
        {
            e.toString();
        }
        catch (Exception ex)
        {
            ex.toString();
        }

        return json;
    }
}
