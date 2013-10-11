#The Server Side
The H-sektionen app is in many cases using a server to get data to show in the app. Some information is
not updated very often, i.e. board members, opening hours etc., but we did choose to put that 
information on a server instead of release an update of the app when the information is edited. The
server is based on PHP. In our case PHP is enough and very easy to use. The server is also used to
get the news feed from Facebook and events from Google Calendar. Data from Facebook and from
Google could be downloaded directly from the app, but if an address is changed, it is much easier
to change on the server side and let the app connect to an address that is fixed.

##Information
The information used in the InfoActivity is reached from 
http://www.prokrastinera.com/hsektionen/info/ and given as JSON. The information is stored in
a MySQL database and is easy to handle via the phpMyAdmin tool.

##News feed (Facebook)
The information used in NewsFeed is reached from 
http://www.prokrastinera.com/hsektionen/newsfeed/?week=x and given as a JSON. The server exchange credentials with facebook server to be able
to get the news feed from H-Sektionen facebook page. The server will produce JSON consisting of the post in a three week date span.
The x in the url corresponds to the week span.

##Events & Pub (Google Calendar)
The information about events and pubs used in EventsActivity and PubActivity is reached from the respective Google calendar that returns JSON.
	
https://www.google.com/calendar/feeds/CALENDAR_ID/public/full?alt=json
All events and pubs are editable using their calendar via google. 


