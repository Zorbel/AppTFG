package com.example.zorbel.data_structures;

import com.example.zorbel.apptfg.R;
import com.example.zorbel.apptfg.views.TopItem;

public class Proposal implements TopItem {

    private int propId;

    private boolean isEditable;

    private String titleProp;
    private String category; //ID
    private String date;
    private String id_user;
    private String user; //name
    private String resLogo;
    private String textProp;
    private String howProp;
    private String moneyProp;

    private String idWave;

    private int numLikes;
    private int numDislikes;
    private int numNotUnderstoods;
    private int numComments;
    private int numViews;
    private boolean isFavorite;

    public Proposal(int propId, boolean editable, String titleProp, String category, String date, String id_user,
                    String user, String resLogo, String textProp, String howProp, String moneyProp, int numLikes,
                    int numDislikes, int numComments, int numNotUnderstoods, int numViews, String idWave) {
        this.propId = propId;
        this.isEditable = editable;
        this.titleProp = titleProp;
        this.category = category;
        this.date = date;
        this.id_user =id_user;
        this.user = user;
        this.resLogo = resLogo;
        this.textProp = textProp;
        this.howProp = howProp;
        this.moneyProp = moneyProp;
        this.numLikes = numLikes;
        this.numDislikes = numDislikes;
        this.numComments = numComments;
        this.numNotUnderstoods = numNotUnderstoods;
        this.numViews = numViews;
        this.isFavorite = false;
        this.idWave = idWave;
    }

    public static int getImage(String id_image) {
        int resource = -1;

        switch (id_image) {
            case "ic_health_cross":
                resource = R.drawable.ic_health_cross;
                break;
            case "ic_education":
                resource = R.drawable.ic_education;
                break;
            case "ic_employment":
                resource = R.drawable.ic_employment;
                break;
            case "ic_houses":
                resource = R.drawable.ic_houses;
                break;
            case "ic_taxes":
                resource = R.drawable.ic_taxes;
                break;
            case "ic_culture":
                resource = R.drawable.ic_culture;
                break;
            case "ic_others":
                resource = R.drawable.ic_others;
                break;
        }

        return resource;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isProposal() {
        return true;
    }

    public String getIdWave() {
        return idWave;
    }

    public void setIdWave(String idWave) {
        this.idWave = idWave;
    }

    public int getPropId() {
        return propId;
    }

    public void setPropId(int propId) {
        this.propId = propId;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public String getTitleProp() {
        return titleProp;
    }

    public void setTitleProp(String title) {
        this.titleProp = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getResLogo() {
        return resLogo;
    }

    public void setResLogo(String resLogo) {
        this.resLogo = resLogo;
    }

    public String getTextProp() {
        return textProp;
    }

    public void setTextProp(String textProp) {
        this.textProp = textProp;
    }

    public String getHowProp() {
        return howProp;
    }

    public void setHowProp(String howProp) {
        this.howProp = howProp;
    }

    public String getMoneyProp() {
        return moneyProp;
    }

    public void setMoneyProp(String moneyProp) {
        this.moneyProp = moneyProp;
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

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isFavorite() {
        return this.isFavorite;
    }
}
