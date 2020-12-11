package com.browarna.railwaycarsserving.dto;

public interface GeneralSetReportRowDto {
    String getCustomer();
    String getOperation();
    String getCargoType();
    Double getDispatchWeightSum();
    Double getDispatchWagonQuantity();
    Double getDeliveryDispatchSum();
    Double getTotalTime();
    Double getExactCalcTime();
    Double getCalcTime();
    Double getMaxPayTime();
    Double getExactPayTime();
    Double getPayTime();
    Double getDraftPaySum();
    Double getPaySum();
    Double getPenaltyTime();
    Double getPenaltySum();
    Double getShuntingWork();
    Double getShuntingWorkSum();
    Double getTotalSum();
    Double getLoadUnloadWorkQuantity();
    Double getLoadUnloadWorkWeightSum();
    Double getLoadUnloadWorkSum();
}
