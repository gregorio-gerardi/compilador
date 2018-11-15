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
mem@cte3 DW 3
mem@cte2 DW 2
mem@cte22 DW 22
@aux2 DD ?
@aux3 DD ?
_c DD ?
_b DD ?
_a DD ?
max_double DW 3.40282347E38
min_double DW 1.17549435E-38
mensaje_error db "Error en tiempo de ejecucion!",0
mensaje_overflow_producto db "ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN PRODUCTO", 0
mensaje_overflow_suma db "ERROR EN TIEMPO DE EJECUCION --> OVERFLOW DETECTADO EN SUMA", 0
mensaje_division_cero db "ERROR EN TIEMPO DE EJECUCION --> DIVISION POR CERO DETECTADA", 0

.code
start:
MOV EAX ,2
MOV _b, EAX
MOV EAX ,3
MOV _c, EAX
MOV EAX,_b
IMUL EAX,_c
JO @LABEL_OVF_PRODUCTO
MOV @aux2, EAX
MOV EAX, 22
ADD EAX, @aux2
JO @LABEL_OVF_SUMA
MOV @aux3, EAX
MOV EAX ,@aux3
MOV _a, EAX

@LABEL_OVF_PRODUCTO:
invoke MessageBox, NULL, addr mensaje_overflow_producto,addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_OVF_SUMA:
invoke MessageBox, NULL, addr mensaje_overflow_suma, addr mensaje_error, MB_OK
JMP @LABEL_END
@LABEL_DIV_CERO:
invoke MessageBox, NULL, addr mensaje_division_cero, addr mensaje_error, MB_OK
@LABEL_END:
invoke ExitProcess, 0
end start
