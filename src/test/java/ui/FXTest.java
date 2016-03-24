package ui;

import base.BaseTest;
import org.junit.Ignore;
import org.junit.Rule;

@Ignore
public abstract  class FXTest extends BaseTest{

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
}
