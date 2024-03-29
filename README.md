﻿# Żwirownia

Prosty system obsługi wydobycia oraz sprzedaży na żwirowni wykonany w języku Java.


## Znane mi mankamenty
- redundancja (na przykład kod menu powielony wiele razy lub metody wczytania danych wyglądają prawie identycznie),
- brak jednostek miary (istnieje tylko jednostka o nazwie: "jednostka"), a wielkości są umowne (od bardzo małego rozmiaru, do bardzo dużego),
- parę innych; m.in. związanych z używaniem globalnego zmiennego stanu (choć w większości jest zależny od swojego obiektu nadrzędnego).


## Funkcje
- możliwość dodania rodzaju wydobywanego surowca (urobku),
- urobek podzielony/posortowany według wielkości - średnicy (domyślny podział to: bardzo drobne, drobne, średnie, duże),
- księgowanie, wyświetlanie i zarządzanie zmagazynowaną ilością wydobytego surowca,
- zarządzanie zamówieniami; tworzenie, realizacja i usuwanie,
- realizacja zamówienia, gdy stan "magazynu" na to pozwala, jeśli nie - wypisywana jest informacja; tak samo pomniejszając ilość poprzez zarządzanie magazynem,
- działanie "na żywo".



## Uruchomienie
**Aplikację w formacie .JAR można uzyskać ze źródeł:**
- samodzielny eksport na podstawie projektu,
- pobrać z mojego GoogleDrive ([Pobieranie](https://drive.google.com/file/d/1ADueMO7-69qVOFLk3fXTy9oXfpYlN_zu/view)) (postaram się, aby w razie aktualizacji, wersja była aktualna)

**Wymagane dodatkowe aplikacje:**
- Java w wersji 12 ([Pobieranie](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html))

_Zakładając, że:_
- korzystamy z systemu Windows,
- Java w wersji 12 zainstalowana jest na komputerze w lokalizacji: "C:\Program Files\Java\jdk-12.0.1",
- oraz, że aplikacja znajduje się w lokalizacji: "C:\" i jest nazwana "java-zwirownia.jar"

aplikację można włączyć poprzez wiersz poleceń lub PowerShell wpisując polecenie (ze cudzysłowami):
- "C:\Program Files\Java\jdk-12.0.1\bin\java.exe" -jar "C:\java-zwirownia.jar"


_Szybkie uruchomienie wiersza poleceń:_

_W Menu Start wyszukaj: CMD i uruchom LUB wciśnij kombinację klawiszy WIN + R, a następnie w polu tekstowym wpisz CMD i zatwierdź._

_Gdy pojawi się konsola powyższe polecenie należy wkleić (najczęściej klikając prawy przycisk) i wywołać klikając ENTER._



## Obsługa
**Uwaga:** Aplikacja przy uruchomieniu zapyta czy załadować wartości przykładowe - spowoduje to dodanie do magazynu przykładowych, różnych ilości surowców, oraz doda do listy zamówień przykładowe zamówienia - w tym jedno zrealizowane.
Jeśli chcesz załadować wartości przykładowe należy wpisać wartość "t", w przeciwnym wypadku należy wpisać "n" i w obu przypadkach należy zatwierdzić wybór wciskając klawisz ENTER.

### 1. ZARZĄDZANIE MAGAZYNEM

    1.1. Aby sprawdzić stan surowców należy kolejno w menu głównym:
        1) wybrać menu magazynu; należy wpisać "1" lub "m", następnie zatwierdzić,
        2) wybrać pozycję "(s)tan magazynu"; w tym celu należy wpisać "1" lub "s" i zatwierdzić,
        3) wyświetlony został stan magazynu dla poszczególnych wielkości i rodzajów surowców,
        4) aby opuścić menu, należy wcisnąć ENTER.

    1.2. Aby uzupełnić stan surowca (ze względu na postępy w produkcji) należy kolejno w menu głównym:
        1) wybrać menu magazynu; należy wpisać "1" lub "m", następnie zatwierdzić,
        2) wybrać pozycję "(u)zupełnij surowiec"; w tym celu należy wpisać "2" lub "u" i zatwierdzić,
        3) postępować zgodnie z poleceniami wyświetlanymi w programie - uruchomiony został kreator uzupełniania stanu surowca.
        Możliwe błędy:
        - "Ta liczba nie może być ujemna!" - podana ilość nie może być ujemna, należy podawać tylko liczby nieujemne,
        - "Błędna wartość - wymagana jest liczba całkowita!" - wymagane jest, aby podana wartość do dodania była liczbą całkowitą.

    1.3. Aby odjąć stan surowca (ze względu na straty w produkcji) należy kolejno w menu głównym:
        1) wybrać menu magazynu; należy wpisać "1" lub "m", następnie zatwierdzić,
        2) wybrać pozycję "(o)dejmij surowiec"; w tym celu należy wpisać "3" lub "o" i zatwierdzić,
        3) postępować zgodnie z poleceniami wyświetlanymi w programie - uruchomiony został kreator odejmowania stanu surowca.
        Możliwe błędy:
        - "Niewystarczające materiały lub błędna wartość" - surowiec jest w niewystarczającej ilości, aby odjąć podaną ilość, lub podana wartość liczbowa jest ujemna,
        - "Błędna wartość - wymagana jest liczba całkowita!" - wymagane jest, aby podana wartość do odjęcia była liczbą całkowitą.

