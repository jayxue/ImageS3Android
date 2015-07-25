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

package com.wms.opensource.images3android.activity;

import java.io.File;
import java.util.UUID;

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.dialog.ConfirmUploadImageDialogBuilder;
import com.wms.opensource.images3android.handler.UploadImageHandler;
import com.wms.opensource.images3android.intent.IntentRequestCode;
import com.wms.opensource.images3android.listener.ImageButtonBackgroundSelector;
import com.wms.opensource.images3android.listener.ImageButtonPictureGalleryOnClickListener;
import com.wms.opensource.images3android.listener.ImageButtonTakePictureOnClickListener;
import com.wms.opensource.images3android.manager.SamsungCameraManager;
import com.wms.opensource.images3android.type.NetworkStatus;
import com.wms.opensource.images3android.util.ActivityUtil;
import com.wms.opensource.images3android.util.ImageUtil;
import com.wms.opensource.images3android.util.NetworkUtil;
import com.wms.opensource.images3android.util.StorageUtil;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadImageActivity extends AppCompatActivity {

	public static Activity currentlyVisible = null;
	
	private ImageView imageViewPreviewPicture = null;
	private ImageButton imageButtonTakePicture = null;
	private ImageButton imageButtonPickImage = null;
	private ImageButton imageButtonPost = null;
	private ImageButton imageButtonImagePlant = null;
	
	private File tempPictureFile = null;

	private ConfirmUploadImageDialogBuilder confirmDialogBuilder = null;
	
	private UploadImageHandler handler = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.take_picture);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);

		tempPictureFile = getTempPictureFile();

		imageViewPreviewPicture = (ImageView) findViewById(R.id.imageViewPreviewPicture);

		imageButtonTakePicture = (ImageButton) findViewById(R.id.imageButtonTakePicture);
		imageButtonTakePicture.setOnTouchListener(new ImageButtonBackgroundSelector());
		ImageButtonTakePictureOnClickListener imageButtonTakePictureOnClickListener = new ImageButtonTakePictureOnClickListener(this);
		imageButtonTakePictureOnClickListener.setImageFile(tempPictureFile);
		imageButtonTakePicture.setOnClickListener(imageButtonTakePictureOnClickListener);		

		imageButtonPickImage = (ImageButton) findViewById(R.id.imageButtonPickImage);
		imageButtonPickImage.setOnClickListener(new ImageButtonPictureGalleryOnClickListener(this));
		imageButtonPickImage.setOnTouchListener(new ImageButtonBackgroundSelector());

		imageButtonPost = (ImageButton) findViewById(R.id.imageButtonPost);
		imageButtonPost.setOnClickListener(new ImageButtonPostOnClickListener());
		imageButtonPost.setOnTouchListener(new ImageButtonBackgroundSelector());
		imageButtonPost.setEnabled(false);

		imageButtonImagePlant = (ImageButton) findViewById(R.id.imageButtonImagePlant);
		imageButtonImagePlant.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityUtil.goToActivity(UploadImageActivity.this, ImageListFragmentActivity.class);
			}
			
		});
		
		handler = new UploadImageHandler(this);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == IntentRequestCode.TAKE_PICTURE && resultCode == RESULT_OK) {
			if(ImageUtil.scaleImageAndSetForImageView(tempPictureFile.getAbsolutePath(), 8, imageViewPreviewPicture, 600)) {
				imageButtonPost.setEnabled(true);
				imageButtonPost.setBackgroundResource(R.drawable.publish_enabled);
			}
		}
		else if (requestCode == IntentRequestCode.TAKE_PICTURE_SAMSUNG && resultCode == Activity.RESULT_OK) {
			SamsungCameraManager manager = new SamsungCameraManager(this);
			manager.onActivityResult();

			// Get the real image
            String pictureFilePath = manager.getPictureFilePath();
            tempPictureFile = new File(pictureFilePath);
            if (ImageUtil.scaleSaveImageAndSetForImageView(pictureFilePath, 8, imageViewPreviewPicture, 600)) {
				imageButtonPost.setEnabled(true);
				imageButtonPost.setBackgroundResource(R.drawable.publish_enabled);
            }
		}
		else if (requestCode == IntentRequestCode.PICK_UP_PICTURE && resultCode == RESULT_OK) {
			Uri selectedPicture = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedPicture, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();

			ImageUtil.scaleImageAndSetForImageView(filePath, 2, imageViewPreviewPicture, 600);
			imageButtonPost.setEnabled(true);
			imageButtonPost.setBackgroundResource(R.drawable.publish_enabled);
			tempPictureFile = new File(filePath);
		} 

		super.onActivityResult(requestCode, resultCode, data);
	}

	private class ImageButtonPostOnClickListener implements
			ImageButton.OnClickListener {

		@Override
		public void onClick(View v) {
	    	NetworkStatus networkStatus = NetworkUtil.getNetworkStatus(UploadImageActivity.this);
		    if (networkStatus.equals(NetworkStatus.WIFI_CONNECTED) || networkStatus.equals(NetworkStatus.MOBILE_CONNECTED)) {
				confirmDialogBuilder = new ConfirmUploadImageDialogBuilder(UploadImageActivity.this, tempPictureFile.getAbsolutePath(), handler); 
				confirmDialogBuilder.create().show();;
		    }
		    else {
		    	Toast.makeText(UploadImageActivity.this, getString(R.string.noNetworkAvailable), Toast.LENGTH_LONG).show();
		    }
		}

	}

	private File getTempPictureFile() {
		File cacheFolder = StorageUtil.getTempDirectory(this);
		File file = new File(cacheFolder,  UUID.randomUUID().toString() + ".jpg");
		return file;
	}
	
	public void reset() {
		tempPictureFile = getTempPictureFile();
		imageViewPreviewPicture.setImageBitmap(null);
		imageButtonPost.setEnabled(false);
		imageButtonPost.setBackgroundResource(R.drawable.publish_disabled);
	}
}
