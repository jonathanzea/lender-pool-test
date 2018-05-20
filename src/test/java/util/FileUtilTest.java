package util;

import exception.InvalidCSVFileFormatException;
import model.Lender;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileUtilTest {

    @Test
    public void testGetLendersFromCSVFile(){
        URI uri = null;
        try {
            uri = ClassLoader.getSystemResource("lenders-pool-ok.csv").toURI();
            String mainPath = Paths.get(uri).toString();
            Path path = Paths.get(mainPath);

            List<Lender> lenderPool = FileUtils.getLendersFromCSVFile(path.toString());
            assertThat(lenderPool.size() > 0);
        } catch (URISyntaxException e) {
            Assert.fail("Exception " + e);
        }

    }

    @Test(expected = InvalidCSVFileFormatException.class)
    public void testGetLendersFromCSVFileThrowsInvalidLoanRequestedAmountException() throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource("lenders-pool-notok.csv").toURI();
        String mainPath = Paths.get(uri).toString();
        Path path = Paths.get(mainPath);

        List<Lender> lenderPool = FileUtils.getLendersFromCSVFile(path.toString());
    }
}
