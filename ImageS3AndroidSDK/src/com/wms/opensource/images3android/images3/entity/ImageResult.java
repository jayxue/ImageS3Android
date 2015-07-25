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

public class ImageResult {

	@JsonProperty("id")
	private ImageId id;
	@JsonProperty("dateTime")
	private String dateTime;
	@JsonProperty("version")
	private Version version;
	@JsonProperty("metadata")
	private Metadata metadata;

	/**
	*
	* @return
	* The id
	*/
	@JsonProperty("id")
	public ImageId getId() {
		return id;
	}

	/**
	*
	* @param id
	* The id
	*/
	@JsonProperty("id")
	public void setId(ImageId id) {
		this.id = id;
	}

	/**
	*
	* @return
	* The dateTime
	*/
	@JsonProperty("dateTime")
	public String getDateTime() {
		return dateTime;
	}

	/**
	*
	* @param dateTime
	* The dateTime
	*/
	@JsonProperty("dateTime")
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	*
	* @return
	* The version
	*/
	@JsonProperty("version")
	public Version getVersion() {
		return version;
	}

	/**
	*
	* @param version
	* The version
	*/
	@JsonProperty("version")
	public void setVersion(Version version) {
		this.version = version;
	}

	/**
	*
	* @return
	* The metadata
	*/
	@JsonProperty("metadata")
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	*
	* @param metadata
	* The metadata
	*/
	@JsonProperty("metadata")
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
}
