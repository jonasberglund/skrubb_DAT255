# User Stories
- As a user, I want to see news when I open the app.
- As a user, I want to find important links and contact information, open hours and more, on a page in the app. 
- As a user, I want a view where I can send some suggestion to Styret.
- As a user, I want to see upcoming eventes i events view. 
- As a user, I want to see the main view (the news feed) when I tap the "back"-button.

#Hur det gick
Informations, event och nyhetsflödet är klara och fungerande, de hämtar info ifrån servern.
Förslag är också klar men skickar just nu inte till en skarp address (då styret inte har någon än).
Man kan hoppa tillbaka till startvyn/nyhetsflödet ifrån alla andra vyer genom att trycka på bak-knappen,
trycks bak-knappen efter det så stängs appen.
Det kan komma vissa ändringar i GUI och teman (bakgrunder mm) då allt sådant inte är färdigbestämt.
Testning av koden var inte så lätt som förväntat, då mycket av det som bör testas är kopplat till GUI och server vilket 
var svårt att test med JUnit. Det var dock lättare att skriva dessa som manuella test men dessa kanske inte är lika effektiva.

*Bonus: En "loading"-animation är tillagd och visas när de serverkrävande delarna laddas.