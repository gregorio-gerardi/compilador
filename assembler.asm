include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte10 DD 10
mem@cte16 DD 16
mem@cte13 DD 13
mem@cte1 DD 1
@aux8 DD ?
_var DD ?
ADENTROWHILEnovale13todavia db " ADENTRO WHILE no vale 13 todavia", 0
ADENTROWHILEvale13dentrodelif db " ADENTRO WHILE vale 13 dentro del if", 0
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
MOV _var, EAX
@labelSaltoIncondicional1:
MOV EAX, _var
CMP EAX, 16
JG @labelSaltoCondicional11
MOV EAX, _var
CMP EAX, 13
JNE @labelSaltoCondicional7
invoke MessageBox, NULL, addr ADENTROWHILEvale13dentrodelif, addr ADENTROWHILEvale13dentrodelif, MB_OK
JMP @labelSaltoIncondicional8
@labelSaltoCondicional7:
invoke MessageBox, NULL, addr ADENTROWHILEnovale13todavia, addr ADENTROWHILEnovale13todavia, MB_OK
@labelSaltoIncondicional8:
MOV EAX, _var
ADD EAX, 1
JO @LABEL_OVF_SUMA
MOV @aux8, EAX
MOV EAX ,@aux8
MOV _var, EAX
JMP @labelSaltoIncondicional1
@labelSaltoCondicional11:
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
