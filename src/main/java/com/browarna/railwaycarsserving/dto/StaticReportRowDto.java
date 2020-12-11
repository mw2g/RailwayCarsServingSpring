package com.browarna.railwaycarsserving.dto;

public interface StaticReportRowDto {
    String getCustomer();
    String getOperation();
    String getCargoType();
    String getWagonType();
    Double getDeliveryWagonQuantity();
    Double getDeliveryWeightSum();
    Double getDispatchWagonQuantity();
    Double getDispatchWeightSum();
    Double getLoadUnloadWorkQuantity();
    Double getLoadUnloadWorkWeightSum();
    Double getCustomerLoadUnloadWorkQuantity();
    Double getCustomerLoadUnloadWorkWeightSum();
    Double getNotEndDelivery();
    Double getWithoutOperation();
}
