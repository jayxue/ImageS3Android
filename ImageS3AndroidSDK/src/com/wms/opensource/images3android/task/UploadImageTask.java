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

package com.wms.opensource.images3android.task;

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.images3.ImageS3Service;
import com.wms.opensource.images3android.util.DialogUtil;
import com.wms.opensource.images3android.util.MessageUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

public class UploadImageTask extends AsyncTask<String, Void, Integer> {

	private Context context;
	private boolean isProgressDialogRequired = true;
	private boolean isProgressDialogCancelable = true;
	private int waitingText = -1;
	private Handler handler = null;
	
	private ProgressDialog progressDialog = null;
	
	public UploadImageTask(Context context, boolean isProgressDialogRequired, boolean isProgressDialogCancelable,
			int waitingText, Handler handler) {
		this.context = context;
		this.isProgressDialogRequired = isProgressDialogRequired;
		this.isProgressDialogCancelable = isProgressDialogCancelable;
		this.waitingText = waitingText;
		this.handler = handler;
	}

	protected void onPreExecute() {
		if(waitingText <= 0) {
			return;
		}
		
		if (isProgressDialogRequired) {
			progressDialog = DialogUtil.showWaitingProgressDialog(context, ProgressDialog.STYLE_SPINNER, context.getString(waitingText), isProgressDialogCancelable);
		}
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		// params[0] is service url, params[1] image plant id, params[2] is path of the file to upload
		int result = ImageS3Service.addImage(params[0], params[1], params[2]);		
		return result;
	}

	protected void onPostExecute(Integer result) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

		if (result.intValue() == 200) {
			DialogUtil.showDialog(context, context.getString(R.string.uploadSuccessful));
			MessageUtil.sendHandlerMessage(handler, HandlerMessage.IMAGE_UPLOADED);
		}
		else {
			DialogUtil.showExceptionAlertDialog(context, context.getString(R.string.uploadFailedTitle), context.getString(R.string.uploadFailed));
		}
	}
}
