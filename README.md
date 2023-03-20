# Documentație
## Serviciu de distribuție digitală de jocuri video
Un serviciu de distribuție digitală de jocuri video este o platformă unde se pot cumpăra, distribui și a promova jocuri video.
## Funcționalități
Website-ul va avea 4 nivele de permisiuni pentru utilizatori, în funcție de contul cu care sunt logați. Fiecare nivel mai înalt are toate funcționalitățile nivelelor mai joase de permisiune:
- Nivelul 0, vizitator: aceștia nu au nevoie de cont. Vizitatorii vor putea să vizualizeze jocurile, să le sorteze după popularitate, recenzii, data lansării sau preț, să le filtreze în funcție de interval de preț, reduceri sau taguri. De asemenea vor putea să vizualizeze poze care aprțin acestor jocuri, recenzile altori utlizatori, conturile lor sau conturi de developeri. Vizitatorii vor avea oricand posibilitatea să se înscrie pe site cu un cont propriu sau să se logheze cu unul existent pentru a avea mai multă libertate pe website. Oricand un vizitator va apăsa pe butoane cum ar fi cele de ”Adaugă în coș”, vor fi redirecționați la pagina unde își pot face cont.
- Nivelul 1, utilizator: aceștia au nevoie de cont. Pe lângă funcționalitățile precizate mai sus, utilizatorii vor avea și o pagină personalizată de descoperire de jocuri. Astfel, în funcție de jocurile deja deținute de utilizator, îi vor fi recomandate jocuri noi cu taguri similare. Utilizatorii vor putea să-și pună porecle, poze de profil, poze de fundal la pagina lor, să își selecteze țara pentru afișarea prețurilor în bancnota locală și să își adauge o descriere la profil. Pe deasupra vor putea ține o evidență a jocurilor jucate și a timpului petrecut pe ele, cum o să fie câte un contor pentru orele jucate în fiecare joc. De asemenea va putea lăsa recenzii cu notă din 10 și un mesaj. Vor putea lăsa comentarii pe paginilor altor utilizatori. Utilizatori vor putea să-și vizualizeze jocurile cumpărate într-un loc, iar când intră pe pagina unui joc deținut vor avea opțiunea să lase o recenzie, să cumpere jocul pentru un alt utilizator, sau să îl downloadeze pentru ei.
- Nivelul 3, developer: aceștia au nevoie de cont și un pas în plus de verificare. Ei au posibilitatea să își încarce jocurile proprii. Vor putea de asemenea să editeze detalii despre jocurile lor, să încarce poze și să adauge discounturi.
- Nivelul 4, administrator: aceștia au voie să șteargă, să modifice și să adauge date din baza de date cu foarte puține constrângeri. Vor putea edita pagini de jocuri sau de utilizatori, să adauge sau să șteargă jocuri din librariile utilizatorilor și să pună discounturi.

# Baza de date
Eu voi avea în total 9 tabele în baza de date.
-  USER - Tabela pentru utilizatori conține detaliile despre ei dar și nivelul de permisiuni.
-  COUNTRY - conține numele, bancnota și exchange rate-ul față de euro. Are o relație de One to Many față de tabela USER.
-  GAME - Are diferite informații despre fiecare joc video, cum ar fi numele, descrierea sau system requirements-urile.
- GAME_IMAGES - Fiecare joc poate avea mai multe poze, așa că am făcut o tabela pentru poze din jocuri și jocul sursă.
- TAG - O listă de taguri posibile pentru jocuri
- GAME_TAGS - Deoarece este o relație Many to Many dintre taguri și jocuri, avem acest tabel.
- COMMENT - Pentru comentarii am mai făcut o tabelă cu mesajul, utilizatorul autor și utilizatorul destinatar. 
- REVIEW - Pentru recenzii am făcut tabela care să conțină mesajul, autorul, jocul destinatar și nota dată.

@startuml

title Database Diagram
' hide the spot
hide circle

' avoid problems with angled crows feet
skinparam linetype ortho

entity "User" as user {
  *id : int
  --
  *username : varchar
  *password : varchar
  *email : varchar
  *permissions : int
  *hours_this_week : float
  *date_joined : date
  country_id : int
  icon_url : varchar
  cover_url : varchar
  bio : varchar
}

entity "Country" as country {
  *id : int
  --
  *name : varchar
  *currency_name : varchar
  *currency_in_euro : float
  flag_url : varchar
}

entity "Comment" as comment{
  *id : int
  --
  *profile_id: int
  *author_id: int
  *message : varchar
  *date : date
}

entity "Game" as game {
  *id : int
  --
  *name : varchar
  *developer_id : int
  *release_date : date
  *price_euro : float
  *sales : int
  discount_percent : int
  discount_end : date
  icon_url : varchar
  description : varchar
  system : varchar
}

entity "Game_images" as game_images {
  *id : int
  --
  *game_id : int
  *image_url : varchar
}

entity "Tag" as tag {
  *name : varchar
}

entity "Game_tags" as game_tags {
  *id : int
  --
  *game_id : int
  *tag_name : varchar
}

entity "Game_ownership" as game_ownership {
  *id : int
  --
  *game_id : int
  *user_id : int
  *hours_played : float
  last_played : date
}

entity "Review" as review {
  *id : int
  --
  *user_id : int
  *game_id : int
  *rating : int
  message : varchar
}

user }o--o| country
comment }o--|| user
comment }o--|| user
review }o--|| user
review }o--|| game
game_ownership }o--|| user
game_ownership }o--|| game
game }o--|| user
game_images }o--|| game
game_tags }o--|| game
game_tags}o--||tag

@enduml

