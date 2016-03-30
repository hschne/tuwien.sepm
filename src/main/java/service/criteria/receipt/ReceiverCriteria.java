package service.criteria.receipt;

import entities.Article;
import entities.Receipt;
import org.apache.commons.lang3.StringUtils;
import service.ServiceException;
import service.criteria.Criteria;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiverCriteria implements Criteria<Receipt> {

    private String receiver;

    public ReceiverCriteria(String name) {
        this.receiver = name;
    }

    @Override
    public List<Receipt> apply(List<? extends Receipt> list) throws ServiceException {
        return list.stream().filter(p -> StringUtils.containsIgnoreCase(p.getReceiver(),receiver)).collect(Collectors.toList());
    }
}
