package zack.inc.jp.experimentinfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EvaluetorInfoActivity extends Activity {

    EditText eName, eOld, eAcquisition, eLastDay;
    private String evaluatorName, evaluatorOld, evaluatorAcquisition, evaluatorLastDay, evaluatorSexText;
    private String driverName, driverOld, driverAcquisition, driverLastDay, driverSexText;
    RadioGroup radioGroup;

    java.text.DateFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluetor_info);
        Intent intent = getIntent();

        driverName = intent.getStringExtra("DRIVER_NAME");
        driverOld = intent.getStringExtra("DRIVER_OLD");
        driverAcquisition = intent.getStringExtra("DRIVER_ACQUISITION");
        driverLastDay = intent.getStringExtra("DRIVER_LAST_DAY");
        driverSexText = intent.getStringExtra("DRIVER_SEX");


        eName = (EditText) findViewById(R.id.EvaluatorNameText);
        eOld = (EditText) findViewById(R.id.eOldText);
        eAcquisition = (EditText) findViewById(R.id.eAcquisitionDateText);
        eLastDay = (EditText) findViewById(R.id.eLastDayText);

        radioGroup = (RadioGroup) findViewById(R.id.eSexRadioGroup);

        df = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss.SSS", Locale.JAPAN);
        df.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));


    }

    public void nextButton(View v) {


        evaluatorName = eName.getText().toString();
        evaluatorOld = eOld.getText().toString();
        evaluatorAcquisition = eAcquisition.getText().toString();
        evaluatorLastDay = eLastDay.getText().toString();
        int checkedId = radioGroup.getCheckedRadioButtonId();

        String timeStamp = df.format(new Date());

        if (evaluatorAcquisition.length() == 0) {
            evaluatorAcquisition = "0";
            evaluatorLastDay = "0";
        }


        if (checkedId != -1) {
            // 選択されているラジオボタンの取得
            RadioButton radioButton = (RadioButton) findViewById(checkedId);// (Fragmentの場合は「getActivity().findViewById」にする)

            // ラジオボタンのテキストを取得
            String text = radioButton.getText().toString();

            if (text.equals("男")) {
                evaluatorSexText = "Male";
            } else {
                evaluatorSexText = "Female";
            }
            Log.v("checked", evaluatorSexText);

        }

        if (evaluatorName.length() != 0 && evaluatorOld.length() != 0 && evaluatorAcquisition.length() != 0 && evaluatorLastDay.length() != 0 && evaluatorSexText.length() != 0) {
            DataLogger dataLogger = new DataLogger(getApplicationContext(), timeStamp, driverName, evaluatorName);
            dataLogger.saveUserInfo("Name","Sex","Old","Acquisition date","The Last Date");
            dataLogger.saveUserInfo(driverName, driverSexText, driverOld, driverAcquisition, driverLastDay);
            dataLogger.saveUserInfo(evaluatorName, evaluatorSexText, evaluatorOld, evaluatorAcquisition, evaluatorLastDay);
            dataLogger.saveUserInfo("Time","item_01","item_02","item_03","item_04");
            Intent intent = new Intent(this, EvaluateActivity.class);
            intent.putExtra("TIME_STAMP",timeStamp);
            intent.putExtra("DRIVER_NAME", driverName);
            intent.putExtra("EVALUATOR_NAME", evaluatorName);
            startActivity(intent);
        } else {
            Toast.makeText(this, "必要な項目が抜けています！", Toast.LENGTH_LONG).show();
        }
    }
}
