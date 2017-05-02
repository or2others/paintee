/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.paintee1.pnt;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import org.apache.cordova.*;
import org.apache.cordova.inappbrowser.InAppBrowser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends CordovaActivity
{
    InAppBrowser mPlugin;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);
    }

    public void bind(Intent serviceIntent, ServiceConnection mServiceConnection, InAppBrowser plugin){
        this.mPlugin = plugin;
        bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void bind2(InAppBrowser plugin){
        this.mPlugin = plugin;

    }


    public void unbind(ServiceConnection mServiceConnection){
        unbindService(mServiceConnection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
//                    String token = jo.getString("purchaseToken");
                    String token = jo.getString("orderId");
                    mPlugin.paySuccessed(token);
                    Log.e(TAG, "You have bought the " + sku + ". Excellent choice, adventurer!");
                }
                catch (JSONException e) {
                    Log.e(TAG, "Failed to parse purchase data.");
                    e.printStackTrace();
                }
            }
            else if (resultCode == RESULT_CANCELED) {

                mPlugin.payFailed();

            }
            else{
                mPlugin.payFailed();
                Log.e("user cancel ? : ", String.valueOf(resultCode));
            }
        }
        else if(requestCode == 1){
            if(resultCode == RESULT_OK){

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
//                String imagepath = getPath(selectedImage);
//                File myFile = new File(picturePath);
//                Bitmap photo = BitmapFactory.decodeFile(picturePath);
                mPlugin.photoSuccess(picturePath, selectedImage);
            }
            else{

            }
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();

        return cursor.getString(column_index);
    }

}
