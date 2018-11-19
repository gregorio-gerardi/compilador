include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte10 DD 10
mem@cte1@5 REAL8 1.5
mem@cte1 DD 1
mem@cte10@5 REAL8 10.5
_singleMutable2 REAL8 ?
_singleMutable1 REAL8 ?
_singleInmutable REAL8 ?
_integerMutable1 DD ?
_integerMutable2 DD ?
@aux10 DD ?
@aux14 DD ?
_integerInmutable DD ?
iniciodelwhile db "inicio del while", 0
findelwhile db "fin del while", 0
dentrodelif db "dentro del if", 0
porquelosenterosjuntossumanmasde10 db "porque los enteros juntos suman mas de 10", 0
porquelosenterosjuntossumanmenosoiguala10 db "porque los enteros juntos suman menos o igual a 10", 0
iniciodelif db "inicio del if", 0
chaumundocruel db "chau mundo cruel", 0
findelif db "fin del if", 0
dentrodelelse db "dentro del else", 0
holamundo db "hola mundo", 0
@aux_mem DW ?
max_double REAL8 340282347000000000000000000000000000000.
min_double REAL8 0.0000000000000000000000000000000000000117549435
mensaje_error db "Error en tiempo de ejecucion!",0
mensaje_fin db "Fin de la ejecucion!",0
mensaje_overflow_producto db "OVERFLOW DETECTADO EN PRODUCTO", 0
mensaje_overflow_suma db "OVERFLOW DETECTADO EN SUMA", 0
mensaje_division_cero db "DIVISION POR CERO DETECTADA", 0

.code
start:
FNINIT
MOV EAX ,10
MOV _integerInmutable, EAX
FLD mem@cte10@5
FSTP _singleInmutable
MOV EAX ,1
MOV _integerMutable1, EAX
MOV EAX ,_integerMutable1
MOV _integerMutable2, EAX
FLD mem@cte1@5
FSTP _singleMutable1
FLD _singleInmutable
FSTP _singleMutable2
invoke MessageBox, NULL, addr iniciodelwhile, addr iniciodelwhile, MB_OK
@labelSaltoIncondicional8:
MOV EAX, _integerMutable1
CMP EAX, _integerInmutable
JGE @labelSaltoCondicional26
MOV EAX, _integerMutable1
ADD EAX, 1
JO @LABEL_OVF_SUMA
MOV @aux10, EAX
MOV EAX ,@aux10
MOV _integerMutable1, EAX
MOV EAX ,_integerMutable1
MOV _integerMutable2, EAX
invoke MessageBox, NULL, addr iniciodelif, addr iniciodelif, MB_OK
MOV EAX, _integerMutable1
ADD EAX, _integerMutable2
JO @LABEL_OVF_SUMA
MOV @aux14, EAX
MOV EAX, @aux14
CMP EAX, _integerInmutable
JG @labelSaltoCondicional21
invoke MessageBox, NULL, addr holamundo, addr holamundo, MB_OK
invoke MessageBox, NULL, addr dentrodelif, addr dentrodelif, MB_OK
invoke MessageBox, NULL, addr porquelosenterosjuntossumanmenosoiguala10, addr porquelosenterosjuntossumanmenosoiguala10, MB_OK
JMP @labelSaltoIncondicional24
@labelSaltoCondicional21:
invoke MessageBox, NULL, addr chaumundocruel, addr chaumundocruel, MB_OK
invoke MessageBox, NULL, addr dentrodelelse, addr dentrodelelse, MB_OK
invoke MessageBox, NULL, addr porquelosenterosjuntossumanmasde10, addr porquelosenterosjuntossumanmasde10, MB_OK
@labelSaltoIncondicional24:
invoke MessageBox, NULL, addr findelif, addr findelif, MB_OK
JMP @labelSaltoIncondicional8
@labelSaltoCondicional26:
invoke MessageBox, NULL, addr findelwhile, addr findelwhile, MB_OK
JMP @LABEL_END

@LABEL_OVF_PRODUCTO:
invoke MessageBox, NULL, addr mensaje_overflow_producto,addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_OVF_SUMA:
invoke MessageBox, NULL, addr mensaje_overflow_suma, addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_DIV_CERO:
invoke MessageBox, NULL, addr mensaje_division_cero, addr mensaje_error, MB_OK
@LABEL_END:
invoke MessageBox, NULL, addr mensaje_fin, addr mensaje_fin, MB_OK
FNINIT
invoke ExitProcess, 0
end start
