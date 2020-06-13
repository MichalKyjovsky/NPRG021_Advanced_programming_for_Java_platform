package cz.cuni.mff.java.testing.example;

import cz.cuni.mff.java.testing.*;

import java.util.ArrayList;
import java.util.Collection;

@TesterInfo(priority = Priority.LOW, createdBy = "PH", lastModified = "01/01/2018")
public class SimpleTest3 {
    private Collection<String> collection;

    @Before
    public void setUp() {
        collection = new ArrayList<>();
    }

    @After
    public void tearDown() {
        collection.clear();
    }

    @Test(enabled = false)
    public void testEmptyCollection() {
        if (!collection.isEmpty()) throw new AssertionError();
    }

    @Test(enabled = true, expectedExceptions = {IndexOutOfBoundsException.class})
    public void testOneItemCollection() {
        collection.add("itemA");
        if (1 == collection.size()) throw new IndexOutOfBoundsException();
    }
}
