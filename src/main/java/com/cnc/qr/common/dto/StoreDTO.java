package com.cnc.qr.common.dto;

import javax.validation.constraints.Size;

/**
 * A DTO representing a user, with his authorities.
 */
public class StoreDTO {

    private Long id;

    private String storeId;

    @Size(max = 50)
    private String storeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
