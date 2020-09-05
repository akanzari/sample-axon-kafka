/*******************************************************************************
 *******************************************************************************/
package com.axonkafka.events.bases;

import java.io.Serializable;

public class CatalogBaseEvent<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public final T catalogId;

	public CatalogBaseEvent(T catalogId) {
		this.catalogId = catalogId;
	}

}