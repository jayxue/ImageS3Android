/*
 * Copyright 2015 Waterloo Mobile Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wms.opensource.images3android.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
//import android.util.Log;
import android.widget.EditText;

public class DialogUtil {

	public static ProgressDialog showWaitingProgressDialog(Context context, int style, String message, boolean cancelable) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(style);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(cancelable);
		try {
			progressDialog.show();
		}
		catch(Exception e) {
			
		}
		return progressDialog;
	}

	public static void showExceptionAlertDialog(Context context, String title, String message) {
		Builder exceptionAlertDialogBuilder = new Builder(context);
		exceptionAlertDialogBuilder.setTitle(title).setMessage(message).setCancelable(true).setNeutralButton("OK", null);
		AlertDialog alert = exceptionAlertDialogBuilder.create();
		try {
			alert.show();
		}
		catch(Exception e) {
			
		}			
	}

	public static void showDialog(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		EditText msgEditText = new EditText(context);
		msgEditText.setText(msg);
		msgEditText.setFocusable(false);
		builder.setCancelable(false).setView(msgEditText).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		try {
			alert.show();
		}	
		catch(Exception e) {
			
		}
	}

}
