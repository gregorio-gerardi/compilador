include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte10@0 DD 10.0
mem@cte15@0 DD 15.0
@aux2 DD ?
_c DD ?
_b DD ?
_a DD ?
max_double DD 3.40282347E38
min_double DD 1.17549435E-38
mensaje_error db "Error en tiempo de ejecucion!",0
mensaje_fin db "Fin de la ejecucion!",0
mensaje_overflow_producto db "OVERFLOW DETECTADO EN PRODUCTO", 0
mensaje_overflow_suma db "OVERFLOW DETECTADO EN SUMA", 0
mensaje_division_cero db "DIVISION POR CERO DETECTADA", 0

.code
start:
FLD mem@cte15@0
FSTP _a
FLD mem@cte10@0
FSTP _b
FLD _a
FLD _b
FADD
FSTP @aux2
FLD max_double
FLD @aux2
FCOM
FSTSW AX
SAHF
JG @LABEL_OVF_SUMA
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
invoke ExitProcess, 0
end start
