<?php
	include 'db_config.php';
	
	$room_id = ($_POST["room_id"]);
	
	$sql = "SELECT * FROM $lobby_table_name WHERE room_id = '$room_id'";
	
	$lobby_result = mysql_query($sql);
	
	$return_result = array();
	
	while ($lobby_row = mysql_fetch_array($lobby_result)) {
		$temp_sql = "SELECT * FROM $tbl_user WHERE id = '$lobby_row[1]'";
		$temp = mysql_query($temp_sql);
		$result = mysql_fetch_array($temp);
		$return_result[] = new LobbyItem($result[1],$lobby_row[2]);	
	}
	
	print(json_encode($return_result));	
	mysql_close();
?>