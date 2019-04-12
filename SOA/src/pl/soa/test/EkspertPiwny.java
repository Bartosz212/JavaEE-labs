package pl.soa.test;

public class EkspertPiwny {

    public static String wyborPiwa(String kolor){
        switch (kolor.toLowerCase()){
            case "jasne":
                return "Żywiec";
            case "słomkowe":
                return "Tyskie";
            case "ciemne":
                return "Książęce";
            default:
                return "Harnaś";
        }
    }

}
