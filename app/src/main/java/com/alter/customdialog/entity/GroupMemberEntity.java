package com.alter.customdialog.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @CreateDate: 2018/1/25
 * @Author: lzsheng
 * @Description:
 * @Version:
 */
public class GroupMemberEntity implements Serializable {


    private int memeberType;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private List<String> imageUrls;

    public GroupMemberEntity(int memeberType, String name, List<String> imageUrls) {
        this.memeberType = memeberType;
        this.name = name;
        this.imageUrls = imageUrls;
    }

    public int getMemeberType() {
        return memeberType;
    }

    public void setMemeberType(int memeberType) {
        this.memeberType = memeberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "GroupMemberEntity{" +
                "memeberType=" + memeberType +
                ", name='" + name + '\'' +
                ", imageUrls=" + imageUrls +
                '}';
    }

}
