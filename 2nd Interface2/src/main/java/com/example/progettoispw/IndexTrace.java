package com.example.progettoispw;



public class IndexTrace {
    private static int i=0;
    private static int j=0;
    private static int k=0;
    private static int l=0;
    private static int m[]= new int[2];
    private static int n=0;
    private static int five;

    public static void add() {
        i += 1;
    }

    public static int get(){
        return i;
    }

    public static void reset() { i=0; }

    public static void set(int index){ i=index; }

    public static int tempget(){
        return j;
    }

    public static void tempset(int index){ j=index; }

    public static int raget(){ return k; }

    public static void raset(int index){ k=index; }

    public static int preget(){ return l; }

    public static void preset(int index){ l=index; }

    public static int getFive(){ return five; }

    public static void setFive(int b){ five=b; }

    public static int dayget(){ return m[0]; }

    public static void dayset(int index){ m[0]=index; }

    public static int timeget(){ return m[1]; }

    public static void timeset(int index){ m[1]=index; }

    public static void timereset(){ m[1]=0; }

    public static int chefget(){ return n; }

    public static void chefset(int index){ n=index; }
}
