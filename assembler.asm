include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte-1@2 REAL8 -1.2
mem@cte1@2 REAL8 1.2
mem@cte0@0 REAL8 0.0
@aux2 REAL8 ?
_c REAL8 ?
_b REAL8 ?
_a REAL8 ?
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
FLD mem@cte-1@2
FSTP _a
FLD mem@cte0@0
FSTP _b
FLD _a
FLD _b
FADD
FSTP @aux2
FLD max_double
FLD @aux2
FCOM
FSTSW @aux_mem
MOV AX, @aux_mem
SAHF
JA @LABEL_OVF_SUMA
FLD min_double
FLD @aux2
FCOM
FSTSW AX
SAHF
JB @LABEL_OVF_SUMA
FLD @aux2
FSTP _c
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
