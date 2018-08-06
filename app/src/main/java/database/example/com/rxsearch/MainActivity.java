package database.example.com.rxsearch;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import database.example.com.rxsearch.databinding.ActivityMainBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [06-Aug-2018 at 1:13 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: RxSearch.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [06-Aug-2018 at 1:13 PM].
 * * --> <Second Editor> on [06-Aug-2018 at 1:13 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [06-Aug-2018 at 1:13 PM].
 * * --> <Second Reviewer> on [06-Aug-2018 at 1:13 PM].
 * * ============================================================================
 **/

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private List<String> proData = new ArrayList<>();
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
        rxSearchByKey();
    }

    private void initRecyclerView() {
        activityMainBinding.recyclerSearch.setLayoutManager(new LinearLayoutManager(this));
        proData = getAllData();
        mainAdapter = new MainAdapter(proData);
        activityMainBinding.recyclerSearch.setAdapter(mainAdapter);
    }

    private void rxSearchByKey() {

        CompositeDisposable compositeDisposable = new CompositeDisposable();

        Disposable searchDisposable = RxSearch.fromSearchView(activityMainBinding.searchViewContact)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return (s.length() > 1 || s.length() == 0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        searchOperation(s);
                    }
                });

        compositeDisposable.add(searchDisposable);
    }

    private void searchOperation(String searchKey) {
        if (mainAdapter == null)
            return;

        if (TextUtils.isEmpty(searchKey)) {
            updateSearchInfo(proData);
            return;
        }

        List<String> updateList = new ArrayList<>();

        for (String proItem : proData) {
            if (proItem.toLowerCase().contains(searchKey.toLowerCase())) {
                updateList.add(proItem);
            }
        }

        updateSearchInfo(updateList);
    }

    private void updateSearchInfo(List<String> updateList) {
        mainAdapter.clearData();
        mainAdapter.addData(updateList);
    }

    private List<String> getAllData() {
        List<String> proData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            proData.add(getSaltString());
        }
        return proData;
    }

    String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}