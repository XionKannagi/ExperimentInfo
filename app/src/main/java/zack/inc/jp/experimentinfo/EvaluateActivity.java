package zack.inc.jp.experimentinfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EvaluateActivity extends AppCompatActivity {

    java.text.DateFormat df;

    private static final int EVALUATE_ITEMS = 4; //評価項目の個数
    DataLogger dataLogger;
    String timeStamp, driverName, evaluatorName;
    RadioGroup radioGroups[] = new RadioGroup[EVALUATE_ITEMS];
    int radioGroupID[] = {
            R.id.radioGroup1,
            R.id.radioGroup2,
            R.id.radioGroup3,
            R.id.radioGroup4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        Intent intent = getIntent();
        timeStamp = intent.getStringExtra("TIME_STAMP");
        driverName = intent.getStringExtra("DRIVER_NAME");
        evaluatorName = intent.getStringExtra("EVALUATOR_NAME");

        dataLogger = new DataLogger(getApplicationContext(), timeStamp, driverName, evaluatorName);

        //一気に関連付け
        for (int i = 0; i < radioGroupID.length; i++) {
            radioGroups[i] = (RadioGroup) findViewById(radioGroupID[i]);
        }

        //データの記録用のフォーマット
        df = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss.SSS", Locale.JAPAN);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));


    }

    public void saveEval(View v) {

        String itemValue[] = new String[EVALUATE_ITEMS];
        int invalidCount = 0;
        int checkedIds[] = new int[EVALUATE_ITEMS];

        //itemValueの初期化
        for (int i = 0; i < EVALUATE_ITEMS; i++) {
            itemValue[i] = "0";
        }

        //チェック項目を一気に取得
        for (int i = 0; i < EVALUATE_ITEMS; i++) {

            checkedIds[i] = radioGroups[i].getCheckedRadioButtonId();

            if (checkedIds[i] != -1) {
                // 選択されているラジオボタンの取得
                RadioButton radioButton = (RadioButton) findViewById(checkedIds[i]);// (Fragmentの場合は「getActivity().findViewById」にする)

                // ラジオボタンのテキストを取得
                itemValue[i] = radioButton.getText().toString();

            } else {
                invalidCount++; //チェックされていない項目の個数
                Log.d("invalid count",":"+invalidCount);
            }

        }

        Log.d("invalid count",":"+invalidCount);
        if (invalidCount > 0) {
            Toast.makeText(this, "全ての項目を入力して下さい", Toast.LENGTH_LONG).show();
        } else {
            dataLogger.saveValue(df.format(new Date()), itemValue[0], itemValue[1], itemValue[2], itemValue[3]);
            //入力決定後チェックを全て外す
            clearChecked();

        }

    }

    public void clearChecked() {
        for (int i = 0; i < EVALUATE_ITEMS; i++) {
            radioGroups[i].clearCheck();
        }
    }

}


