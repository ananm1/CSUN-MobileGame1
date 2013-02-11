<?php
	include 'db_config.php';
	
	$room_id = ($_POST["room_id"]);
	
	//$room_id = ($_GET["room_id"]);
	$status = 1;
	$number = 2;	
	$sql = "SELECT * FROM $lobby_table_name WHERE room_id = '$room_id' AND status = '$status'";
	$launch_result = mysql_query($sql);
	$num = mysql_num_rows($launch_result);
	
	if ($num == $number){
		$return_value = array("launch" => "1");
	} else {
		$return_value = array("launch" => "0");
	}
	print(json_encode($return_value));	
	mysql_close();
?>