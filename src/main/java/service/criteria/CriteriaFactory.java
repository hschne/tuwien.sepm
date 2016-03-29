package service.criteria;

public class CriteriaFactory {

    private Criteria resultCriteria;

    public void append(Criteria criteria){
        if(resultCriteria == null){
            resultCriteria = criteria;
        }
        else{
            resultCriteria = new AndCriteria<>(resultCriteria,criteria);
        }
    }

    public Criteria get(){
        return resultCriteria;
    }
}
