package com.apollopharmacy.mpospharmacist.data.db.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

//        if (oldVersion == 1) {
//            schema.create("Cart")
//                    .addField("sessionId", String.class)
//                    .addField("sku", String.class)
//                    .addField("quantity", int.class)
//                    .addField("dateTime", String.class);
//            oldVersion++;
//        }
    }
}
