package com.sxdtdx.aitou.model.bean;

/**
 * Created by xinyuan on 17-5-14.
 */

public class Option {
    private String name;
    private boolean isSelected;
    private int voteds;

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

    public int getVoteds() {
        return voteds;
    }

    public void setVoteds(int voteds) {
        this.voteds = voteds;
    }
}
