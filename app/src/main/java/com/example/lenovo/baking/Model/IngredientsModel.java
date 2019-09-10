package com.example.lenovo.baking.Model;

public class IngredientsModel {

    public String quantity;
    public String measure;
    public String ingredient;
    public static int id;
    public static int ingredient1;
    public static String ingredient2;


    public IngredientsModel() {
    }

    public static String getIngredient2() {
        return ingredient2;
    }

    public static void setIngredient2(String ingredient2) {
        IngredientsModel.ingredient2 = ingredient2;
    }

    public static int getIngredient1() {
        return ingredient1;
    }

    public static void setIngredient1(int ingredient1) {
        IngredientsModel.ingredient1 = ingredient1;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        IngredientsModel.id = id;
    }

    public IngredientsModel(String quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
