package edu.ggc.lutz.listpersistsolution;

import android.provider.BaseColumns;

public final class NotationPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private NotationPersistenceContract() {
    }

    /* Inner class that defines the table contents */
    public static class NotationEntry implements BaseColumns {
        public static final String TABLE_NAME = "notations";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DETAILS = "details";
    }
}
