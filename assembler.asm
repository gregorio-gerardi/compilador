.386
.MODEL flat, stdcall
option casemap :none
.STACK 200h
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
include \masm32\include\masm32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
.DATA
mem@cte340282346E38 DD 3.40282346E38
mem@cte340282345E38 DD 3.40282345E38
@aux2 DD ?
_c DD ?
_b DD ?
_a DD ?
max_double DD 3.40282347E38
min_double DD 1.17549435E-38
mensaje_error db "Error en tiempo de ejecucion!",0
mensaje_fin db "Fin de la ejecucion!",0
mensaje_overflow_producto db "ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN PRODUCTO", 0
mensaje_overflow_suma db "ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN SUMA", 0
mensaje_division_cero db "ERROR EN TIEMPO DE EJECUCION --> DIVISION POR CERO DETECTADA", 0

.code
start:
FLD mem@cte340282346E38
FSTP _a
FLD mem@cte340282345E38
FSTP _b
FLD _a
FLD _b
FMUL
FSTP @aux2
FLD max_double
FLD @aux2
FCOM
FSTSW AX
SAHF
JO @LABEL_OVF_PRODUCTO
FLD min_double
FLD @aux2
FCOM
FSTSW AX
SAHF
JO @LABEL_OVF_PRODUCTO
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
