package edu.ggc.lutz.dicenotation.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> items = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> itemMap = new HashMap<String, DummyItem>();

    static {
        Log.v("dungeonX", "adding .....");
        addItem(new DummyItem("d10","","d10"));
        addItem(new DummyItem("2d10x2-13","","2d10x2-13"));
        addItem(new DummyItem("2d6","","2d6"));
        addItem(new DummyItem("3d6","","3d6"));
        addItem(new DummyItem("d6+12","","d6+12"));
        addItem(new DummyItem("d10x10","","d10x10"));
        addItem(new DummyItem("d3","","d3"));
    }

    public static void addItem(DummyItem item) {
        items.add(item);
        itemMap.put(item.id, item);
    }

    public static void removeItem(DummyItem itemId) {
        items.remove(itemId);
        itemMap.remove(itemId);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }

    }
}
