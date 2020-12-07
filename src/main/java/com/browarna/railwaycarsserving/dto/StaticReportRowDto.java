package com.browarna.railwaycarsserving.dto;

public interface StaticReportRowDto {
    String getCustomer();
    String getOperation();
    String getCargoType();
    String getWagonType();
    Long getDeliveryWagonQuantity();
    Long getDeliveryWeightSum();
    Long getDispatchWagonQuantity();
    Long getDispatchWeightSum();
    Long getLoadUnloadWorkQuantity();
    Long getLoadUnloadWorkWeightSum();
    Long getCustomerLoadUnloadWorkQuantity();
    Long getCustomerLoadUnloadWorkWeightSum();
    Long getNotEndDelivery();
    Long getWithoutOperation();
}
