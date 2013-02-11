<?php
	include 'db_config.php';
	
	$sql = "SELECT * FROM $lobby_table_name";
	
	$result = mysql_query($sql);
	$num_row = mysql_num_rows($result);
	
	$result = mysql_query($sql);
	
	$room_result = array();
	$room_result[] = new RoomItem(1);	
	$room_result[] = new RoomItem(2);	
	$room_result[] = new RoomItem(3);	
	$room_result[] = new RoomItem(4);	
	
	while ($room_row = mysql_fetch_array($result)) {
		switch ($room_row[3]) {
			case '1':
				$room_result[0]->num++;	
				break;
			case '2':
				$room_result[1]->num++;	
				break;
			case '3':
				$room_result[2]->num++;	
				break;
			case '4':
				$room_result[3]->num++;	
				break;
			default:
				break;
		}	
	}	
	print(json_encode($room_result));
	mysql_close();
?>