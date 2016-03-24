package ui.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ReceiptRepository;
import service.ServiceException;

public class ReceiptList {

    private final Logger logger = LogManager.getLogger(ArticleList.class);
    private ReceiptRepository receiptRepository;
    private ObservableList<ReceiptModel> receipts;

    public ReceiptList(ReceiptRepository receiptRepository) throws ServiceException {
        this.receiptRepository = receiptRepository;
        initializeList();
    }

    public ObservableList<ReceiptModel> get() {
        return receipts;
    }

    private void initializeList() throws ServiceException {
        receipts = FXCollections.observableArrayList();
        ModelFactory factory = new ModelFactory();
        receipts.addAll(factory.createReceiptModels(receiptRepository.getAll()));
        receipts.addListener((ListChangeListener<ReceiptModel>) c -> {
                    while (c.next()) {
                        addItems(c);
                    }
                }
        );
    }

    private void addItems(ListChangeListener.Change<? extends ReceiptModel> c) {
        for (ReceiptModel additem : c.getAddedSubList()) {
            try {
                receiptRepository.create(additem);
            } catch (ServiceException e1) {
                logger.error("Failed to create article");
            }
        }
    }


}
