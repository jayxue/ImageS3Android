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

public class Metadata {
	@JsonProperty("dimension")
	private Dimension dimension;
	@JsonProperty("format")
	private String format;
	@JsonProperty("size")
	private Integer size;

	/**
	*
	* @return
	* The dimension
	*/
	@JsonProperty("dimension")
	public Dimension getDimension() {
		return dimension;
	}

	/**
	*
	* @param dimension
	* The dimension
	*/
	@JsonProperty("dimension")
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	/**
	*
	* @return
	* The format
	*/
	@JsonProperty("format")
	public String getFormat() {
		return format;
	}

	/**
	*
	* @param format
	* The format
	*/
	@JsonProperty("format")
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	*
	* @return
	* The size
	*/
	@JsonProperty("size")
	public Integer getSize() {
		return size;
	}

	/**
	*
	* @param size
	* The size
	*/
	@JsonProperty("size")
	public void setSize(Integer size) {
		this.size = size;
	}
}
