package com.browarna.railwaycarsserving.repository;

import com.browarna.railwaycarsserving.dto.GeneralSetReportRowDto;
import com.browarna.railwaycarsserving.dto.StaticReportRowDto;
import com.browarna.railwaycarsserving.model.DeliveryOfWagon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<DeliveryOfWagon, Long> {
    String staticReportQuery = "select\n" +
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
            "\tsum(if (d.memo_of_dispatch_id is null , 1, 0)) as notEndDelivery,\n" +
            "\tsum(if (co.operation_name = 'БЕЗ ОПЕРАЦИИ' and d.end_date between ?1 and ?2, 1, 0)) as withoutOperation\n" +
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
            "\tor d.memo_of_dispatch_id is null \n" +
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

    String staticReportForCustomerQuery = "select\n" +
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
            "\tsum(if (d.memo_of_dispatch_id is null , 1, 0)) as notEndDelivery,\n" +
            "\tsum(if (co.operation_name = 'БЕЗ ОПЕРАЦИИ' and d.end_date between ?1 and ?2, 1, 0)) as withoutOperation\n" +
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
            "\t(d.start_date between ?1 and ?2\n" +
            "\tor d.end_date between ?1 and ?2\n" +
            "\tor d.memo_of_dispatch_id is null)\n" +
            "\tand c.customer_name = ?3\n" +
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

    String generalSetReportForCustomerQuery = "select\n" +
            "\tif(grouping(c.customer_name),\n" +
            "\t'all',\n" +
            "\tc.customer_name) as customer,\n" +
            "\tif(grouping(co.operation_name),\n" +
            "\t'all',\n" +
            "\tco.operation_name) as operation,\n" +
            "\tif(grouping(ct.type_name),\n" +
            "\t'all',\n" +
            "\tct.type_name) as cargoType,\n" +
            "\tsum(d.cargo_weight) as dispatchWeightSum,\n" +
            "\tcount(d.delivery_id) as dispatchWagonQuantity,\n" +
            "\tsum(@deliveryDispatchSum \\:= (select t.tariff from tariff t where t.type_id = (select tt.type_id from tariff_type tt where tt.type_code = 'deliveryDispatchWork') and t.relevance_date < d.end_date order by t.relevance_date desc limit 1)) as deliveryDispatchSum,\n" +
            "\tsum(@totalTime \\:= if (o.owner_name != 'Собств.(аренда)', TIMESTAMPDIFF(second, d.start_date, d.end_date) / 3600, 0)) as totalTime,\n" +
            "\tsum(@exactCalcTime \\:= if (o.owner_name != 'Собств.(аренда)', @totalTime - (select @deliveryDispatchTimeNorm \\:= tn.norm from time_norm tn where tn.relevance_date < d.end_date and tn.type_id = (select tnt.type_id from time_norm_type tnt where tnt.type_code = 'deliveryDispatchTime') order by tn.relevance_date desc limit 1), 0)) as exactCalcTime,\n" +
            "\tsum(@calcTime \\:= if (o.owner_name != 'Собств.(аренда)', if(@exactCalcTime - floor(@exactCalcTime) > 0.25, floor(@exactCalcTime) + 1, floor(@exactCalcTime)), 0)) as calcTime,\n" +
            "\tsum(@maxPayTime \\:= if (o.owner_name != 'Собств.(аренда)', (select tn.norm from time_norm tn where tn.relevance_date < d.end_date and tn.type_id = (select tnt.type_id from time_norm_type tnt where tnt.type_name = 'Технологический срок оборота вагона') order by tn.relevance_date desc limit 1) - @deliveryDispatchTimeNorm + 24, 0)) as maxPayTime,\n" +
            "\tsum(@exactPayTime \\:= if (o.owner_name != 'Собств.(аренда)', if((select ow.owner_name from owner ow where ow.owner_id = d.owner_id) = 'ВСП', @calcTime, if (@calcTime > @maxPayTime, @maxPayTime, @calcTime)), 0)) as exactPayTime,\n" +
            "\tsum(@payTime \\:= if (o.owner_name != 'Собств.(аренда)', if(@exactPayTime - floor(@exactPayTime) > 0.25, floor(@exactPayTime) + 1, floor(@exactPayTime)), 0)) as payTime,\n" +
            "\tsum(@draftPaySum \\:= if (o.owner_name != 'Собств.(аренда)', (select br.rate from base_rate br where br.group_id = (select wt.group_id from wagon_type wt where wt.type_id = d.wagon_type_id) and br.hours = @payTime) * (select itbr.index_to_rate from index_to_base_rate itbr where itbr.relevance_date  < d.end_date order by itbr.relevance_date desc limit 1), 0)) as draftPaySum, \n" +
            "\tsum(@paySum \\:= if((select ow.owner_name from owner ow where ow.owner_id = d.owner_id) = 'СНГ', @draftPaySum * 1.3, @draftPaySum)) as paySum,\n" +
            "\tsum(@penaltyTime \\:= if (o.owner_name != 'Собств.(аренда)', @calcTime - @payTime, 0)) as penaltyTime,\n" +
            "\tsum(@penaltySum \\:= if (o.owner_name != 'Собств.(аренда)', @penaltyTime * (select br.rate from base_rate br where br.group_id = (select wt.group_id from wagon_type wt where wt.type_id = d.wagon_type_id) and br.hours = @payTime), 0)) as penaltySum,\n" +
            "\tsum(if (d.shunting_works > 0, d.shunting_works, 0)) as shuntingWork,\n" +
            "\tsum(@shuntingWorkSum \\:= if (d.shunting_works > 0, d.shunting_works * (select t.tariff from tariff t where t.relevance_date < d.end_date and t.type_id = (select tt.type_id from tariff_type tt where tt.type_code = 'shuntingWork') order by t.relevance_date desc limit 1), 0)) as shuntingWorkSum,\n" +
            "\tsum(@paySum + @penaltySum + @loadUnloadWorkSum + @shuntingWorkSum + @deliveryDispatchSum) as totalSum,\n" +
            "\tsum(if (d.load_unload_work = 1, 1, 0)) as loadUnloadWorkQuantity,\n" +
            "\tsum(if (d.load_unload_work = 1, d.cargo_weight, 0)) as loadUnloadWorkWeightSum,\n" +
            "\tsum(@loadUnloadWorkSum \\:= if (d.load_unload_work = 1, d.cargo_weight * (select t.tariff from tariff t where t.relevance_date < d.end_date and t.type_id = (select tt.type_id from tariff_type tt where tt.type_code = 'loadUnloadWork') order by t.relevance_date desc limit 1), 0)) as loadUnloadWorkSum\n" +
            "from\n" +
            "\tdelivery_of_wagon d\n" +
            "left join customer c on\n" +
            "\tc.customer_id = d.customer_id\n" +
            "left join cargo_operation co on\n" +
            "\td.operation_id = co.operation_id\n" +
            "left join cargo_type ct on\n" +
            "\td.cargo_type_id = ct.type_id\n" +
            "left join owner o on\n" +
            "\td.owner_id = o.owner_id\n" +
            "where\n" +
            "\td.end_date between ?1 and ?2\n" +
            "\tand co.operation_name != ?3\n" +
            "\tand c.customer_name = ?4\n" +
            "group by\n" +
            "\tc.customer_name,\n" +
            "\tco.operation_name,\n" +
            "\tct.type_name with rollup\n" +
            "order by\n" +
            "\tc.customer_name,\n" +
            "\tco.operation_name,\n" +
            "\tct.type_name";

    String generalSetReportQuery = "select\n" +
            "\tif(grouping(c.customer_name),\n" +
            "\t'all',\n" +
            "\tc.customer_name) as customer,\n" +
            "\tif(grouping(co.operation_name),\n" +
            "\t'all',\n" +
            "\tco.operation_name) as operation,\n" +
            "\tif(grouping(ct.type_name),\n" +
            "\t'all',\n" +
            "\tct.type_name) as cargoType,\n" +
            "\tsum(d.cargo_weight) as dispatchWeightSum,\n" +
            "\tcount(d.delivery_id) as dispatchWagonQuantity,\n" +
            "\tsum(@deliveryDispatchSum \\:= (select t.tariff from tariff t where t.type_id = (select tt.type_id from tariff_type tt where tt.type_code = 'deliveryDispatchWork') and t.relevance_date < d.end_date order by t.relevance_date desc limit 1)) as deliveryDispatchSum,\n" +
            "\tsum(@totalTime \\:= if (o.owner_name != 'Собств.(аренда)', TIMESTAMPDIFF(second, d.start_date, d.end_date)/ 3600, 0)) as totalTime,\n" +
            "\tsum(@exactCalcTime \\:= if (o.owner_name != 'Собств.(аренда)', @totalTime - (select @deliveryDispatchTimeNorm \\:= tn.norm from time_norm tn where tn.relevance_date < d.end_date and tn.type_id = (select tnt.type_id from time_norm_type tnt where tnt.type_code = 'deliveryDispatchTime') order by tn.relevance_date desc limit 1), 0)) as exactCalcTime,\n" +
            "\tsum(@calcTime \\:= if (o.owner_name != 'Собств.(аренда)', if(@exactCalcTime - floor(@exactCalcTime) > 0.25, floor(@exactCalcTime) + 1, floor(@exactCalcTime)), 0)) as calcTime,\n" +
            "\tsum(@maxPayTime \\:= if (o.owner_name != 'Собств.(аренда)', (select tn.norm from time_norm tn where tn.relevance_date < d.end_date and tn.type_id = (select tnt.type_id from time_norm_type tnt where tnt.type_name = 'Технологический срок оборота вагона') order by tn.relevance_date desc limit 1) - @deliveryDispatchTimeNorm + 24, 0)) as maxPayTime,\n" +
            "\tsum(@exactPayTime \\:= if (o.owner_name != 'Собств.(аренда)', if((select ow.owner_name from owner ow where ow.owner_id = d.owner_id) = 'ВСП', @calcTime, if (@calcTime > @maxPayTime, @maxPayTime, @calcTime)), 0)) as exactPayTime,\n" +
            "\tsum(@payTime \\:= if (o.owner_name != 'Собств.(аренда)', if(@exactPayTime - floor(@exactPayTime) > 0.25, floor(@exactPayTime) + 1, floor(@exactPayTime)), 0)) as payTime,\n" +
            "\tsum(@draftPaySum \\:= if (o.owner_name != 'Собств.(аренда)', (select br.rate from base_rate br where br.group_id = (select wt.group_id from wagon_type wt where wt.type_id = d.wagon_type_id) and br.hours = @payTime) * (select itbr.index_to_rate from index_to_base_rate itbr where itbr.relevance_date  < d.end_date order by itbr.relevance_date desc limit 1), 0)) as draftPaySum, \n" +
            "\tsum(@paySum \\:= if((select ow.owner_name from owner ow where ow.owner_id = d.owner_id) = 'СНГ', @draftPaySum * 1.3, @draftPaySum)) as paySum,\n" +
            "\tsum(@penaltyTime \\:= if (o.owner_name != 'Собств.(аренда)', @calcTime - @payTime, 0)) as penaltyTime,\n" +
            "\tsum(@penaltySum \\:= if (o.owner_name != 'Собств.(аренда)', @penaltyTime * (select br.rate from base_rate br where br.group_id = (select wt.group_id from wagon_type wt where wt.type_id = d.wagon_type_id) and br.hours = @payTime), 0)) as penaltySum,\n" +
            "\tsum(if (d.shunting_works > 0, d.shunting_works, 0)) as shuntingWork,\n" +
            "\tsum(@shuntingWorkSum \\:= if (d.shunting_works > 0, d.shunting_works * (select t.tariff from tariff t where t.relevance_date < d.end_date and t.type_id = (select tt.type_id from tariff_type tt where tt.type_code = 'shuntingWork') order by t.relevance_date desc limit 1), 0)) as shuntingWorkSum,\n" +
            "\tsum(@paySum + @penaltySum + @loadUnloadWorkSum + @shuntingWorkSum + @deliveryDispatchSum) as totalSum,\n" +
            "\tsum(if (d.load_unload_work = 1, 1, 0)) as loadUnloadWorkQuantity,\n" +
            "\tsum(if (d.load_unload_work = 1, d.cargo_weight, 0)) as loadUnloadWorkWeightSum,\n" +
            "\tsum(@loadUnloadWorkSum \\:= if (d.load_unload_work = 1, d.cargo_weight * (select t.tariff from tariff t where t.relevance_date < d.end_date and t.type_id = (select tt.type_id from tariff_type tt where tt.type_code = 'loadUnloadWork') order by t.relevance_date desc limit 1), 0)) as loadUnloadWorkSum\n" +
            "from\n" +
            "\tdelivery_of_wagon d\n" +
            "left join customer c on\n" +
            "\tc.customer_id = d.customer_id\n" +
            "left join cargo_operation co on\n" +
            "\td.operation_id = co.operation_id\n" +
            "left join cargo_type ct on\n" +
            "\td.cargo_type_id = ct.type_id\n" +
            "left join owner o on\n" +
            "\td.owner_id = o.owner_id\n" +
            "where\n" +
            "\td.end_date between ?1 and ?2\n" +
            "\tand co.operation_name != ?3\n" +
            "group by\n" +
            "\tc.customer_name,\n" +
            "\tco.operation_name,\n" +
            "\tct.type_name with rollup\n" +
            "order by\n" +
            "\tc.customer_name,\n" +
            "\tco.operation_name,\n" +
            "\tct.type_name";

    @Query(value = staticReportQuery, nativeQuery = true)
    List<StaticReportRowDto> staticReport(Date afterDate, Date beforeDate);

    @Query(value = staticReportForCustomerQuery, nativeQuery = true)
    List<StaticReportRowDto> staticReportForCustomer(Date afterDate, Date beforeDate, String customerName);

    @Query(value = generalSetReportQuery, nativeQuery = true)
    List<GeneralSetReportRowDto> generalSetReport(Date afterDate, Date beforeDate, String excludeOperation);

    @Query(value = generalSetReportForCustomerQuery, nativeQuery = true)
    List<GeneralSetReportRowDto> generalSetReportForCustomer(Date afterDate, Date beforeDate, String excludeOperation, String customerName);
}
