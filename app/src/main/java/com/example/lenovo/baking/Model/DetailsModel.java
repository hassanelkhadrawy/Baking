package com.example.lenovo.baking.Model;

public class DetailsModel
{
  public String shortDescription;
  public String description;
  public String videoURL;
    public String id;
    public String name;


    public DetailsModel(String shortDescription, String description, String videoURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public DetailsModel(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
