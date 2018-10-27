package com.chankane.chocolate.brawny.spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.res.AssetManager;

class CSVDao {

    public static void parse(Context context) {
        // AssetManagerの呼び出し
        AssetManager assetManager = context.getResources().getAssets();
        try {
            // CSVファイルの読み込み
            InputStream is = assetManager.open("hard.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                // 各行が","で区切られていて4つの項目があるとする
                StringTokenizer st = new StringTokenizer(line, ",");
                String first = st.nextToken();
                String second = st.nextToken();
                String third = st.nextToken();
                String fourth = st.nextToken();

                // 何らかの処理
            }
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
