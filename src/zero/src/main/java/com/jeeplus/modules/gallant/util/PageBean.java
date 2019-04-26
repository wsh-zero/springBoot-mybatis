package com.jeeplus.modules.gallant.util;

public class PageBean {

    private int currpage;

    private int sizepage=30;

    private int countpage;


    public int getSizepage() {
        return sizepage;
    }

    public void setSizepage(int sizepage) {
        this.sizepage = sizepage;
    }

    public int getCountpage() {
        return countpage;
    }

    public void setCountpage(int count) {
        if(count%this.sizepage==0){
            this.countpage=count/this.sizepage;
        }else{
            this.countpage=count/this.sizepage+1;
        }
     //   this.countpage = countpage;
    }

    public int getCurrpage() {
        return currpage;
    }

    public void setCurrpage(int currpage) {
        this.currpage = currpage;
    }
}
