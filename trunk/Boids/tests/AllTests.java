import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
  engine.VectorTest.class,
  engine.AngleTest.class
})

public class AllTests {
    // the class remains completely empty, 
    // being used only as a holder for the above annotations
}
