package zack.inc.jp.experimentinfo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DriverInfoActivity extends AppCompatActivity {

    EditText dName, dOld, dAcquisition, dFrequency, dLastDay;
    private String driverName, driverOld, driverAcquisition, driverFrequency, driverLastDay, driverSexText;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);

        dName = (EditText) findViewById(R.id.DriverNameText);
        dOld = (EditText) findViewById(R.id.dOldText);
        dAcquisition = (EditText) findViewById(R.id.dAcquisitionDateText);
        dFrequency = (EditText) findViewById(R.id.dFrequency);
        dLastDay = (EditText) findViewById(R.id.dLastDayText);
        radioGroup = (RadioGroup) findViewById(R.id.dSexRadioGroup);

    }

    public void nextButton(View v) {


        driverName = dName.getText().toString();
        driverOld = dOld.getText().toString();
        driverAcquisition = dAcquisition.getText().toString();
        driverFrequency = dFrequency.getText().toString();
        driverLastDay = dLastDay.getText().toString();
        int checkedId = radioGroup.getCheckedRadioButtonId();

        if (checkedId != -1) {
            // 選択されているラジオボタンの取得
            RadioButton radioButton = (RadioButton) findViewById(checkedId);// (Fragmentの場合は「getActivity().findViewById」にする)

            // ラジオボタンのテキストを取得
            String text = radioButton.getText().toString();

            if (text.equals("男")) {
                driverSexText = "Male";
            } else {
                driverSexText = "Female";
            }
            Log.v("checked", driverSexText);

        }

        if (driverName.length() != 0 && driverOld.length() != 0 && driverAcquisition.length() != 0 && driverFrequency.length() != 0 && driverLastDay.length() != 0 && driverSexText.length() != 0) {

            Intent intent = new Intent(this, EvaluetorInfoActivity.class);

            intent.putExtra("DRIVER_NAME", driverName);
            intent.putExtra("DRIVER_OLD", driverOld);
            intent.putExtra("DRIVER_ACQUISITION", driverAcquisition);
            intent.putExtra("DRIVER_FREQUENCY",driverFrequency);
            intent.putExtra("DRIVER_LAST_DAY", driverLastDay);
            intent.putExtra("DRIVER_SEX", driverSexText);

            startActivity(intent);
        } else {
            Toast.makeText(this, "全ての項目を入力して下さい", Toast.LENGTH_LONG).show();
        }
    }

}
