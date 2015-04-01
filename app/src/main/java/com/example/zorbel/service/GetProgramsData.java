package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.data_structures.PoliticalGroups;
import com.example.zorbel.data_structures.Section;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaime on 30/03/15.
 */
public class GetProgramsData extends AsyncTask<URL, Void, Void> {

    private HttpURLConnection con;
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TEXT = "text";

    private Context mContext;
    private View mRootView;
    private int politicalProgramId;

    private ProgressDialog pDialog;

    public GetProgramsData(Context con, View rootView, int id) {
        this.mContext = con;
        this.mRootView = rootView;
        this.politicalProgramId = id;

    }


    @Override
    protected Void doInBackground(URL... urls) {
        StringBuilder builder = new StringBuilder();

        try {

            // Establecer la conexión
            con = (HttpURLConnection) urls[0].openConnection();

            // Obtener el estado del recurso
            int statusCode = con.getResponseCode();

            if (statusCode == 200) {
                InputStream in = new BufferedInputStream(con.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(GetPoliticalParties.class.toString(), "Failed to get JSON object");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("JSON", "     :      " + builder.toString() + "  ");
        getPoliticalProgram(builder.toString(), politicalProgramId);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.text_dialog_downloading));
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // TODO: create the index view
        pDialog.dismiss();
    }

    private void getPoliticalProgram(String jsonStr, int id) {

        List<Section> al = new ArrayList<Section>();

        if (jsonStr != null) {
            try {

                JSONArray sections = new JSONArray(jsonStr);


                for (int i = 0; i < sections.length(); i++) {
                    JSONObject s = sections.getJSONObject(i);

                    String title = s.getString(TAG_SECTION_TITLE);
                    int id_section = s.getInt(TAG_SECTION_ID);

                    // TODO fill in the section with the proper attributes (id_section)
                    Section sec = new Section(id_section, id, title, null, null);

                    al.add(sec);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Section root = new Section(0, id, null, null, null);
        createIndex(root, al, 0);
        PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(id).setmSectionRoot(root);

    }

    protected int createIndex(Section parent, List<Section> JSONResult, int index) {

        // X = currentSection
        Section currentSection = JSONResult.get(index);

        if (parent.getlSections() == null) {
            parent.setlSections(new ArrayList<Section>());
        }
        parent.addSubSection(currentSection);
        Log.d("Index", currentSection.getmSection() + "  :  " + currentSection.getmTitle());
        //P.hijo(X) ??

        // c = nextIndex
        int nextIndex = index + 1;

        while ((nextIndex < JSONResult.size()) && (getLevel(JSONResult.get(nextIndex)) >= getLevel(currentSection))) {

            if (getLevel(JSONResult.get(nextIndex)) == getLevel(currentSection)) {

                currentSection = JSONResult.get(nextIndex);
                nextIndex++;
                parent.addSubSection(currentSection);
                Log.d("Index", currentSection.getmSection() + "  :  " + currentSection.getmTitle());
            } else if (getLevel(JSONResult.get(nextIndex)) > getLevel(currentSection)) {

                nextIndex = createIndex(currentSection, JSONResult, nextIndex);

            }
        }

        return nextIndex;

    }

    protected int getLevel(Section sec) {

        int id_sec = sec.getmSection();
        int level = 0;

        if (id_sec % 100 != 0) {

            level = 4;
            return level;

        } else if (id_sec % 10000 != 0) {

            level = 3;
            return level;

        } else if (id_sec % 1000000 != 0) {

            level = 2;
            return level;

        } else {

            level = 1;
            return level;
        }
    }
}