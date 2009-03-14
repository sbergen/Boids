import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
  vector.VectorTest.class,
  vector.AngleTest.class
})

public class AllTests {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations
}
