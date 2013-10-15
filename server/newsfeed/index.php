
<?php
// NewsFeed: Revive a post from H-sektionen with the week number corresponding to the date span for the news post
// week = 0 is the current week.

//If there is no variable in url the var_week=0 
if(count($_GET)==0){
$var_week=0;
}
else{$var_week=$_GET['week'];}

//Produce a date span of three weeks
$var_week=-3*$var_week;
$untilWeek ="{$var_week} Week";
$var_week=-3+$var_week;
$sinceWeek ="{$var_week} Week";

//Date span moved one day into the future to include post of the current day
$until=strtotime($untilWeek);
$until = strtotime("+1 day", $until);
$since=strtotime($sinceWeek);
$since = strtotime("+1 day", $since);

//Since
$yearS = date('Y', $since);
$monthS = date('m', $since);
$dayS = date('d', $since);
$sinceDate=''.$yearS . '-' . $monthS . '-' . $dayS;
//Until
$yearU = date('Y', $until);
$monthU = date('m', $until);
$dayU = date('d', $until);
$untilDate=''.$yearU . '-' . $monthU . '-' . $dayU;

//Get the apptoken with client id and appsecret
$str = file_get_contents('https://graph.facebook.com/oauth/access_token?client_id=161725214029162&client_secret=bace54110f0ba764dd7f98af20f5bfea&grant_type=client_credentials');
$tooken =explode("=",$str);
//access token is now in $tooken[1]

//Get newsfeed from facebook, limit the numver of post to the datespann. 
$pageContent = file_get_contents('https://graph.facebook.com/109143889143301/posts?fields=id,name,message,picture&limit=25&since='.$sinceDate.'&until='.$untilDate.'&access_token='.$tooken[1]); 

//Print the result
echo $pageContent;	


