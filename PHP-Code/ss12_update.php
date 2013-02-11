<?php
	include 'db_config.php';

	$sql = "SELECT * FROM $tbl_game_state";
	$result = mysql_query($sql);
	
	$myArray = array();
	
	if ($result) {
		while ($row = mysql_fetch_row($result)) {
			$number_result = new Number();
			$number_result->id = $row[0];
			$number_result->user_id = $row[1];
			$number_result->number = $row[2];
	//add new Number to result array
			$myArray[] = $number_result;		
		}
	
	print(json_encode($myArray));
	}
	
	mysql_close();
?>