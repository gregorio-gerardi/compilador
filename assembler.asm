include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte5 DD 5
mem@cte1 DD 1
mem@cte0 DD 0
_var DD ?
_count DD ?
@aux5 DD ?
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
MOV EAX ,0
MOV _var, EAX
MOV EAX ,5
MOV _count, EAX
@labelTercetoWhile2:
MOV EAX, _var
CMP EAX, _count
invoke MessageBox, NULL, addr holamundo, addr holamundo, MB_OK
MOV EAX, _var
ADD EAX, 1
JO @LABEL_OVF_SUMA
MOV @aux5, EAX
MOV EAX ,@aux5
MOV _var, EAX
JMP @labelTercetoWhile2
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
