package cz.cuni.mff.java.testing.example;

import cz.cuni.mff.java.testing.*;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleTest4 {
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

    @Test(enabled = false)
    public void testOneItemCollection() {
        collection.add("itemA");
        if (1 != collection.size()) throw new AssertionError();
    }
}
