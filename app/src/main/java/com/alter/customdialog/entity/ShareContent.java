package com.alter.customdialog.entity;

import java.io.Serializable;

/**
 * @CreateDate: 2018/1/25
 * @Author: lzsheng
 * @Description:
 * @Version:
 */
public class ShareContent implements Serializable {

    private String desc;
    private String hrefUrl; // 网页连接地址;
    private String iconUrl;

    public ShareContent(String desc, String hrefUrl, String iconUrl) {
        this.desc = desc;
        this.hrefUrl = hrefUrl;
        this.iconUrl = iconUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHrefUrl() {
        return hrefUrl;
    }

    public void setHrefUrl(String hrefUrl) {
        this.hrefUrl = hrefUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "ShareContent{" +
                "desc='" + desc + '\'' +
                ", hrefUrl='" + hrefUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
