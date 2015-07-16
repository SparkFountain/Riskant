AppTitle("Riskant - Wähle klug!")
Graphics(400,400,32,2)
SetBuffer(BackBuffer())
Global frameTimer = CreateTimer(60)
SeedRnd(MilliSecs())

;Grafiken
Global kreis = LoadImage("gfx/kreis.png")
MaskImage(kreis,255,255,255)
Global rechteck = LoadImage("gfx/rechteck.png")
MaskImage(rechteck,255,255,255)
Global dreieck = LoadImage("gfx/dreieck.png")
MaskImage(dreieck,255,255,255)
Global parallelogramm = LoadImage("gfx/parallelogramm.png")
MaskImage(parallelogramm,255,255,255)

;Variablen
Global einheiten = 5000
Global status$ = "Runde beginnt"
Global gewaehlt = 0			;wieviele Symbole gewählt wurden
Global mh = MouseHit(1)		;zwischenspeichern, ob die linke Maustaste gedrückt wurde
Global x1, x2, x3, y1, y2, y3
Global umfang				;der Umfang des erzeugten Dreiecks


While Not KeyHit(1)
	
	mh = MouseHit(1)
	
	Cls()
	WaitTimer(frameTimer)
	
	If(status = "Runde beginnt") Then
		GeneriereSymbole()
	ElseIf(status = "Wähle drei Symbole") Then
		ZeichneSymbole()
		KlickeSymbole()
	ElseIf(status = "Auswerten") Then
		BerechneEinheiten()
	ElseIf(status = "Ergebnis anzeigen") Then
		ZeichneSymbole()
		ZeichneDreieck()
	EndIf
	
	ZeichneGUI()
	
	Flip(0)
	
Wend
End



Function AltesLoeschen()
	
	For s.symbol = Each symbol
		Delete s
	Next
	
	x1 = 0 : x2 = 0 : x3 = 0 : y1 = 0 : y2 = 0 : y3 = 0
	gewaehlt = 0
	umfang = 0
	
End Function



Function GeneriereSymbole()
	
	For i=1 To 10
		Local s.symbol = New symbol
		s\sorte = Rnd(1,4)
		s\x = Rnd(10,384)
		s\y = Rnd(10,300)
	Next
	
	status = "Wähle drei Symbole"
	
End Function


Function ZeichneSymbole()
	
	For s.symbol = Each symbol
		If(s\gewaehlt) Then 
			Color(128,128,128)
			Rect(s\x-8,s\y-8,32,32,1)
		EndIf
		
		If(status = "Ergebnis anzeigen") Then
			If(s\innerhalb = 1 And s\gewaehlt = 0) Then
				Color(0,255,0)
				Rect(s\x-8,s\y-8,32,32,1)
				Color(255,255,255)
				If(s\sorte = 3) Then
					Text(s\x,s\y-20,"-150")
				EndIf
			ElseIf(s\innerhalb = 0 And s\gewaehlt = 0)
				Color(255,0,0)
				Rect(s\x-8,s\y-8,32,32,1)
				Color(255,255,255)
				If(s\sorte = 1) Then
					Text(s\x,s\y-20,"-50")
				ElseIf(s\sorte = 2) Then
					Text(s\x,s\y-20,"-80")
				ElseIf(s\sorte = 3) Then
					Text(s\x,s\y-20,"+150")
				ElseIf(s\sorte = 4) Then
					Text(s\x,s\y-20,"-100")
				EndIf
			EndIf
		EndIf
		
		If(s\sorte = 1) Then
			DrawImage(kreis,s\x,s\y)
		ElseIf(s\sorte = 2) Then
			DrawImage(rechteck,s\x,s\y)
		ElseIf(s\sorte = 3) Then
			DrawImage(dreieck,s\x,s\y)
		Else
			DrawImage(parallelogramm,s\x,s\y)
		EndIf
	Next
	
End Function


Function KlickeSymbole()
	
	For s.symbol = Each symbol
		If(mh And MouseX() >= s\x And MouseX() <= s\x+16 And MouseY() >= s\y And MouseY() <= s\y+16) Then
			If(s\gewaehlt) Then
				s\gewaehlt = 0
				gewaehlt=gewaehlt-1
			Else
				If(gewaehlt < 3) Then
					s\gewaehlt = 1
					gewaehlt=gewaehlt+1
				EndIf
			EndIf
		EndIf
	Next
	
