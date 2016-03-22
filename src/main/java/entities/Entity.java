package entities;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Entity {

    int getId() throws NotImplementedException;

    void setId(int id);
}
