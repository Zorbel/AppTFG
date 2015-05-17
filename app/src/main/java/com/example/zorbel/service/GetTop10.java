package com.example.zorbel.service;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.TopItem;
import com.example.zorbel.apptfg.TopItemAdapter;
import com.example.zorbel.data_structures.Section;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class GetTop10 extends ConnectionGet {

    private static final String TAG_ID_POLITICAL_PARTY = "id_political_party";
    private static final String TAG_SECTION_ID = "section";
    private static final String TAG_SECTION_TITLE = "title";
    private static final String TAG_LIKES = "likes";
    private static final String TAG_NOT_UNDERSTOOD = "not_understood";
    private static final String TAG_DISLIKES = "dislikes";
    private static final String TAG_VIEWS = "views";
    private static final String TAG_COMMENTS = "comments";

    private ArrayList<TopItem> listTop10;

    public GetTop10(Context mContext, View mRootView) {
        super(mContext, mRootView);
    }


    @Override
    protected Void doInBackground(URL... urls) {

        super.doInBackground(urls);

        getTop10(super.getJson());

        return null;
    }

    private void getTop10(String JSONString) {
        if (JSONString != null) {
            try {

                JSONArray ar = new JSONArray(JSONString);

                listTop10 = new ArrayList<TopItem>();

                for (int i = 0; i < ar.length(); i++) {

                    JSONObject ob = ar.getJSONObject(i);

                    int idPol = ob.getInt(TAG_ID_POLITICAL_PARTY);
                    int idSec = ob.getInt(TAG_SECTION_ID);
                    String title = ob.getString(TAG_SECTION_TITLE);

                    int numLikes = ob.getInt(TAG_LIKES);
                    int numNotUnd = ob.getInt(TAG_NOT_UNDERSTOOD);
                    int numDislikes = ob.getInt(TAG_DISLIKES);
                    int numViews = ob.getInt(TAG_VIEWS);
                    int numComments = ob.getInt(TAG_COMMENTS);

                    Section sec = new Section(idSec, idPol, title, numLikes, numDislikes, numNotUnd, numComments, numViews);

                    listTop10.add(sec);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This method shows the Top10 list on the Top10Activity.
     */

    private void showTop() {

        ListView top10ListView = (ListView) super.getmRootView().findViewById(R.id.top10ListView);
        top10ListView.setAdapter(new TopItemAdapter(super.getmContext(), listTop10));

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        showTop();
    }
}