### 2. ZARZĄDZANIE ZAMÓWIENIAMI

    2.1. Aby dodać zamówienie należy kolejno w menu głównym:
        1) wybrać menu zamówień; należy wpisać "2" lub "Z", następnie zatwierdzić,
        2) wybrać pozycję "(s)twórz zamówienie"; w tym celu należy wpisać "1" lub "s" i zatwierdzić,
        3) postępować zgodnie z poleceniami wyświetlanymi w programie - uruchomiony został kreator tworzenia zamówienia.
        Możliwe błędy:
        - "Wprowadzona wartość jest zbyt mała! (min 1)" - podana wartość jest zbyt mała,
        - "Wprowadzona wartość jest zbyt mała! (max 10000000)" - podana wartość jest zbyt duża, maksymalna ilość to: jeden milion jednostek
        - "Błędna wartość - wymagana jest liczba całkowita!" - wymagane jest, aby podana wartość była liczbą całkowitą.

    2.2 Aby zarządzać (przeglądać szczegóły, zrealizować lub usunąć) niezrealizowanym zamówieniem należy kolejno w menu głównym:
        1) wybrać menu zamówień; należy wpisać "2" lub "Z", następnie zatwierdzić,
        2) wybrać pozycję "zamówienia do (r)ealizacji"; w tym celu należy wpisać "2" lub "r" i zatwierdzić,
        3) następnie z wyświetlonych elementów, wybrać nas interesujący i aby wyświetlić szczegóły zamówienia, wpisać wartość liczbową (ID); aby zrezygnować należy wpisać wartość -1,
        4) zapoznać się z danymi zamówienia i wpisać odpowiednio liczbę 1, 2 lub 0, aby wybrać kolejno pozycje realizacji, usunięcia zamówienia lub powrotu do menu zamówień.
        Możliwe błędy:
        - "Brak wystarczających materiałów, aby zrealizować zamówienie!" - surowiec jest w niewystarczającej ilości, aby odjąć ilość surowca w zamówieniu - należy poczekać na uzupełnienie stanu magazynu (tego surowca) w celu realizacji.

    2.3 Aby zarządzać (przeglądać szczegóły, usunąć) zrealizowanym zamówieniem należy kolejno w menu głównym:
        1) wybrać menu zamówień; należy wpisać "2" lub "Z", następnie zatwierdzić,
        2) wybrać pozycję "zamówienia (z)realizowane"; w tym celu należy wpisać "3" lub "z" i zatwierdzić,
        3) następnie z wyświetlonych elementów, wybrać nas interesujący i aby wyświetlić szczegóły zamówienia, wpisać wartość liczbową (ID); aby zrezygnować należy wpisać wartość -1,
        4) zapoznać się z danymi zamówienia i wpisać odpowiednio liczbę 1 lub 0, aby wybrać kolejno pozycje usunięcia zamówienia lub powrotu do menu zamówień.



## Rady
Aby upewnić się, że żwirownia zrealizuje możliwie jak największą ilość zamówień, przy tworzeniu zamówienia należy kierować się poniższymi:
1) wybierać **jeden, wybrany rozmiar**, dla którego mamy pewność, że jest dostępny w magazynie w podanym rozmiarze,
2) wybierać **jak największy przedział** (najlepiej wszystkie dostępne) oraz należy stosować to **w połączeniu z opcją** dostępną podczas pytania: "Jak chcesz zrealizować zamówienie": **"odejmij łącznie ilość w miarę równomiernie"**.
3) nawiązując do wyżej wspomnianej opcji, podczas wymienionego pytania, do wyboru jest jeszcze jedna opcja: "odejmij ilość od każdego z osobna" - jednak jest ona najmniej wydajna pod względem ekonomicznym, ponieważ odejmuje od każdego wybranego rozmiaru konkretną ilość surowca - jest to jedynie zalecane, gdy klient żwirowni potrzebuje konkretną liczbę jednostek dla odpowiednich (lub wszystkich, gdy nie wybrano konkretnych) rozmiarów.


**Przykład pokazujący poprawność rady (opcja druga oraz trzecia):**

