package dao;

public abstract class ADao implements IDao {

    private Database database;

    public ADao(Database database){
        this.database = database;
    }

    public void Create(){}
}
