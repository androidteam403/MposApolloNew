package com.apollo.pharmacy.data.db.realm;

import io.realm.Realm;

public class RealmService {

    private final Realm mRealm;

    public RealmService(final Realm realm) {
        mRealm = realm;
    }

    public void closeRealm() {
        mRealm.close();
    }

    public interface OnTransactionCallback {
        void onRealmSuccess();

        void onRealmError(final Exception e);
    }
}
