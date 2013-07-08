<?php

	
	define('RETURN_KEYWORD','result');
	define('RETURN_VALUE_FAILED','false');
	define('RETURN_VALUE_SUCCESS','true');
	define('RETURN_VALUE_SQL_FAILED','sql_failed');
	
	class Number {
		public $id;
		public $user_id;
		public $number;	
		
		public function __construct() {
			$this->id = -1;
			$this->user_id = -1;
			$this->number = 0;
		}
	}
	
	class RoomItem {
		public $id;
		public $num;
		
		public function __construct($temp_id) {
			$this->id = $temp_id;
			$this->num = 0;
		}
	}
	
	class LobbyItem {
		public $username;
		public $status;
		
		public function __construct($id,$temp_status) {
			$this->username = $id;
			$this->status = $temp_status;
		}
	}
	$con = mysql_connect($host, $user, $password);
	mysql_select_db($db_name);	
	
?>
