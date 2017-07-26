package com.epam.ta.parking.dao.impl;

/**
 * <code>MySQLDBQuery</code> is a data base mysql queries
 */
public interface MySQLDBQuery {

	String QUERY_ADD_SLOT = "insert into slot (s_size) values (?)";
	String QUERY_FIND_SLOT = "select s_id, s_size, s_covered from slot where s_size = ? and s_covered = ? and s_id not in (select s_id from slot_has_vehicle) limit 1";
	String QUERY_GET_SLOT_STATISTICS = "select count(1) as all_slot, (select count(1) from slot where s_covered > 0) as reserve_slot from slot";
	String QUERY_GET_FIND_A_SLOT = "select s_id, s_size, s_covered from slot where s_id = ?";
	String QUERY_FREE_SLOT_RESERVATION = "update slot_has_vehicle set sl_end = now() where sl_id = ?";
	String QUERY_OCCUPY_A_SLOT = "insert into slot_has_vehicle (s_id, v_id) values (?,?)";
	String QUERY_GET_SLOT_RESERVATION_BY_ID = "select sl_id, s_id, v_id, sl_start, sl_end from slot_has_vehicle where sl_id = ?";
	String QUERY_FIND_SLOT_OCCUPIED_BY_VEHICLE = "select sl_id, s_id, v_id, sl_start, sl_end from slot_has_vehicle where v_id = (select v_id from vehicle where v_reg_num = ?) and sl_end IS NULL";
	String QUERY_GET_VEHICLE_BY_REG_NUMBER = "select v_id, v_reg_num, v_type from vehicle where v_reg_num=?";
	String QUERY_CREATE_A_VEHICLE = "insert into vehicle (v_reg_num, v_type) values (?,?)";
	String QUERY_DELETE_SLOT_RESERVATION = "delete from slot_has_vehicle where sl_id=?";
}
