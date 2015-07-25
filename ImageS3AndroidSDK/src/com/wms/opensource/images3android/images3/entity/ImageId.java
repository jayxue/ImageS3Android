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

package com.wms.opensource.images3android.images3.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageId {
	@JsonProperty("imagePlantId")
	private String imagePlantId;
	@JsonProperty("imageId")
	private String imageId;

	/**
	*
	* @return
	* The imagePlantId
	*/
	@JsonProperty("imagePlantId")
	public String getImagePlantId() {
		return imagePlantId;
	}

	/**
	*
	* @param imagePlantId
	* The imagePlantId
	*/
	@JsonProperty("imagePlantId")
	public void setImagePlantId(String imagePlantId) {
		this.imagePlantId = imagePlantId;
	}

	/**
	*
	* @return
	* The imageId
	*/
	@JsonProperty("imageId")
	public String getImageId() {
		return imageId;
	}

	/**
	*
	* @param imageId
	* The imageId
	*/
	@JsonProperty("imageId")
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
}
