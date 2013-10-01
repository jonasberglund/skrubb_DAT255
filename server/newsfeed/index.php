

<?php

$str = file_get_contents('https://graph.facebook.com/oauth/access_token?client_id=161725214029162&client_secret=bace54110f0ba764dd7f98af20f5bfea&grant_type=client_credentials');
$tooken =explode("=",$str);
//echo $tooken[0];
echo $tooken[1];  //token finns i $tooken[1]


$pageContent = file_get_contents('https://graph.facebook.com/109143889143301/posts?fields=id,name,message&limit=5&access_token='.$tooken[1]); 
 // You can use your app token

  //$parsedJson  = json_decode($pageContent); // decode JSON
	
	echo $pageContent;	
	//echo json_encode($parsedJson);
		/*
		echo $id = $parsedJson->data[0]->id; // this echos only the latest post ID
		echo $name = $parsedJson->data[0]->name;
		echo $created_time = $parsedJson->data[0]->created_time;
		echo $message = $parsedJson->data[0]->message;
		
		echo $id = $parsedJson->data[1]->id; // this echos only the latest post ID
		echo $name = $parsedJson->data[1]->name;
		echo $created_time = $parsedJson->data[1]->created_time;
		echo $message = $parsedJson->data[1]->message;
		
		echo $id = $parsedJson->data[2]->id; // this echos only the latest post ID
		echo $name = $parsedJson->data[2]->name;
		echo $created_time = $parsedJson->data[2]->created_time;
		echo $message = $parsedJson->data[2]->id->message;
		
		echo $id = $parsedJson->data[3]->id; // this echos only the latest post ID
		echo $name = $parsedJson->data[3]->name;
		echo $created_time = $parsedJson->data[3]->created_time;
		echo $message = $parsedJson->data[3]->id->message;
		*/
?>
