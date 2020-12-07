package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.dto.StaticReportRowDto;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<DeliveryOfWagon, Long> {
    String query = "select\n" +
            "\tif(grouping(c.customer_name),\n" +
            "\t'all',\n" +
            "\tc.customer_name) as customer,\n" +
            "\tif(grouping(co.operation_name),\n" +
            "\t'all',\n" +
            "\tco.operation_name) as operation,\n" +
            "\tif(grouping(ct.type_name),\n" +
            "\t'all',\n" +
            "\tct.type_name) as cargoType,\n" +
            "\tif(grouping(wt.type_name),\n" +
            "\t'all',\n" +
            "\twt.type_name) as wagonType,\n" +
            "\tsum(if (d.start_date between ?1 and ?2, 1, 0)) as deliveryWagonQuantity,\n" +
            "\tsum(if (d.start_date between ?1 and ?2, d.cargo_weight, 0)) as deliveryWeightSum,\n" +
            "\tsum(if (d.end_date between ?1 and ?2 and co.operation_name != 'БЕЗ ОПЕРАЦИИ' and co.operation_name != 'ВЕСОПОВЕРОЧНЫЙ', 1, 0)) as dispatchWagonQuantity,\n" +
            "\tsum(if (d.end_date between ?1 and ?2 and co.operation_name != 'БЕЗ ОПЕРАЦИИ' and co.operation_name != 'ВЕСОПОВЕРОЧНЫЙ', d.cargo_weight, 0)) as dispatchWeightSum,\n" +
            "\tsum(if (d.load_unload_work = 1 and d.end_date between ?1 and ?2 and co.operation_name != 'БЕЗ ОПЕРАЦИИ' and co.operation_name != 'ВЕСОПОВЕРОЧНЫЙ', 1, 0)) as loadUnloadWorkQuantity,\n" +
            "\tsum(if (d.load_unload_work = 1 and d.end_date between ?1 and ?2 and co.operation_name != 'БЕЗ ОПЕРАЦИИ' and co.operation_name != 'ВЕСОПОВЕРОЧНЫЙ', d.cargo_weight, 0)) as loadUnloadWorkWeightSum,\n" +
            "\tsum(if (d.load_unload_work = 0 and d.end_date between ?1 and ?2, 1, 0)) as customerLoadUnloadWorkQuantity,\n" +
            "\tsum(if (d.load_unload_work = 0 and d.end_date between ?1 and ?2, d.cargo_weight, 0)) as customerLoadUnloadWorkWeightSum,\n" +
            "\tsum(if (d.end_date = '0000.00.00' or d.end_date > ?2, 1, 0)) as notEndDelivery,\n" +
            "\tsum(if (co.operation_name = 'БЕЗ ОПЕРАЦИИ' or co.operation_name = 'ВЕСОПОВЕРОЧНЫЙ', 1, 0)) as withoutOperation\n" +
            "from\n" +
            "\tdelivery_of_wagon d\n" +
            "left join customer c on\n" +
            "\tc.customer_id = d.customer_id\n" +
            "left join cargo_operation co on\n" +
            "\td.operation_id = co.operation_id\n" +
            "left join cargo_type ct on\n" +
            "\td.cargo_type_id = ct.type_id\n" +
            "left join wagon_type wt on\n" +
            "\td.wagon_type_id = wt.type_id\n" +
            "where\n" +
            "\td.start_date between ?1 and ?2\n" +
            "\tor d.end_date between ?1 and ?2\n" +
            "group by\n" +
            "\tc.customer_name,\n" +
            "\tco.operation_name,\n" +
            "\tct.type_name,\n" +
            "\twt.type_name with rollup\n" +
            "order by\n" +
            "\tc.customer_name,\n" +
            "\tco.operation_name,\n" +
            "\tct.type_name,\n" +
            "\twt.type_name;";



    @Query(value = query, nativeQuery = true)
    List<StaticReportRowDto> staticReport(Date afterDate, Date beforeDate);

    @Query(value = query, nativeQuery = true)
    List<StaticReportRowDto> report();
}
