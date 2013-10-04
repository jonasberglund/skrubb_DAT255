package se.chalmers.h_sektionen.utils;

public class Constants {
	public static final String GOOGLEEVENTS = "https://www.google.com/calendar/feeds/5id1508tk2atummuj0vq33d7lc@group.calendar.google.com/public/full?alt=json&" +
    		"orderby=starttime&" +
    		"sortorder=ascending&" +
    		"futureevents=true&" + //Overrides start-min and max
    		//"start-min=2013-08-31T10:57:00-08:00&" +
    		//"start-max=2013-09-31T10:57:00-08:00" +
    		"";
	
	
	public static final String PUBEVENTS = "https://www.google.com/calendar/feeds/ad5l0g27kpvt7klvlsudmodnhk@group.calendar.google.com/public/full?alt=json&" +
			"orderby=starttime&" +
    		"sortorder=ascending&" +
    		"futureevents=true&" + //Overrides start-min and max
    		//"start-min=2013-08-31T10:57:00-08:00&" +
    		//"start-max=2013-09-31T10:57:00-08:00" +
    		"";
	
	public static final String NEWSFEED = "http://jpv-net.dyndns.org:1337/H-Sektionen/newsfeed/";
	public static final String SUGGESTEMAIL = "foerslagsladan.918a17d9@internverksamhet.h-styret.podio.com";
	public static final String INFO = "http://jpv-net.dyndns.org:1337/H-Sektionen/info/";
	
	public static final String GET_FEED_ERROR_MSG = "Det gick inte att hämta flödet.";
	public static final String INTERNET_CONNECTION_ERROR_MSG = "Ingen internetanslutning.";
	public static final String INFO_ERROR = "Kunde inte hämta information.";
    
	public static final String SERVER_ADDRESS = "http://jpv-net.dyndns.org:1337";
	public static final String[] LUNCH_URLS = {"http://www.lindholmen.se/sv/restaurang/ls-kitchen"
		, "http://www.chalmerskonferens.se/dagens-menyer/lindholmen/","http://www.lindholmen.se/sv/restaurang/mimolett"
		, "http://www.lindholmen.se/sv/restaurang/bistrot", "http://www.lindholmen.se/sv/restaurang/restaurang-aran"};
	public static final String KOKBOKLUNCH = "http://www.chalmerskonferens.se/dagens-menyer/lindholmen/";
}
