package fr.ut1.m2ipm.dafumarket.dto;

import java.util.ArrayList;
import java.util.List;

public class MessagePanierDTO {

    private String message;

    private List<StockPanierMagasinDTO> stocksMagasins;


    public MessagePanierDTO(String message) {
        this.message = message;
        this.stocksMagasins = new ArrayList<StockPanierMagasinDTO>();
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void addMagasin(StockPanierMagasinDTO magasin) {
        this.stocksMagasins.add(magasin);
    }

    public  List<StockPanierMagasinDTO> getStocksMagasins(){
        return stocksMagasins;
    }



}



