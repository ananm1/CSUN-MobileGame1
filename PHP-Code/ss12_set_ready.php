<?php
	include 'db_config.php';
	
	$room_id = ($_POST["room_id"]);
	$user_id = ($_POST["user_id"]);
	
	$sql = "UPDATE $lobby_table_name SET status = 1 WHERE room_id = '$room_id' AND user_id = '$user_id'";
	$ready_result = mysql_query($sql);
	echo $ready_result;
		if (!$ready_result) {
			$return_value = array(RETURN_KEYWORD => RETURN_VALUE_SQL_FAILED);
		} else {
			$return_value = array(RETURN_KEYWORD => RETURN_VALUE_SUCCESS);
		}	
	
	print(json_encode($return_value));	
	mysql_close();
?>