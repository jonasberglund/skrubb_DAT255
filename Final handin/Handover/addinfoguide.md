#How to add information to the info view
The information in the info view is fetched from the server. Here is a guide on how to add/delete
information.

##Links
To add a link on the server, you need to login to the phpMyAdmin tool and then select the "skrubb" 
database, the "links" table and then click "Insert". Fill in the first "name"- and "href"-fields. 
The "name"-field specify what you want to have displayed and "href" is the actual address,
i.e. http://www.chalmers.se. Click "Go".

##Board Members
To add a member on the server, you have to login on the phpMyAdmin tool and then select the
"skrubb" database, the "members" table and then click "Insert". Fill in the first "name"-,
"email"-, "phone"-, "position"- and "picture"-field. The "picture" field should be an url to the
picture of the member. To make sure that the picture will show up, you should use a png file. The
png file could be uploaded to a server via FTP or simliar. Click "Go".

##Opening hours
To add openinghours to the server, you have to login to the phpMyAdmin tool and then select the
"skrubb" database, then the "openinghours" table and click "Insert". Fill in the first "name"- and
"opentime"-field. If a place has different times for different days, divide the opentimes with  
"&lt;br /&gt;". Click "Go".

Example

    name: Gulan  
    opentime: Mådag - Torsdag: 08:00 - 16:00<br />Fredag: 09:00 - 15:30


*Author: Robin Törnquist*
