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

package com.wms.opensource.images3android.dialog;

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.listener.ConfirmUploadImageDialogBuilderOnClickListener;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Handler;

public class ConfirmUploadImageDialogBuilder extends Builder {

	public ConfirmUploadImageDialogBuilder(Context context, String filePath, Handler handler) {
		super(context);
		this.setCancelable(false);
		this.setMessage(context.getString(R.string.confirmUploadImage));
		this.setNegativeButton(context.getString(R.string.cancel), null);
		this.setPositiveButton(context.getString(R.string.yes), new ConfirmUploadImageDialogBuilderOnClickListener(context, filePath, handler));
	}

}
