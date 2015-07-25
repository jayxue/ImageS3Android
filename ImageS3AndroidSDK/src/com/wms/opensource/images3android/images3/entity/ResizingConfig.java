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

public class ResizingConfig {

	@JsonProperty("unit")
	private String unit;
	@JsonProperty("width")
	private Integer width;
	@JsonProperty("height")
	private Integer height;
	@JsonProperty("isKeepProportions")
	private Boolean isKeepProportions;

	/**
	*
	* @return
	* The unit
	*/
	@JsonProperty("unit")
	public String getUnit() {
		return unit;
	}

	/**
	*
	* @param unit
	* The unit
	*/
	@JsonProperty("unit")
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	*
	* @return
	* The width
	*/
	@JsonProperty("width")
	public Integer getWidth() {
		return width;
	}

	/**
	*
	* @param width
	* The width
	*/
	@JsonProperty("width")
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	*
	* @return
	* The height
	*/
	@JsonProperty("height")
	public Integer getHeight() {
		return height;
	}

	/**
	*
	* @param height
	* The height
	*/
	@JsonProperty("height")
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	*
	* @return
	* The isKeepProportions
	*/
	@JsonProperty("isKeepProportions")
	public Boolean getIsKeepProportions() {
		return isKeepProportions;
	}

	/**
	*
	* @param isKeepProportions
	* The isKeepProportions
	*/
	@JsonProperty("isKeepProportions")
	public void setIsKeepProportions(Boolean isKeepProportions) {
		this.isKeepProportions = isKeepProportions;
	}
}
