<?php
	include 'db_config.php';

	$debug = true;	
	if (debug) {
		$room_id = ($_POST["room_id"]);
		$user_id = ($_POST["user_id"]);
	} else {
		$room_id = ($_GET["room_id"]);
		$user_id = ($_GET["user_id"]);
	}
	$status = 0;
	$temp_sql = "SELECT * FROM $lobby_table_name WHERE room_id = '$room_id' AND user_id = '$user_id'";
	$result = mysql_query($temp_sql);
	$count = mysql_num_rows($result);
	echo $count;
	if ($count == 0){	
		$sql = "INSERT INTO $lobby_table_name(room_id,user_id,status) VALUES('$room_id','$user_id','$status')";
	
		$insert_result = mysql_query($sql);
	
		if (!$insert_result) {
			$return_value = array(RETURN_KEYWORD => RETURN_VALUE_SQL_FAILED);
		} else {
			$return_value = array(RETURN_KEYWORD => RETURN_VALUE_SUCCESS);
		}
	}	
	print(json_encode($return_value));
	mysql_close();	
?>