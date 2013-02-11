<?php
	include 'db_config.php';
	
	$u = ($_POST["username"]);
	$p = ($_POST["password"]);
	
	$sql = "SELECT * FROM ss12_user WHERE username = '$u'";
	
	$result = mysql_query($sql);
	$return = "";
	
	//echo mysql_num_rows($result);
	if(mysql_num_rows($result) <= 0)
	{

		$insert_sql = "INSERT INTO ss12_user(username,password) VALUES('$u','$p')";

		$result = mysql_query($insert_sql);

		$sql = "SELECT id FROM ss12_user WHERE username = '$u' AND password = '$p'";
	
		$result = mysql_query($sql);
	
		$output = mysql_fetch_assoc($result);
		
		print(json_encode($output));
	}
	else
		{
			$sql = "SELECT id FROM ss12_user WHERE username = '$u' AND password = '$p'";
			$result = mysql_query($sql);
			if(mysql_num_rows($result) <= 0)
			{
				//print("-1");
			}
			else
				{
					$output = mysql_fetch_assoc($result);
					print(json_encode($output));
				}
		}
	
	mysql_close();
		
?>