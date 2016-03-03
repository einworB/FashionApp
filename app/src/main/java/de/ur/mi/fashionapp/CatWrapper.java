package de.ur.mi.fashionapp;

/**
 * Created by Mario on 01.03.2016.
 */
public class CatWrapper {
    private static final int CATEGORY_TOP = 0;
  // TODO: replace other "magic numbers"
    public String catWrap(int num){
        switch(num){
            case CATEGORY_TOP: return "top";
            case 1: return "bottom";
            case 2: return "shoe";
            case 3: return "accessoire";
        }
        return "Out of Array";
    }
    public String seasonWrap(int num){
        switch(num){
            case 0: return "spring";
            case 1: return "summer";
            case 2: return "fall";
            case 3: return "winter";
        }
        return "Out of Array";
    }
    public String colorWrap(int num){
        switch(num){
            case 0: return "red";
            case 1: return "blue";
            case 2: return "green";
            case 3: return "yellow";
            case 4: return "black";
            case 5: return "white";
            case 6: return "pink";
            case 7: return "purple";
            case 8: return "orange";
            case 9: return "turquoise";
        }
        return "Out of Array";
    }
    public String occasionWrap(int num){
        switch(num){
            case 0: return "evening";
            case 1: return "beach";
            case 2: return "couch";
            case 3: return "occasional";
        }
        return "Out of Array";
    }
}
