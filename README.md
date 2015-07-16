# Riskant

Ziel dieses Spiels ist es, so lange wie möglich noch genug Längeneinheiten übrig zu haben,
um Verbindungen zu bauen zwischen den Symbolen, die an zufälligen Stellen auf dem Bildschirm
erscheinen.
Du hast anfangs 5000 Längeneinheiten zur Verfügung, die unten links als "Faden" dargestellt
werden, mit einer Zahl dahinter. In jeder Runde erscheinen zehn zufällige Symbole auf dem
Bildschirm. Jetzt musst du versuchen, ein Dreieck zu konstruieren, welches möglichst viele
Kreise, Rechtecke und Dreiecke einschließt. Klicke dazu auf drei der Symbole; dann werden
die Dreieckslinien dazwischen erstellt. Alles was außerhalb des Dreiecks bleibt, wird wie
folgt gewertet:

 * Jeder Kreis kostet 50 Längeneinheiten
 * Jedes Rechteck kostet 80 Längeneinheiten
 * Jedes Parallelogramm kostet 100 Längeneinheiten

Eine Besonderheit stellen die Dreiecksymbole dar: Diese geben nämlich je 150 Längeneinheiten
hinzu, wenn sie außerhalb (!) des von dir gebildeten Dreiecks liegen. Liegen sie jedoch
innerhalb (!) deines Dreiecks, so werden dir je 150 Längeneinheiten abgezogen.

Die von dir als Eckpunkte ausgewählten Symbole zählen jeweils null Einheiten.

Ein weiterer Kasus Knaxus ist, dass dir die Längeneinheiten, welche für das Erzeugen deines
Dreiecks nötig waren, ebenfalls abgezogen werden! Sonst könnte man einfach immer die drei
äußeren Begrenzungssymbole auswählen, was aber hier schwierig wird, weil irgendwann keine
Längeneinheiten mehr übrig sind.

Noch einmal "kurz zusammengefasst", was man tun sollte:

 * möglichst viele Kreise, Rechtecke und Parallelogramme einschließen
 * möglichst viele Dreiecke ausschließen
 * aufpassen, dass die Längeneinheiten nicht zur Neige gehen (sonst kann später
    das Dreieck, was man gerne hätte, nicht mehr gebildet werden)

Verloren hat man, sobald sich kein Dreieck mehr bilden lässt, bzw. die Längeneinheiten
kleiner oder gleich null sind.


HINWEIS: Für die Berechnung, ob ein Objekt innerhalb des vom Benutzer erzeugten
Dreiecks liegt oder nicht, habe ich den Code von Vertex benutzt. Vielen Dank dafür!