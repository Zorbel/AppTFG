package com.example.zorbel.apptfg;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.PoliticalParty;
import com.example.zorbel.service.GetProgramsData;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PoliticalProgramIndexActivity extends ActionBarActivity {

    //Left Menu
    private ListView drawerListLeft;
    private String[] tagTitles;

    //Right Menu Index
    private ExpandableListView drawerListRight;
    private ExpandableListAdapter mListAdapter;
    private List<String> mListDataHeader;

    //Nav Drawer menus
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //Expandable List Index
    private ExpandableListView mIndexListView;
    private ExpandableListAdapter mListAdapterIndex;
    private List<String> mListDataHeaderIndex;

    private PoliticalParty polParty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political_program_index);

        createMenus();

        mIndexListView =(ExpandableListView) findViewById(R.id.expandableListView);

        int polIndex = getIntent().getExtras().getInt("PoliticalPartyIndex");

        polParty = PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(polIndex);

        if (polParty.getmSectionRoot() == null) {
            getProgramSectionsData(polParty.getmId());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    public void createMenus() {

        //MENU LEFT NAV DRAWER

        //Obtener arreglo de strings desde los recursos
        tagTitles = getResources().getStringArray(R.array.MenuEntries);
        //Obtener drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Obtener listview
        drawerListLeft = (ListView) findViewById(R.id.left_drawer);
        drawerListRight = (ExpandableListView) findViewById(R.id.right_drawer);

        //Fill the index right menu
        getIndexTitles(0);

        //Nueva lista de drawer items
        ArrayList<MenuLeftItem> items = new ArrayList<MenuLeftItem>();
        items.add(new MenuLeftItem(tagTitles[0]));
        items.add(new MenuLeftItem(tagTitles[1]));
        items.add(new MenuLeftItem(tagTitles[2]));
        items.add(new MenuLeftItem(tagTitles[3]));


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerListLeft.setAdapter(new MenuLeftListAdapter(this, items));

        drawerListLeft.setOnItemClickListener(new DrawerItemClickListener());


        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                //R.mipmap.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                //Acciones que se ejecutan cuando se cierra el drawer
                getSupportActionBar().setTitle(getString(R.string.title_activity_political_program_index));
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }

            public void onDrawerOpened(View drawerView) {
                //Acciones que se ejecutan cuando se despliega el drawer
                if (drawerLayout.isDrawerVisible(Gravity.END)) {
                    getSupportActionBar().setTitle(getString(R.string.titleIndex));
                } else {
                    getSupportActionBar().setTitle(getString(R.string.titleMenu));
                }
                supportInvalidateOptionsMenu();
                drawerToggle.syncState();
            }
        };
        //Seteamos la escucha

        drawerLayout.setDrawerListener(drawerToggle);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // If the nav drawer is open, hide action items related to the content view
        for (int i = 0; i < menu.size(); i++) {

            if (drawerLayout.isDrawerOpen(drawerListLeft) || drawerLayout.isDrawerOpen(drawerListRight)) {
                menu.getItem(i).setVisible(false);
            } else {
                menu.getItem(i).setVisible(true);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_political_program_index, menu);
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

        if (id == R.id.index_action) {
            drawerLayout.openDrawer(Gravity.END);
        }


        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí

            if (drawerLayout.isDrawerVisible(Gravity.END)) {
                drawerLayout.closeDrawer(Gravity.END);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {

        if (position == 0) {  //Parties Menu

            Intent in = new Intent(this, PartiesActivity.class);
            startActivity(in);

        }

        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerListLeft.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerListLeft);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void getProgramSectionsData(int id) {
        URL link = null;
        try {
            link = new URL("http://10.0.2.2/ServiceRest/public/getPoliticalProgram/" + id);
            GetProgramsData task = new GetProgramsData(this, findViewById(R.id.activityPoliticalProgramIndexLayout), id);
            task.execute(link);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    private void getIndexTitles(int political_party_id) {
        // Request to the database the table of contents that matches the id of the political party.
        HashMap<String, List<String>> listDataChild = generateData();
        mListDataHeader = new ArrayList<String>();
        mListDataHeader.add("0. Preámbulo");
        mListDataHeader.add("1. Democracia, participación y transparencia");
        mListDataHeader.add("2. Economía al servicio de la ciudadanía");
        mListDataHeader.add("3. Políticas Sociales");
        mListDataHeader.add("4. Derechos civiles");
        mListDataHeader.add("5. Red, derechos de autor y cultura libre");
        mListDataHeader.add("6. Medio Ambiente");

        mListAdapter = new ExpandableListAdapter(this, mListDataHeader, listDataChild);
        drawerListRight.setAdapter(mListAdapter);
    }


    private HashMap<String, List<String>> generateData() {
        HashMap<String, List<String>> listDataSection = new HashMap<String, List<String>>();

        List<String> listDataChild = new ArrayList<String>();
        listDataChild.add("- Los derechos humanos");
        listDataChild.add("- Los compromisos piratas");
        listDataChild.add("- Una Unión Europea de las personas");
        listDataSection.put("0. Preámbulo", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("1.1 Participación ciudadana y gobierno abierto");
        listDataChild.add("1.2 Calidad legislativa");
        listDataChild.add("1.3 Diversidad");
        listDataChild.add("1.4 Resiliencia");
        listDataSection.put("1. Democracia, participación y transparencia", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("2.1 Derecho al trabajo");
        listDataChild.add("2.2 Derecho a la propiedad privada y colectiva");
        listDataChild.add("2.3 Economía al servicio del bienestar social");
        listDataChild.add("2.4 Igualdad de oportunidades");
        listDataSection.put("2. Economía al servicio de la ciudadanía", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("3.1 Servicios públicos universales");
        listDataChild.add("3.2 Garantía de protección de los consumidores y usuarios");
        listDataChild.add("3.3 Igualdad de oportunidades en la educación");
        listDataChild.add("3.4 Sanidad");
        listDataSection.put("3. Políticas Sociales", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("4.1 Salud sexual y reproductiva");
        listDataChild.add("4.2 Derechos de la infancia");
        listDataChild.add("4.3 Justicia independiente, gratuita e igual para todos");
        listDataChild.add("4.4 Libertad de prensa");
        listDataSection.put("4. Derechos civiles", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("5.1 Derechos de autor");
        listDataChild.add("5.2 Software Libre, Cultura Libre y Conocimiento Libre");
        listDataChild.add("5.3 Open Access y Open Data");
        listDataChild.add("5.4 Patentes");
        listDataSection.put("5. Red, derechos de autor y cultura libre", listDataChild);

        listDataChild = new ArrayList<String>();
        listDataChild.add("6.1. Endurecimiento de la lucha contra la minería no sostenible");
        listDataChild.add("6.2. Contaminación del litoral y de las aguas");
        listDataChild.add("6.3. Lucha activa contra la aceleración del cambio climático");
        listDataChild.add("6.4. El Ártico, Santuario Global");
        listDataSection.put("6. Medio Ambiente", listDataChild);

        return listDataSection;
    }


}