package main.model;

import main.utils.iocontrol.Mappable;

public interface Model extends Mappable{
    //returns the ID of the model as a string
    String getID();
}