End Function


Function ZeichneGUI()
	
	Color(255,200,0)
	Rect(15,340,120,3,1)
	Color(255,255,255)
	Text(150,330,"Längeneinheiten: "+einheiten)
	
	If(status = "Wähle drei Symbole") Then
		Color(255,100,0)
		Rect(30,360,150,20)
		Color(255,255,255)
		Text(35,365,"Dreieck erzeugen")
		
		;wenn auf "Dreieck erzeugen" geklickt wird, erzeuge das Dreieck und ziehe den Rest ab
		If(gewaehlt = 3) Then
			If(mh And MouseX() >= 30 And MouseX() <= 180 And MouseY() >= 360 And MouseY() <= 380) Then
				status = "Auswerten"
			EndIf
		EndIf
	EndIf
	
	If(status = "Ergebnis anzeigen") Then
		If(einheiten > 0) Then
			Color(255,100,0)
			Rect(30,360,150,20)
			Color(255,255,255)
			Text(35,365,"Nächste Runde")
			
			If(mh And MouseX() >= 30 And MouseX() <= 180 And MouseY() >= 360 And MouseY() <= 380) Then
				AltesLoeschen()
				status = "Runde beginnt"
			EndIf
			
			Text(200,365,"Umfang des Dreiecks: "+umfang)
		Else
			RuntimeError("Du hast alle Einheiten verloren!")
		EndIf
	EndIf
	
End Function


Function BerechneEinheiten()
	
	Local a#, b#, c#
	Local kosinus#
	
	For s.symbol = Each symbol
		If(s\gewaehlt) Then
			If(x1 = 0) Then
				x1 = s\x+8
				y1 = s\y+8
			ElseIf(x2 = 0) Then
				x2 = s\x+8
				y2 = s\y+8
			Else
				x3 = s\x+8
				y3 = s\y+8
			EndIf
		EndIf
	Next
	
	;berechne die Längeneinheiten, die zum Erstellen dieses Dreiecks benötigt wurden
	umfang=umfang+Sqr(Abs((x1-x2)*(x1-x2))+Abs((y1-y2)*(y1-y2)))
	umfang=umfang+Sqr(Abs((x1-x3)*(x1-x3))+Abs((y1-y3)*(y1-y3)))
	umfang=umfang+Sqr(Abs((x2-x3)*(x2-x3))+Abs((y2-y3)*(y2-y3)))
	einheiten=einheiten-umfang
	
	;ermittle alle Symbole, die im Dreieck liegen
	For s.symbol = Each symbol
		If(s\gewaehlt = 0) Then
			;danke @Vertex!
			Local b1%, b2%, b3%
			b1% = Sign(s\x+8, s\y+8, x1, y1, x2, y2) < 0.0
			b2% = Sign(s\x+8, s\y+8, x2, y2, x3, y3) < 0.0
			b3% = Sign(s\x+8, s\y+8, x3, y3, x1, y1) < 0.0
			If((b1 = b2) And (b2 = b3)) Then s\innerhalb = 1
		EndIf
	Next
	
	For s.symbol = Each symbol
		If(s\innerhalb = 0) Then
			If(s\sorte = 1) Then
				einheiten=einheiten-50
			ElseIf(s\sorte = 2) Then
				einheiten=einheiten-80
			ElseIf(s\sorte = 3) Then
				einheiten=einheiten+150
			ElseIf(s\sorte = 4) Then
				einheiten=einheiten-100
			EndIf
		Else
			If(s\sorte = 3) Then
				einheiten=einheiten-150
			EndIf
		EndIf
	Next
	
	status = "Ergebnis anzeigen"
	
End Function


;danke @Vertex!
Function Sign#(x1#, y1#, x2#, y2#, x3#, y3#)
	Return (x1# - x3#)*(y2# - y3#) - (x2# - x3#)*(y1# - y3#)
End Function


Function ZeichneDreieck()
	
	Color(255,128,0)
	
	Line(x1,y1,x2,y2)
	Line(x1,y1,x3,y3)
	Line(x2,y2,x3,y3)
	
End Function


Type symbol
	Field sorte
	Field x,y
	Field gewaehlt
	Field innerhalb
End Type
;~IDEal Editor Parameters:
;~C#Blitz3D