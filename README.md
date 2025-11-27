-Lista korišćenih tehnologija i njihovih verzija:<br>
Java: 23.0.2<br>
Spring Boot: 4.0.0<br>
Hibernate ORM: 7.1.8.Final<br>
Tomcat (embedded): 11.0.14<br>
H2 Database: 2.4.240<br>
Maven<br>
<br>
-Instukcije za podešavanje okruženja potrebnog za build<br>
Instaliranje JDK 23<br>
Instaliranje Maven<br>
(Instaliranje IDE)<br>
<br>
-Instrukcije za pokretanje build-a<br>
Iz root direktorijuma projekta (gde se nalazi pom.xml):<br>
mvn clean install<br>
(ili "Maven build" u odabranom IDE)<br>
<br>
-Instrukcije za pokretanje aplikacije<br>
Iz root direktorijuma projekta (gde se nalazi pom.xml):<br>
mvn spring-boot:run<br>
(ili Run as Java Application nad fajlom MenzeApplication.java u odabranom IDE)<br>
<br>
-Instrukcije za pokretanje unit testova<br>
Nakon pokretanja Postmana uvucite kolekciju i environment fajlove. File -> Import i izaberite<br>
oba fajla sa .json ekstenzijom.<br>
Nakon importa proverite da li vam se u Collections i Environments delu nalaze importovana<br>
kolekcija i environment varijabla.<br>
Nakon toga izaberite importovani environment klikom na sledeće dugme i izaberite<br>
Levi9Cloud.<br>
Uneti "localhost:8080" kao baseURL varijablu u Environments.<br>
Da bi ste pokrenuli testove u Collections kliknite na kolekciju<br>
„5DanaUOblacima2025ChallengePublic” i pritisnite dugme Run.<br>
Nakon toga kada se otvori prompt za pokretanje testova izaberite opciju „Run manually“ i<br>
kliknite na dugme „Run 5DanaUOblacima2025ChallengePublic”<br>

