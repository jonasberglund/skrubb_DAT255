#Reflektion
Den här veckan har det blivit mindre arbete gjort då 4 av 6 medlemmar har fått lägga tid på sitt
projekt i sin andra kurs. Vad vi har arbetat på är refaktorering och småfixar samt lite design.
Vi har även sysslat med dokumentering, manual, lite tester samt börjat med en rapport av
projektet. Uppskattningsvis har vi totalt lagt ner ca 80 timmars arbete denna sprinten.

En implementering vi hade velat haft med var en slags refresh-knapp som man kunde ladda ner data
från internet/server på nytt och haft funktionalitet som skulle sparat data i bundles för att
påskynda laddning utav activities som vi redan öppnat en gång i appen. Det var i detta skedet som
vi upptäckte att våra activities aldrig bara stoppades utan alltid förstördes då Bundle-objektet
som vi sparat från onSaveInstanceState och kommer med i onCreate alltid var tomt.

Vi la väldigt mycket energi på att försöka få det att fungera med ovanstående implementation men
förgäves då det visades inte fungera alls. I vår refaktorering som vi påbörjade pillade vi med att
flytta om koden på ett sådant sett som android vill ha de uppbyggt, dvs att vyer skapas i onCreate
osv.

##Saker som borde gjorts tidigare
* Vi borde har tagit oss tid i början till att kolla upp lite hur Android vill att koden ska se
  ut, och hur Activities borde vara uppbyggda lite mer. Detta är sånt som har kommit fram nu, när
  vi har suttit och refaktorerat.
* Vi tänker oss att vi har följt Gitflow-idén ganska bra, men ibland när någon redan håller på med
  en liknande feature så kanske man har haft någon slags "nöd"-branch, eller ställt sig i någon
  som är "ledig" för att inte göra en med liknande namn, osv. Detta hade man nog kanske kunnat
  kollat upp lite mer innan hur man ville göra och följa något slag mönster.

##Roller
Samma som tidigare.

##Övrigt
Se sprintlog.md. För att projektet ska fungera korrekt i Eclipse måste projektet "appcompat" från
mappen "skrubb_DAT255" läggas in.
