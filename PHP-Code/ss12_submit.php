<?php
	include 'db_config.php';
	
	$u = ($_POST["user_id"]);
	$num = ($_POST["number"]);
	
	$sql = "SELECT * FROM $tbl_game_state WHERE number = '$num' OR number = '$num'-1";
	
	$result = mysql_query($sql);
	$num_row = mysql_num_rows($result);
	
	if ($num_row == 1 || $num ==1 ) {
		$insert_sql = "INSERT INTO $tbl_game_state(user_id,number) VALUES($u, $num)";
		echo "success";
	}
	
	$insert_result = mysql_query($insert_sql);
	mysql_close();
?>