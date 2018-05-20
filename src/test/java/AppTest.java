import exception.InvalidLoanRequestedAmountException;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void testCheckAndGetLoanAmountRequestedOK() {
        try {
            App.checkAndGetLoanAmountRequested("1000");
        } catch (RuntimeException rex) {
            Assert.fail("Exception " + rex);
        }
    }

    @Test(expected = InvalidLoanRequestedAmountException.class)
    public void testCheckAndGetLoanAmountRequestedThrowsInvalidLoanRequestedAmountException() {
        App.checkAndGetLoanAmountRequested("100notOk");
    }
}
