# User Stories
- As a user, I want to see news when I open the app.
- As a user, I want to find important links and contact information, open hours and more, on a page in the app. 
- As a user, I want a view where I can send some suggestion to Styret.
- As a user, I want to see upcoming eventes i events view. 
- As a user, I want to see the main view (the news feed) when I tap the "back"-button.

#Hur det gick
Informations, event och nyhetsfl�det �r klara och fungerande, de h�mtar info ifr�n servern.
F�rslag �r ocks� klar men skickar just nu inte till en skarp address (d� styret inte har n�gon �n).
Man kan hoppa tillbaka till startvyn/nyhetsfl�det ifr�n alla andra vyer genom att trycka p� bak-knappen,
trycks bak-knappen efter det s� st�ngs appen.
Det kan komma vissa �ndringar i GUI och teman (bakgrunder mm) d� allt s�dant inte �r f�rdigbest�mt.
Testning av koden var inte s� l�tt som f�rv�ntat, d� mycket av det som b�r testas �r kopplat till GUI och server vilket 
var sv�rt att test med JUnit. Det var dock l�ttare att skriva dessa som manuella test men dessa kanske inte �r lika effektiva.

*Bonus: En "loading"-animation �r tillagd och visas n�r de serverkr�vande delarna laddas.