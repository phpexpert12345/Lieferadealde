package com.app.liferdeal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LanguageModel {

    @SerializedName("LanguageListList")
    @Expose
    private List<LanguageListList> languageListList = null;

    public List<LanguageListList> getLanguageListList() {
        return languageListList;
    }

    public void setLanguageListList(List<LanguageListList> languageListList) {
        this.languageListList = languageListList;
    }
    public class LanguageListList {

        @SerializedName("lang_code")
        @Expose
        private String langCode;
        @SerializedName("lang_name")
        @Expose
        private String langName;
        @SerializedName("lang_icon")
        @Expose
        private String langIcon;
        @SerializedName("error")
        @Expose
        private String error;

        public String getLangCode() {
            return langCode;
        }

        public void setLangCode(String langCode) {
            this.langCode = langCode;
        }

        public String getLangName() {
            return langName;
        }

        public void setLangName(String langName) {
            this.langName = langName;
        }

        public String getLangIcon() {
            return langIcon;
        }

        public void setLangIcon(String langIcon) {
            this.langIcon = langIcon;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}
