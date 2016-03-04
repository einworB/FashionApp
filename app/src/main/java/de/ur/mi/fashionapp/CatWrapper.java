package de.ur.mi.fashionapp;

/**
 * Created by Mario on 01.03.2016.
 */
public class CatWrapper {
    public static final int CATEGORY_TOP = 0;
    public static final int CATEGORY_BOTTOM = 1;
    public static final int CATEGORY_SHOE = 2;
    public static final int CATEGORY_ACCESSOIRE = 3;
    public static final int SEASON_SPRING = 0;
    public static final int SEASON_SUMMER = 1;
    public static final int SEASON_FALL = 2;
    public static final int SEASON_WINTER = 3;
    public static final int COLOR_RED = 0;
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_YELLOW = 3;
    public static final int COLOR_BLACK = 4;
    public static final int COLOR_WHITE = 5;
    public static final int COLOR_PINK = 6;
    public static final int COLOR_PURPLE = 7;
    public static final int COLOR_ORANGE = 8;
    public static final int COLOR_TURQUOISE = 9;
    public static final int OCCASION_EVENING = 0;
    public static final int OCCASION_BEACH = 1;
    public static final int OCCASION_COUCH = 2;
    public static final int OCCASION_OCCASIONAL = 3;



    public String catWrap(int num){
        switch(num){
            case CATEGORY_TOP: return "top";
            case CATEGORY_BOTTOM: return "bottom";
            case CATEGORY_SHOE: return "shoe";
            case CATEGORY_ACCESSOIRE: return "accessoire";
        }
        return "Out of Array";
    }
    public String seasonWrap(int num){
        switch(num){
            case SEASON_SPRING: return "spring";
            case SEASON_SUMMER: return "summer";
            case SEASON_FALL: return "fall";
            case SEASON_WINTER: return "winter";
        }
        return "Out of Array";
    }
    public String colorWrap(int num){
        switch(num){
            case COLOR_RED: return "red";
            case COLOR_BLUE: return "blue";
            case COLOR_GREEN: return "green";
            case COLOR_YELLOW: return "yellow";
            case COLOR_BLACK: return "black";
            case COLOR_WHITE: return "white";
            case COLOR_PINK: return "pink";
            case COLOR_PURPLE: return "purple";
            case COLOR_ORANGE: return "orange";
            case COLOR_TURQUOISE: return "turquoise";
        }
        return "Out of Array";
    }
    public String occasionWrap(int num){
        switch(num){
            case OCCASION_EVENING: return "evening";
            case OCCASION_BEACH: return "beach";
            case OCCASION_COUCH: return "couch";
            case OCCASION_OCCASIONAL: return "occasional";
        }
        return "Out of Array";
    }
}
