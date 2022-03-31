package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model;

public class FiltersModel {

    private  boolean Stockstatus;
    private  boolean Prioritystatus;
    private  boolean Pickingliststatus;
    private  boolean Packingliststatus;

    public  boolean getStockstatus()
    {
        return  Stockstatus;
    }

    public  boolean getPrioritystatus()
    {
        return  Prioritystatus;
    }
    public  boolean getPickingliststatus()
    {
        return  Pickingliststatus;

    }
    public  boolean  getPackingliststatus()
    {
        return Packingliststatus;
    }

    public  void  setStockstatus(boolean Stockstatus)
    {
        this.Stockstatus=Stockstatus;
    }
    public  void  setPrioritystatus(boolean Prioritystatus)
    {
        this.Prioritystatus=Prioritystatus;
    }
    public  void  setPickingliststatus(boolean Pickingliststatus)
    {
        this.Pickingliststatus=Pickingliststatus;
    }

    public  void  setPackingliststatus(boolean Packingliststatus)
    {
        this.Packingliststatus=Packingliststatus;
    }


}
