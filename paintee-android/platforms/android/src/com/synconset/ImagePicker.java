/**
 * An Image Picker Plugin for Cordova/PhoneGap.
 */
package com.synconset;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.content.pm.PackageManager;
import android.os.Build;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class ImagePicker extends CordovaPlugin {
	public static String TAG = "ImagePicker";

	private CallbackContext callbackContext;
	private JSONObject params;

	public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
		 this.callbackContext = callbackContext;
		 this.params = args.getJSONObject(0);

		 Context context = this.cordova.getActivity().getApplicationContext();

		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int read = context
				.checkCallingOrSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int write = context
				.checkCallingOrSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (read != PackageManager.PERMISSION_GRANTED && write != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(cordova.getActivity(), GettingPermissionsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
				if (action.equals("getPictures")) {
					Intent intent = new Intent(cordova.getActivity(), MultiImageChooserActivity.class);
					int max = 20;
					int desiredWidth = 0;
					int desiredHeight = 0;
					int quality = 100;
					if (this.params.has("maximumImagesCount")) {
						max = this.params.getInt("maximumImagesCount");
					}
					if (this.params.has("width")) {
						desiredWidth = this.params.getInt("width");
					}
					if (this.params.has("height")) {
						desiredHeight = this.params.getInt("height");
					}
					if (this.params.has("quality")) {
						quality = this.params.getInt("quality");
					}
					intent.putExtra("MAX_IMAGES", max);
					intent.putExtra("WIDTH", desiredWidth);
					intent.putExtra("HEIGHT", desiredHeight);
					intent.putExtra("QUALITY", quality);
					if (this.cordova != null) {
						this.cordova.startActivityForResult((CordovaPlugin) this, intent, 0);
					}
				}
			}
        } else {
			if (action.equals("getPictures")) {
				Intent intent = new Intent(cordova.getActivity(), MultiImageChooserActivity.class);
				int max = 20;
				int desiredWidth = 0;
				int desiredHeight = 0;
				int quality = 100;
				if (this.params.has("maximumImagesCount")) {
					max = this.params.getInt("maximumImagesCount");
				}
				if (this.params.has("width")) {
					desiredWidth = this.params.getInt("width");
				}
				if (this.params.has("height")) {
					desiredHeight = this.params.getInt("height");
				}
				if (this.params.has("quality")) {
					quality = this.params.getInt("quality");
				}
				intent.putExtra("MAX_IMAGES", max);
				intent.putExtra("WIDTH", desiredWidth);
				intent.putExtra("HEIGHT", desiredHeight);
				intent.putExtra("QUALITY", quality);
				if (this.cordova != null) {
					this.cordova.startActivityForResult((CordovaPlugin) this, intent, 0);
				}
			}
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.e("resulted : ","resulted");
		if (resultCode == Activity.RESULT_OK && data != null) {
			ArrayList<String> fileNames = data.getStringArrayListExtra("MULTIPLEFILENAMES");
			JSONArray res = new JSONArray(fileNames);
			this.callbackContext.success(res);
		} else if (resultCode == Activity.RESULT_CANCELED && data != null) {
			String error = data.getStringExtra("ERRORMESSAGE");
			this.callbackContext.error(error);
		} else if (resultCode == Activity.RESULT_CANCELED) {
			JSONArray res = new JSONArray();
			this.callbackContext.success(res);
		} else {
			this.callbackContext.error("No images selected");
		}
	}
}
