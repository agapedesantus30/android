package edu.ggc.lutz.dicenotation.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyContent {

    public static List<DummyItem> items = new ArrayList<DummyItem>();

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
