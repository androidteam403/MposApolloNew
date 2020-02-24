package com.apollopharmacy.mpospharmacist.data.db.realm;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import com.apollopharmacy.mpospharmacist.data.db.model.Cart;
import com.apollopharmacy.mpospharmacist.root.ApolloMposApp;

import io.realm.Realm;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = ApolloMposApp.getRealm();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //query a single item with the given id
    public Cart getCartTransaction(String id) {

        return realm.where(Cart.class).equalTo("transactionId", id).findFirst();
    }
}
