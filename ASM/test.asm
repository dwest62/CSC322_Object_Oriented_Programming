
.data
	greet: .asciiz "hello world!\n"
.text
.globl main
main:
addi $s0, $zero, 5
loop:
beq $s0, $zero, continue
li $v0, 4
la $a0, greet
syscall
sub $s0, $s0, 1
j loop

continue:
li $v0, 4
la $a0, greet
syscall

exit:
li $v0, 10
syscall