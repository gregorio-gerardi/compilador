include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte3@502823E36 REAL8 3.502823E36
mem@cte214748 DD 214748
_s2 REAL8 ?
_s3 REAL8 ?
_s1 REAL8 ?
_l1 DD ?
@aux_mem DW ?
max_double_positivo REAL8 340282347000000000000000000000000000000.
min_double_positivo REAL8 0.0000000000000000000000000000000000000117549435
min_double_negativo REAL8 -340282347000000000000000000000000000000.
max_double_negativo REAL8 -0.0000000000000000000000000000000000000117549435
mensaje_error db "Error en tiempo de ejecucion!",0
mensaje_fin db "Fin de la ejecucion!",0
mensaje_overflow_producto db "OVERFLOW DETECTADO EN PRODUCTO", 0
mensaje_overflow_producto_negativo db "OVERFLOW DETECTADO EN PRODUCTO PARA NUMERO NEGATIVO", 0
mensaje_overflow_suma db "OVERFLOW DETECTADO EN SUMA", 0
mensaje_overflow_suma_negativo db "OVERFLOW DETECTADO EN SUMA PARA NUMERO NEGATIVO", 0
mensaje_division_cero db "DIVISION POR CERO DETECTADA", 0

.code
start:
FNINIT
MOV EAX ,214748
MOV _l1, EAX
FLD mem@cte3@502823E36
FSTP _s1
JMP @LABEL_END

@LABEL_OVF_PRODUCTO:
invoke MessageBox, NULL, addr mensaje_overflow_producto,addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_OVF_PRODUCTO_NEGATIVO:
invoke MessageBox, NULL, addr mensaje_overflow_producto_negativo,addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_OVF_SUMA:
invoke MessageBox, NULL, addr mensaje_overflow_suma, addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_OVF_SUMA_NEGATIVO:
invoke MessageBox, NULL, addr mensaje_overflow_suma_negativo, addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_DIV_CERO:
invoke MessageBox, NULL, addr mensaje_division_cero, addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_END:
invoke MessageBox, NULL, addr mensaje_fin, addr mensaje_fin, MB_OK
FNINIT
invoke ExitProcess, 0
end start
