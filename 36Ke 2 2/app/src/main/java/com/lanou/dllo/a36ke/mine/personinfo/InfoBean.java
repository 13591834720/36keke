package com.lanou.dllo.a36ke.mine.personinfo;

import android.widget.EditText;

import cn.bmob.v3.BmobObject;

/**
 * Created by dllo on 16/7/5.
 */
public class InfoBean extends BmobObject{
    private String companyName;
    private String companyBrief;
    private String companyLogo;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBrief() {
        return companyBrief;
    }

    public void setCompanyBrief(String companyBrief) {
        this.companyBrief = companyBrief;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }
}
