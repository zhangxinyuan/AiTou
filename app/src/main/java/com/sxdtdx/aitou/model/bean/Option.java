package com.sxdtdx.aitou.model.bean;

/**
 * Created by xinyuan on 17-5-14.
 */

public class Option {
    private String name;
    private boolean isSelected;
    private int votedCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getVotedCount() {
        return votedCount;
    }

    public void setVotedCount(int count) {
        this.votedCount = count;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                ", votedCount=" + votedCount +
                '}';
    }
}
