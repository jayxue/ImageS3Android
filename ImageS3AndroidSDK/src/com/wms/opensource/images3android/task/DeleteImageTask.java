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
import com.wms.opensource.images3android.images3.ImageS3Service;
import com.wms.opensource.images3android.util.DialogUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DeleteImageTask  extends AsyncTask<Void, Void, Boolean> {

	private Context context = null;
	private String baseUrl = "";
	private String imagePlantId = "";
	private String imageId = "";
	
	private ProgressDialog progressDialog = null;

	public DeleteImageTask(Context context, String baseUrl, String imagePlantId, String imageId) {
		this.context = context;
		this.baseUrl = baseUrl;
		this.imagePlantId = imagePlantId;
		this.imageId = imageId;
	}
	
	protected void onPreExecute() {
		progressDialog = DialogUtil.showWaitingProgressDialog(context, ProgressDialog.STYLE_SPINNER, context.getString(R.string.deletingImage), false);
    }
	
	@Override
	protected Boolean doInBackground(Void... params) {
		return ImageS3Service.deleteImage(baseUrl, imagePlantId, imageId);
	}

	@Override
	protected void onPostExecute(Boolean result) {
    	if(progressDialog != null && progressDialog.isShowing() == true)
    		progressDialog.dismiss();		

		if(result == true) {
			DialogUtil.showDialog(context, context.getString(R.string.imageDeleted));
		}
		else {
			DialogUtil.showExceptionAlertDialog(context, context.getString(R.string.deleteFailedTitle), context.getString(R.string.deleteFailed));
		}
	}
}
