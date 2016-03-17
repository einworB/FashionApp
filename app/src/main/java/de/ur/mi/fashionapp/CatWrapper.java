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
    public static final int OCCASION_SPORT = 3;



    public int catWrap(int num){
        switch(num){
            case CATEGORY_TOP: return R.drawable.ic_icon_top;
            case CATEGORY_BOTTOM: return R.drawable.ic_icon_bottom;
            case CATEGORY_SHOE: return R.drawable.ic_icon_shoe;
            case CATEGORY_ACCESSOIRE: return R.drawable.ic_icon_accessoires;
        }
        return -1;
    }
    public int seasonWrap(int num){
        switch(num){
            case SEASON_SPRING: return R.drawable.ic_icon_spring;
            case SEASON_SUMMER: return R.drawable.ic_icon_summer;
            case SEASON_FALL: return R.drawable.ic_icon_autumn;
            case SEASON_WINTER: return R.drawable.ic_icon_winter;
        }
        return -1;
    }
    public int colorWrap(int num){
        switch(num){
            case COLOR_RED: return R.color.md_red_600;
            case COLOR_BLUE: return R.color.md_blue_600;
            case COLOR_GREEN: return  R.color.md_green_600;
            case COLOR_YELLOW: return R.color.md_yellow_600;
            case COLOR_BLACK: return R.color.md_black;
            case COLOR_WHITE: return R.color.md_white;
            case COLOR_PINK: return R.color.md_pink_600;
            case COLOR_PURPLE: return R.color.md_purple_600;
            case COLOR_ORANGE: return R.color.md_orange_600;
            case COLOR_TURQUOISE: return R.color.md_green_200;
        }
        return -1;
    }
    public int occasionWrap(int num){
        switch(num){
            case OCCASION_EVENING: return R.drawable.ic_icon_evening;
            case OCCASION_BEACH: return R.drawable.ic_icon_beach;
            case OCCASION_COUCH: return R.drawable.ic_icon_couch;
            case OCCASION_SPORT: return R.drawable.ic_icon_sport;
        }
        return -1;
    }
}
