package cz.cuni.mff.java.testing.example;

import cz.cuni.mff.java.testing.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@TesterInfo(priority = Priority.HIGH, createdBy = "PH", lastModified = "01/01/2020")
public class SimpleTest {
    private Collection<String> collection;

    @Before
    public void setUp() {
        collection = new ArrayList<>();
    }

    @After
    public void tearDown() {
        collection.clear();
    }

    @Test()
    public void testEmptyCollection() {
        if (!collection.isEmpty()) throw new AssertionError();
    }

    @Test()
    public void testOneItemCollection() {
        collection.add("itemA");
        if (1 != collection.size()) throw new AssertionError();
    }

    @Test()
    public void testFail() {
        throw new RuntimeException();
    }

    @Test(expectedExceptions = {Exception.class})
    public void testException() {
        throw new RuntimeException();
    }
}
