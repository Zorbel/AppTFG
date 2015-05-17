package com.example.zorbel.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.ListIndexAdapter;
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


public class GetProgramsData extends ConnectionGet {

    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TEXT = "text";

    private int politicalProgramId;
    private int politicalProgramGroupIndex;

    public GetProgramsData(Context mContext, View mRootView, int id, int index) {
        super(mContext, mRootView);
        this.politicalProgramId = id;
        this.politicalProgramGroupIndex = index;
    }

    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getPoliticalProgram(super.getJson(), politicalProgramId);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(super.getmRootView() != null) {
            List<Section> index = PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(politicalProgramGroupIndex).getmSectionRoot().getlSections();

            ListView mIndexListView = (ListView) super.getmRootView().findViewById(R.id.indexListView);
            mIndexListView.setAdapter(new ListIndexAdapter(super.getmContext(), index));
        }

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


                    Section sec = new Section(id_section, id, title, null, null);

                    al.add(sec);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        Section root = new Section(0, id, null, null, null);
        createIndex(root, al, 0);
        PoliticalGroups.getInstance().getMlistOfPoliticalParties().get(politicalProgramGroupIndex).setmSectionRoot(root);

    }

    protected int createIndex(Section parent, List<Section> JSONResult, int index) {
        // X = currentSection
        Section currentSection = JSONResult.get(index);

        if (parent.getlSections() == null) {
            parent.setlSections(new ArrayList<Section>());
        }
        parent.addSubSection(currentSection);

        // c = nextIndex
        int nextIndex = index + 1;

        while ((nextIndex < JSONResult.size()) && (getLevel(JSONResult.get(nextIndex)) >= getLevel(currentSection))) {

            if (getLevel(JSONResult.get(nextIndex)) == getLevel(currentSection)) {
                currentSection = JSONResult.get(nextIndex);
                nextIndex++;
                parent.addSubSection(currentSection);

            } else if (getLevel(JSONResult.get(nextIndex)) > getLevel(currentSection))
                nextIndex = createIndex(currentSection, JSONResult, nextIndex);
        }

        return nextIndex;
    }

    protected int getLevel(Section sec) {

        int id_sec = sec.getmSection(), level;

        if (id_sec % 100 != 0) {
            level = 4;

        } else if (id_sec % 10000 != 0) {
            level = 3;

        } else if (id_sec % 1000000 != 0) {
            level = 2;

        } else
            level = 1;

        return level;
    }

}
