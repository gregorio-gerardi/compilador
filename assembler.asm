include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
dll_dllcrt0 PROTO C
printf PROTO C :VARARG
.DATA
mem@cte4@5 REAL8 4.5
mem@cte10@5 REAL8 10.5
_s2 REAL8 ?
_s3 REAL8 ?
@aux2 REAL8 ?
_s1 REAL8 ?
@aux8 REAL8 ?
@aux5 REAL8 ?
@aux11 REAL8 ?
imprime6 db "imprime 6", 0
imprime2_33 db "imprime 2_33", 0
imprime15 db "imprime 15", 0
imprime47_25 db "imprime 47_25", 0
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
FLD mem@cte10@5
FSTP _s1
FLD mem@cte4@5
FSTP _s2
FLD _s1
FLD _s2
FADD
FSTP @aux2
FLD @aux2
FTST
FSTSW AX
SAHF
FNINIT
JB @LABEL_CHEQUEO_OVERFLOW_SUMA_NEGATIVOS_SINGLES
FLD max_double_positivo
FLD @aux2
FCOM
FSTSW @aux_mem
MOV AX, @aux_mem
SAHF
FNINIT
JA @LABEL_OVF_SUMA
FLD min_double_positivo
FLD @aux2
FCOM
FSTSW AX
SAHF
FNINIT
JB @LABEL_OVF_SUMA
JMP @LABEL_FIN_OVERFLOW_POSITIVO
@LABEL_CHEQUEO_OVERFLOW_SUMA_NEGATIVOS_SINGLES:
FLD max_double_negativo
FLD @aux2
FCOM
FSTSW @aux_mem
MOV AX, @aux_mem
SAHF
FNINIT
JA @LABEL_OVF_SUMA_NEGATIVO
FLD min_double_negativo
FLD @aux2
FCOM
FSTSW AX
SAHF
FNINIT
JB @LABEL_OVF_SUMA_NEGATIVO
@LABEL_FIN_OVERFLOW_POSITIVO:
FLD @aux2
FSTP _s3
invoke MessageBox, NULL, addr imprime15, addr imprime15, MB_OK

invoke printf, cfm$("%.20Lf\n"), _s3
FLD _s1
FLD _s2
FSUB
FSTP @aux5
FLD @aux5
FSTP _s3
invoke MessageBox, NULL, addr imprime6, addr imprime6, MB_OK

invoke printf, cfm$("%.20Lf\n"), _s3
FLD _s1
FLD _s2
FMUL
FSTP @aux8
FLD @aux8
FTST
FSTSW AX
SAHF
FNINIT
JB @LABEL_CHEQUEO_OVERFLOW_MUL_NEGATIVOS_SINGLES
FLD max_double_positivo
FLD @aux8
FCOM
FSTSW AX
SAHF
FNINIT
JA @LABEL_OVF_PRODUCTO
FLD min_double_positivo
FLD @aux8
FCOM
FSTSW AX
SAHF
FNINIT
JB @LABEL_OVF_PRODUCTO
JMP @LABEL_FIN_OVERFLOW_POSITIVO_MUL
@LABEL_CHEQUEO_OVERFLOW_MUL_NEGATIVOS_SINGLES:
FLD max_double_negativo
FLD @aux8
FCOM
FSTSW AX
SAHF
FNINIT
JA @LABEL_OVF_PRODUCTO_NEGATIVO
FLD min_double_negativo
FLD @aux8
FCOM
FSTSW AX
SAHF
FNINIT
JB @LABEL_OVF_PRODUCTO_NEGATIVO
@LABEL_FIN_OVERFLOW_POSITIVO_MUL:
FLD @aux8
FSTP _s3
invoke MessageBox, NULL, addr imprime47_25, addr imprime47_25, MB_OK

invoke printf, cfm$("%.20Lf\n"), _s3
FLD _s2
FTST
FSTSW AX
SAHF
JE @LABEL_DIV_CERO
FLD _s1
FLD _s2
FDIV
FSTP @aux11
FLD @aux11
FSTP _s3
invoke MessageBox, NULL, addr imprime2_33, addr imprime2_33, MB_OK

invoke printf, cfm$("%.20Lf\n"), _s3
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
