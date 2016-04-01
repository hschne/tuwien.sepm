package ui.model;

import entities.Receipt;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ReceiptRepository;
import service.ServiceException;
import service.criteria.Criteria;

/**
 * Wrapper around the observable list containing all receipts. Responsible for keeping in sync with service repository.
 */
public class ReceiptList {

    private final Logger logger = LogManager.getLogger(ArticleList.class);
    private ReceiptRepository receiptRepository;
    private final ListChangeListener<ReceiptModel> addListener = c -> {
        while (c.next()) {
            addReceipts(c);
        }
    };
    private ObservableList<ReceiptModel> receipts;

    public ReceiptList(ReceiptRepository receiptRepository) throws ServiceException {
        this.receiptRepository = receiptRepository;
        initializeList();
    }

    public ObservableList<ReceiptModel> get() {
        return receipts;
    }

    public void applyFilter(Criteria<Receipt> criteria) throws ServiceException {
        receipts.clear();
        addReceipts(criteria);
    }

    private void addReceipts(Criteria<Receipt> criteria) throws ServiceException {
        receipts.removeListener(addListener);
        ModelFactory factory = new ModelFactory();
        receipts.addAll(factory.createReceiptModels(receiptRepository.filter(criteria)));
        receipts.addListener(addListener);
    }

    private void initializeList() throws ServiceException {
        receipts = FXCollections.observableArrayList();
        ModelFactory factory = new ModelFactory();
        receipts.addAll(factory.createReceiptModels(receiptRepository.getAll()));
        receipts.addListener(addListener);
    }

    private void addReceipts(ListChangeListener.Change<? extends ReceiptModel> c) {
        for (ReceiptModel additem : c.getAddedSubList()) {
            try {
                receiptRepository.create(additem);
            } catch (ServiceException e) {
                logger.error("Failed to create article");
            }
        }
    }


}
