<?php

header('Content-type: application/json; charset=UTF-8');

// Filled in by admin.
$mysql_host = "";
$mysql_database = "";
$mysql_user = "";
$mysql_password = "";

// Connect to the server.
$mysql_connection = mysql_connect($mysql_host, $mysql_user, $mysql_password);
mysql_select_db($mysql_database);

// Fetch the board members.
$result = mysql_query("SELECT * FROM members ORDER BY id");
$members = array();
while($row = mysql_fetch_array($result)) {
	$member = array();
	$member['name'] = utf8_encode($row['name']);
	$member['email'] = utf8_encode($row['email']);
	$member['position'] = utf8_encode($row['position']);
	$member['picture'] = utf8_encode($row['picture']);
	$member['phone'] = utf8_encode($row['phone']);
	array_push($members, $member);
}
mysql_free_result($result);

// Fetch the links.
$result = mysql_query("SELECT * FROM links ORDER BY id");
$links = array();
while($row = mysql_fetch_array($result)) {
	$link = array();
	$link['name'] = utf8_encode($row['name']);
	$link['href'] = utf8_encode($row['href']);
	array_push($links, $link);
}
mysql_free_result($result);

// Fetch the opening hours.
$result = mysql_query("SELECT * FROM openinghours ORDER BY id");
$openinghours = array();
while($row = mysql_fetch_array($result)) {
	$place = array();
	$place['name'] = utf8_encode($row['name']);
	$place['opentime'] = utf8_encode($row['opentime']);
	array_push($openinghours, $place);
}
mysql_free_result($result);

mysql_close($mysql_connection);

// Put all values in one array.
$infoArray = array();
$infoArray['members'] = $members;
$infoArray['links'] = $links;
$infoArray['openinghours'] = $openinghours;

// Print the array as JSON.
print json_encode($infoArray);

?>
