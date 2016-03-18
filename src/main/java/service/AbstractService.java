package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for service implementations. Provides logger.
 */
public class AbstractService {

    protected final Logger logger = LogManager.getLogger(AbstractService.class);
}