Przyjmijmy, że do żwirowni trafia zamówienie na surowiec: Żwir i jego stan to: \[bardzo drobne - 25; drobne - 32; średnie - 23; duże - 37; bardzo duże - 49]

Mamy dwa zamówienia na ten surowiec:
1) Zamówienie 1:
   - opis zamówienia: Zamówienie żwiru - konkretne ilości
   - rodzaj materiału: Żwir
   - rozmiar od: drobne, do: duże
   - ilość: łącznie 75 (każdy rozmiar po 25)
2) Zamówienie 2:
   - opis zamówienia: Zamówienie żwiru - do pobrania łącznie
   - rodzaj materiału: Żwir
   - rozmiar od: drobne, do: duże
   - ilość: łącznie 75 (pobieranie z wybranych dostępnych rozmiarów w miarę równomiernie)
   
Różnica jest spora w wybranym sposobie realizacji, co jest opisane poniżej.

> Zwróć uwagę na linijkę z rozmiarem oraz o ilości do pobrania - są to istotne informacje.

Suma ilości wielkości, z których użytkownik chce pobrać surowiec, to: 32 + 23 + 37 => 92 (każde zamówienie informuje, że wymaga ilości: 75).

Gdy chcemy pobrać w zamówieniu 1) konkretną ilość (25) z każdego rozmiaru, otrzymamy informację "Brak wystarczających materiałów, aby zrealizować zamówienie!".
Jest to spowodowane faktem, że jedna z wymaganych hałd (konkretnie o rozmiarze "średnie") nie spełnia wymagania ilości: 25 jednostek.

Z kolei realizując drugie zamówienie otrzymamy informację "Zamówienia zostało zrealizowane pomyślnie!", ponieważ system automatycznie postara się dobrać ilość spośród wybranych materiałów, tak, aby zamówienie zawsze zostało zrealizowane, jeśli tylko suma ilości potrzebnych wielkości jest większa, bądź równa ilości w magazynie.

Po realizacji zamówienia, stan tego surowca w magazynie wynosi: \[bardzo drobne - 25; drobne - 8; średnie - 0; duże - 9; bardzo duże - 49].

Zwróć uwagę jak system poradził sobie z brakiem dwóch jednostek w rozmiarze średnie (było dostępnych 23, a preferowana ilość to 25 \[bo: 75 łącznie / 3 rozmiary = 25 jednostek na rozmiar]): pobrał w zamian po jednej z rozmiarów drobne oraz duże.


## Opis struktury i działanie kodu
Program podzielony jest na dwa główne moduły:
- magazynu,
- zamówień.

W module magazynu operacje przebiegają na obiekcie typu Material, który posiada metody pozwalające odjąć, bądź dodać, określoną ilość z całości, konkretnych wielkości (np. małe i średnie) lub tylko z jednej wielkości materiału.
W metodzie odjęcia ilości surowca możliwe jest przekazanie informacji w przypadku podania więcej niż jednej wielkości, czy ilość ma zostać odjęta od każdej po równo, czy łącznie (ale nierównomiernie, gdy jest niemożliwe utrzymanie równomierności).
W module tym też możliwe jest dodanie kolejnych (własnych) możliwych wielkości do wybrania - enumerator SizeVariant oraz typów materiałów (np. kamień) - enumerator MaterialTypes - możliwe dodanie kolejnych typów i wielkości poprzez edycję w kodzie.

W module zamówień na podstawie listy z materiałami z modułu magazynu przetwarzane są zamówienia.
Przechowuje on informacje o zamówieniach (zrealizowanych i niezrealizowanych), możliwe jest tworzenie nowych i zarządzanie nimi.
Realizacja bazuje na istniejących obiektach typu Material poprzez odwołanie się do nich.

Poza tymi głównymi modułami, jednym z mniejszych, ale nadal ważnych modułów jest moduł menu, który pozwala wyświetlać nam sformatowany do potrzeb i według ustawień wygląd aplikacji, prezentacji danych w modułach i ich obsługi.

W celu łatwiejszej obsługi wprowadzanych danych w klasie Functions zostały stworzone statyczne metody pilnujące poprawności wprowadzanych danych. Jeśli na przykład oczekujemy w danym miejscu w programie łańcucha znaków, który ma określoną długość lub jest zawarty w podanej liście Stringów, wystarczy użyć konkretnej metody z tej klasy - każda z opisem znajdują się w klasie Functions.
W tej klasie znajdują się równeż dwie dodatkowe funkcje; uśpienie programu oraz procedura oczekująca na wciśnięcie klawisza, aby kontynuować działanie programu.

Aby zarządzać początkowymi wartościami (zwłaszcza po dodaniu nowych typów materiałów), należy udać się do klasy GravelPitManagement i zmodyfikować dane w procedurze Initialize.
