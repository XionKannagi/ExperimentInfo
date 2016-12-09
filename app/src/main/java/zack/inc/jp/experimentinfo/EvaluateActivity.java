package zack.inc.jp.experimentinfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EvaluateActivity extends Activity {

    DataLogger dataLogger;
    String timeStamp, driverName, evaluatorName;
    //RadioGroup radioGroup1, radioGroup2, radioGroup3, radioGroup4;
    java.text.DateFormat df;
    RadioGroup radioGroups[] = new RadioGroup[4];
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

//        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
//        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
//        radioGroup3 = (RadioGroup) findViewById(R.id.radioGroup3);
//        radioGroup4 = (RadioGroup) findViewById(R.id.radioGroup4);


//        radioGroups[0] = (RadioGroup) findViewById(R.id.radioGroup1);
//        radioGroups[1] = (RadioGroup) findViewById(R.id.radioGroup2);
//        radioGroups[2] = (RadioGroup) findViewById(R.id.radioGroup3);
//        radioGroups[4] = (RadioGroup) findViewById(R.id.radioGroup4);


        for (int i = 0; i < radioGroupID.length; i++) {
            radioGroups[i] = (RadioGroup) findViewById(radioGroupID[i]);
        }

        df = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss.SSS", Locale.JAPAN);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));


    }

    public void saveEval(View v) {

        String itemValue[] = new String[4];
        int invalidCount = 0;
        int checkedIds[] = new int[4];
        for (int i = 0; i < itemValue.length; i++) {
            itemValue[i] = "0";
        }


        for (int i = 0; i < radioGroups.length; i++) {
            checkedIds[i] = radioGroups[i].getCheckedRadioButtonId();
            Log.d("checkedId :", ""+ checkedIds[i]);

            if (checkedIds[i] != -1) {
                // 選択されているラジオボタンの取得
                RadioButton radioButton = (RadioButton) findViewById(checkedIds[i]);// (Fragmentの場合は「getActivity().findViewById」にする)

                // ラジオボタンのテキストを取得
                itemValue[i] = radioButton.getText().toString();

                Log.d("itemValue:",itemValue[i]);

            } else {
                invalidCount++;
                Log.d("invalid count",":"+invalidCount);
            }

        }

        Log.d("invalid count",":"+invalidCount);
        if (invalidCount > 0) {
            Toast.makeText(this, "全ての項目を入力して下さい", Toast.LENGTH_LONG).show();
        } else {

            dataLogger.saveValue(df.format(new Date()), itemValue[0], itemValue[1], itemValue[2], itemValue[3]);
            clearChecked();

        }

    }

    public void clearChecked() {
        for (int i = 0; i < radioGroups.length; i++) {
            radioGroups[i].clearCheck();
        }
    }

}


