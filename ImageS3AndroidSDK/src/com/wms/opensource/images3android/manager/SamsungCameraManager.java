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

package com.wms.opensource.images3android.manager;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Since Samsung device has special issues on camera, we need a separate class to deal with it.
 */
public class SamsungCameraManager {

	private Activity activity = null;
	
	private String pictureFilePath = ""; 
	
	public SamsungCameraManager(Activity activity) {
		this.activity = activity;
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	public void onActivityResult() {
        String[] projection = {
                MediaStore.Images.Thumbnails._ID,
                MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.KIND,
                MediaStore.Images.Thumbnails.DATA};
        String selection = MediaStore.Images.Thumbnails.KIND + "=" + MediaStore.Images.Thumbnails.MINI_KIND;
        String sort = MediaStore.Images.Thumbnails._ID + " DESC";

        Cursor myCursor = activity.managedQuery(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, selection, null, sort);

        long imageId = 0l;
        long thumbnailImageId = 0l;
        String thumbnailPath = "";

        try {
            myCursor.moveToFirst();
            imageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID));
            thumbnailImageId = myCursor.getLong(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID));
            thumbnailPath = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
        }
        finally {
            myCursor.close();
        }

        //Create new Cursor to obtain the file Path for the large image
        String[] largeFileProjection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA
        };

        String largeFileSort = MediaStore.Images.ImageColumns._ID + " DESC";
        myCursor = activity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, largeFileProjection, null, null, largeFileSort);
        String largeImagePath = "";

        try {
            myCursor.moveToFirst();
            largeImagePath = myCursor.getString(myCursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA));
        } finally {
            myCursor.close();
        }

        Uri uriLargeImage = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageId));
        Uri uriThumbnailImage = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, String.valueOf(thumbnailImageId));

		// Get the real image
        pictureFilePath = largeImagePath;
	}
	
	public String getPictureFilePath() {
		return pictureFilePath;
	}
	
}
