package com.android.luisalonso.rxtest_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mFirstSampleButton = (Button) findViewById(R.id.button_first_sample);
        Button mSecondSampleButton = (Button) findViewById(R.id.button_second_sample);
        Button mThirdSampleButton = (Button) findViewById(R.id.button_third_sample);

        mFirstSampleButton.setOnClickListener(this);
        mSecondSampleButton.setOnClickListener(this);
        mThirdSampleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_first_sample: basicsOfObserversAndObservables();
                break;
            case R.id.button_second_sample: usingBasicOperations();
                break;
            case R.id.button_third_sample: usingMultipleOperations();
                break;
        }
    }

    private void basicsOfObserversAndObservables() {
        /* We can see observables as an objects that emits data */
        Observable<String> basicObservable = Observable.just("Basic data");

        /* We can see observers as an objects that consumes data provided by observables */
        Observer<String> basicObserver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e.getMessage() != null) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };

        /* If you don't want to use all methods, you have another option, call will be execute
        whenever the observable emits data */
        Action1<String> basicAction1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };

        //Subscription basicSubscription = basicObservable.subscribe(basicObserver);
        Subscription basicSubscription = basicObservable.subscribe(basicAction1);
    }

    private void usingBasicOperations() {
        Observable<Integer> arrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});
        arrayObservable = arrayObservable.map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        });

        arrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Toast.makeText(MainActivity.this, integer.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void usingMultipleOperations() {
        Observable<Integer> arrayObservable = Observable.from(new Integer[]{1, 2, 3, 4, 5, 6});

        arrayObservable.filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer % 2 == 0;
            }
        }).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer * integer;
            }
        }).reduce(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        });

        arrayObservable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Toast.makeText(MainActivity.this, integer.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
