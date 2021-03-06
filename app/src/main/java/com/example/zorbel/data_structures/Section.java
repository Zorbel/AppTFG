package com.example.zorbel.data_structures;

import com.example.zorbel.apptfg.views.TopItem;

import java.util.List;

public class Section implements TopItem{

    private int mSection;
    private int mPoliticalParty;
    private String mTitle;
    private String mText;
    private String mCategory;

    private int numLikes;
    private int numDislikes;
    private int numNotUnderstoods;
    private int numComments;
    private boolean isFavorite;

    private int numViews;
    private List<Section> lSections;

    public Section(int mSection, int mPoliticalParty, String mTitle, String mText, List<Section> lSections) {
        this.mSection = mSection;
        this.mPoliticalParty = mPoliticalParty;
        this.mTitle = mTitle;
        this.mText = mText;
        this.lSections = lSections;
        this.isFavorite = false;
    }

    public Section(int mSection, int mPoliticalParty, String mTitle, String mCategory, int numLikes, int numDislikes, int numNotUnderstoods, int numComments, int numViews) {
        this.mSection = mSection;
        this.mPoliticalParty = mPoliticalParty;
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.numLikes = numLikes;
        this.numDislikes = numDislikes;
        this.numNotUnderstoods = numNotUnderstoods;
        this.numComments = numComments;
        this.numViews = numViews;
        this.isFavorite = false;
    }

    public int getmSection() {
        return mSection;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmPoliticalParty() {
        return mPoliticalParty;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public List<Section> getlSections() {
        return lSections;
    }

    public void setlSections(List<Section> lSections) {
        this.lSections = lSections;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumDislikes() {
        return numDislikes;
    }

    public void setNumDislikes(int numDislikes) {
        this.numDislikes = numDislikes;
    }

    public int getNumNotUnderstoods() {
        return numNotUnderstoods;
    }

    public void setNumNotUnderstoods(int numNotUnderstoods) {
        this.numNotUnderstoods = numNotUnderstoods;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public int getNumViews() {
        return numViews;
    }

    public void setNumViews(int numViews) {
        this.numViews = numViews;
    }

    public void addSubSection(Section subSec) {
        this.lSections.add(subSec);
    }

    @Override
    public boolean isSection() {
        return true;
    }

    @Override
    public boolean isProposal() {
        return false;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }
}