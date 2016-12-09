package zack.inc.jp.experimentinfo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by togane on 2016/11/25.
 */
public class DataLogger {

    private String driverName;
    private String evaluatorName;
    private String saveTime;
    private Context appContext;
    String filePathStr;


    public DataLogger(Context context, String timeStamp, String driverName, String evaluatorName) {

        this.driverName = driverName;
        this.evaluatorName = evaluatorName;
        this.saveTime = timeStamp;
        this.appContext = context;
        String exPathStr = appContext.getExternalFilesDir(null).getPath();// /root/sdcard/Android/data/package_name/filesを取得
        this.filePathStr = exPathStr + "/" + saveTime + "_dr_" + driverName + "_ev_" + evaluatorName + ".csv";


    }


    public void saveUserInfo(String name, String sex, String old, String acquisition, String lastDay) {
        FileOutputStream fos;
        BufferedWriter bw = null;

        try {
            fos = new FileOutputStream(filePathStr, true);// /root/sdcard/Android/data/package_name/files/直下に記録
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            StringBuilder sb = new StringBuilder();
            //Logに必要なデータを詰めていく
            sb.append(name); //記録確定時間からの経過時間
            sb.append(",");
            sb.append(sex);
            sb.append(",");
            sb.append(old);
            sb.append(",");
            sb.append(acquisition);
            sb.append(",");
            sb.append(lastDay);

            bw.write(sb.toString());
            bw.newLine();
            bw.flush();

            fos.close();

        } catch (FileNotFoundException nfe) {
            nfe.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

        }

    }

    public void saveValue(String timeStamp, String item1Value, String item2Value, String item3Value, String item4Value) {
        FileOutputStream fos;
        BufferedWriter bw = null;
        Log.d("dataSaved","is called");

        try {
            fos = new FileOutputStream(filePathStr, true);// /root/sdcard/Android/data/package_name/files/直下に記録
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            StringBuilder sb = new StringBuilder();
            //Logに必要なデータを詰めていく
            sb.append(timeStamp); //記録確定時間からの経過時間
            sb.append(",");
            sb.append(item1Value);
            sb.append(",");
            sb.append(item2Value);
            sb.append(",");
            sb.append(item3Value);
            sb.append(",");
            sb.append(item4Value);

            bw.write(sb.toString());
            bw.newLine();
            bw.flush();

            fos.close();

        } catch (FileNotFoundException nfe) {
            nfe.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }

        }
    }

}
