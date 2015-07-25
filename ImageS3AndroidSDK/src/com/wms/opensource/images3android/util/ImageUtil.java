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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageUtil {

	public static boolean scaleSaveImageAndSetForImageView(String filePath,	int scale, ImageView imageViewPreviewPicture, int maxSize) {
		File file = new File(filePath);
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;

			int newScale = 1;	
		    if(options.outHeight > maxSize || options.outWidth > maxSize) {
		        newScale = (int)Math.pow(2, (int) Math.round(Math.log(maxSize / 
		           (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
		    }

		    BitmapFactory.Options newOptions = new BitmapFactory.Options();
			if(newScale > scale) {
				newOptions.inSampleSize = newScale;
			}
			else {
				newOptions.inSampleSize = scale;
			}
	
			Bitmap scaledBm = BitmapFactory.decodeStream(new FileInputStream(file), null, newOptions);
			OutputStream outStream = new FileOutputStream(file);
			scaledBm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			outStream.flush();
			outStream.close();
			imageViewPreviewPicture.setImageBitmap(scaledBm);
			return true;
		} 
		catch (IOException e) {
			return false;
		}
	}

	public static boolean scaleImageAndSetForImageView(String filePath, int scale, ImageView imageViewPreviewPicture, int maxSize) {
		File file = new File(filePath);
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		try {
			int newScale = 1;	
		    if(options.outHeight > maxSize || options.outWidth > maxSize) {
		        newScale = (int)Math.pow(2, (int) Math.round(Math.log(maxSize / 
		           (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
		    }

		    BitmapFactory.Options newOptions = new BitmapFactory.Options();
			if(newScale > scale) {
				newOptions.inSampleSize = newScale;
			}
			else {
				newOptions.inSampleSize = scale;
			}

			Bitmap scaledBm = BitmapFactory.decodeStream(new FileInputStream(file), null, newOptions);
			imageViewPreviewPicture.setImageBitmap(scaledBm);
			return true;
		}
		catch (FileNotFoundException e) {
			return false;
		}

	}

}
